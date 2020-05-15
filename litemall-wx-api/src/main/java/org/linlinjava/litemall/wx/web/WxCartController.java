package org.linlinjava.litemall.wx.web;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.linlinjava.litemall.core.system.SystemConfig;
import org.linlinjava.litemall.core.util.JacksonUtil;
import org.linlinjava.litemall.core.util.ResponseUtil;
import org.linlinjava.litemall.db.beans.Constants;
import org.linlinjava.litemall.db.domain.*;
import org.linlinjava.litemall.db.service.*;
import org.linlinjava.litemall.wx.annotation.LogAnno;
import org.linlinjava.litemall.wx.annotation.LoginUser;
import org.linlinjava.litemall.wx.vo.CartVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

import static org.linlinjava.litemall.wx.util.WxResponseCode.GOODS_NO_STOCK;
import static org.linlinjava.litemall.wx.util.WxResponseCode.GOODS_UNSHELVE;

/**
 * 用户购物车服务
 */
@RestController
@RequestMapping("/wx/cart")
@Validated
public class WxCartController {
    private final Log logger = LogFactory.getLog(WxCartController.class);

    @Autowired
    private LitemallCartService cartService;
    @Autowired
    private LitemallGoodsService goodsService;
    @Autowired
    private LitemallGoodsProductService productService;
    @Autowired
    private LitemallAddressService addressService;
    @Autowired
    private LitemallGrouponRulesService grouponRulesService;
    @Autowired
    private LitemallCouponService couponService;
    @Autowired
    private LitemallCouponUserService couponUserService;
    @Autowired
    private LitemallGoodsSpecificationService specificationService;
    @Autowired
    private CouponVerifyService couponVerifyService;
    @Autowired
    private LitemallShopService litemallShopService;
    @Autowired
    private LitemallTaxService litemallTaxService;
    @Autowired
    private LitemallShopRegionService litemallShopRegionService;


    /**
     * 用户购物车信息
     *
     * @param userId 用户ID
     * @return 用户购物车信息
     */
    @GetMapping("index")
    @LogAnno
    public Object index(@LoginUser Integer userId) {
        if (userId == null) {
            return ResponseUtil.unlogin();
        }

        List<LitemallCart> cartList = cartService.queryByUid(userId);
        Integer goodsCount = 0;
        BigDecimal goodsAmount = new BigDecimal(0.00);
        Integer checkedGoodsCount = 0;
        BigDecimal checkedGoodsAmount = new BigDecimal(0.00);
        for (LitemallCart cart : cartList) {
            LitemallShop shop = litemallShopService.findById(cart.getShopId());
            if(shop != null){
                cart.setShopName(shop.getName());
            }
            goodsCount += cart.getNumber();
            goodsAmount = goodsAmount.add(cart.getPrice().multiply(new BigDecimal(cart.getNumber())));
            if (cart.getChecked()) {
                checkedGoodsCount += cart.getNumber();
                checkedGoodsAmount = checkedGoodsAmount.add(cart.getPrice().multiply(new BigDecimal(cart.getNumber())));
            }
        }
        Map<String, Object> cartTotal = new HashMap<>();
        cartTotal.put("goodsCount", goodsCount);
        cartTotal.put("goodsAmount", goodsAmount);
        cartTotal.put("checkedGoodsCount", checkedGoodsCount);
        cartTotal.put("checkedGoodsAmount", checkedGoodsAmount);

        Map<String, Object> result = new HashMap<>();
        result.put("cartList", cartList);
        result.put("cartTotal", cartTotal);

        return ResponseUtil.ok(result);
    }

    /**
     * 加入商品到购物车
     * <p>
     * 如果已经存在购物车货品，则增加数量；
     * 否则添加新的购物车货品项。
     *
     * @param userId 用户ID
     * @param cart   购物车商品信息， { goodsId: xxx, productId: xxx, number: xxx }
     * @return 加入购物车操作结果
     */
    @PostMapping("add")
    @LogAnno
    public Object add(@LoginUser Integer userId, @RequestBody LitemallCart cart) {
        if (userId == null) {
            return ResponseUtil.unlogin();
        }
        if (cart == null) {
            return ResponseUtil.badArgument();
        }
        Integer shopId = cart.getShopId();
        Integer productId = cart.getProductId();
        Integer number = cart.getNumber().intValue();
        Integer goodsId = cart.getGoodsId();
        Integer[] specIds = cart.getSpecificationIds();
        if (!ObjectUtils.allNotNull(productId, number, goodsId, shopId)) {
            return ResponseUtil.badArgument();
        }
        if(number <= 0){
            return ResponseUtil.badArgument();
        }

        //判断商品是否可以购买
        LitemallGoods goods = goodsService.findById(goodsId);
        if (goods == null || !goods.getIsOnSale()) {
            return ResponseUtil.fail(GOODS_UNSHELVE, "商品已下架");
        }

        LitemallGoodsProduct product = productService.findById(productId);
        //判断购物车中是否存在此规格商品
        LitemallCart existCart = cartService.queryExist(goodsId, productId, userId, specIds);
        if (existCart == null) {
            //取得规格的信息,判断规格库存
            if (product == null || number > product.getNumber()) {
                return ResponseUtil.fail(GOODS_NO_STOCK, "库存不足");
            }

            cart.setId(null);
            cart.setShopId(shopId);
            cart.setGoodsSn(goods.getGoodsSn());
            cart.setGoodsName((goods.getName()));
            cart.setPicUrl(goods.getPicUrl());
            BigDecimal sellPrice = product.getSellPrice();
            List<String> specifications = new ArrayList<>();
            if(null != specIds && specIds.length > 0 ){
                cart.setSpecificationIds(specIds);
                List<LitemallGoodsSpecification> litemallGoodsSpecifications = specificationService.queryByIds(specIds);
                for(LitemallGoodsSpecification item : litemallGoodsSpecifications){
                    sellPrice = sellPrice.add(item.getPrice());
                    specifications.add(item.getValue());
                }
            }
            cart.setPrice(sellPrice);
            cart.setSpecifications(specifications.toArray(new String[]{}));
            cart.setSpecificationIds(specIds);
            cart.setUserId(userId);
            cart.setTaxPrice(product.getTax().divide(new BigDecimal(100.00)).multiply(sellPrice));
            cart.setChecked(true);
            cartService.add(cart);
        } else {
            //取得规格的信息,判断规格库存
            int num = existCart.getNumber() + number;
            if (num > product.getNumber()) {
                return ResponseUtil.fail(GOODS_NO_STOCK, "库存不足");
            }
            existCart.setNumber((short) num);
            if (cartService.updateById(existCart) == 0) {
                return ResponseUtil.updatedDataFailed();
            }
        }

        return goodscount(userId);
    }

    /**
     * 立即购买
     * <p>
     * 和add方法的区别在于：
     * 1. 如果购物车内已经存在购物车货品，前者的逻辑是数量添加，这里的逻辑是数量覆盖
     * 2. 添加成功以后，前者的逻辑是返回当前购物车商品数量，这里的逻辑是返回对应购物车项的ID
     *
     * @param userId 用户ID
     * @param cart   购物车商品信息， { goodsId: xxx, productId: xxx, number: xxx }
     * @return 立即购买操作结果
     */
    @PostMapping("fastadd")
    @LogAnno
    public Object fastadd(@LoginUser Integer userId, @RequestBody LitemallCart cart) {
        if (userId == null) {
            return ResponseUtil.unlogin();
        }
        if (cart == null) {
            return ResponseUtil.badArgument();
        }

        Integer shopId = cart.getShopId();
        Integer productId = cart.getProductId();
        Integer number = cart.getNumber().intValue();
        Integer goodsId = cart.getGoodsId();
        Integer[] specIds = cart.getSpecificationIds();
        if (!ObjectUtils.allNotNull(productId, number, goodsId, shopId)) {
            return ResponseUtil.badArgument();
        }
        if(number <= 0){
            return ResponseUtil.badArgument();
        }

        //判断商品是否可以购买
        LitemallGoods goods = goodsService.findById(goodsId);
        if (goods == null || !goods.getIsOnSale()) {
            return ResponseUtil.fail(GOODS_UNSHELVE, "商品已下架");
        }

        LitemallGoodsProduct product = productService.findById(productId);
        //判断购物车中是否存在此规格商品
        LitemallCart existCart = cartService.queryExist(goodsId, productId, userId, specIds);
        if (existCart == null) {
            //取得规格的信息,判断规格库存
            if (product == null || number > product.getNumber()) {
                return ResponseUtil.fail(GOODS_NO_STOCK, "库存不足");
            }

            cart.setId(null);
            cart.setShopId(shopId);
            cart.setGoodsSn(goods.getGoodsSn());
            cart.setGoodsName((goods.getName()));
            cart.setPicUrl(goods.getPicUrl());
            BigDecimal sellPrice = product.getSellPrice();
            List<String> specifications = new ArrayList<>();
            if(null != specIds && specIds.length > 0 ){
                cart.setSpecificationIds(specIds);
                List<LitemallGoodsSpecification> litemallGoodsSpecifications = specificationService.queryByIds(specIds);
                for(LitemallGoodsSpecification item : litemallGoodsSpecifications){
                    sellPrice = sellPrice.add(item.getPrice());
                    specifications.add(item.getValue());
                }
            }
            cart.setPrice(sellPrice);
            cart.setTaxPrice(product.getTax().divide(new BigDecimal(100.00)).multiply(sellPrice));
            cart.setSpecifications(specifications.toArray(new String[]{}));
            cart.setSpecificationIds(specIds);
            cart.setUserId(userId);
            cart.setChecked(true);
            cartService.add(cart);
        } else {
            //取得规格的信息,判断规格库存
            int num = number;
            if (num > product.getNumber()) {
                return ResponseUtil.fail(GOODS_NO_STOCK, "库存不足");
            }
            existCart.setNumber((short) num);
            if (cartService.updateById(existCart) == 0) {
                return ResponseUtil.updatedDataFailed();
            }
        }

        return ResponseUtil.ok(existCart != null ? existCart.getId() : cart.getId());
    }

    /**
     * 修改购物车商品货品数量
     *
     * @param userId 用户ID
     * @param cart   购物车商品信息， { id: xxx, goodsId: xxx, productId: xxx, number: xxx }
     * @return 修改结果
     */
    @PostMapping("update")
    @LogAnno
    public Object update(@LoginUser Integer userId, @RequestBody LitemallCart cart) {
        if (userId == null) {
            return ResponseUtil.unlogin();
        }
        if (cart == null) {
            return ResponseUtil.badArgument();
        }
        Integer productId = cart.getProductId();
        Integer number = cart.getNumber().intValue();
        Integer goodsId = cart.getGoodsId();
        Integer id = cart.getId();
        if (!ObjectUtils.allNotNull(id, productId, number, goodsId)) {
            return ResponseUtil.badArgument();
        }
        if(number <= 0){
            return ResponseUtil.badArgument();
        }

        //判断是否存在该订单
        // 如果不存在，直接返回错误
        LitemallCart existCart = cartService.findById(id);
        if (existCart == null) {
            return ResponseUtil.badArgumentValue();
        }

        // 判断goodsId和productId是否与当前cart里的值一致
        if (!existCart.getGoodsId().equals(goodsId)) {
            return ResponseUtil.badArgumentValue();
        }
        if (!existCart.getProductId().equals(productId)) {
            return ResponseUtil.badArgumentValue();
        }

        //判断商品是否可以购买
        LitemallGoods goods = goodsService.findById(goodsId);
        if (goods == null || !goods.getIsOnSale()) {
            return ResponseUtil.fail(GOODS_UNSHELVE, "商品已下架");
        }

        //取得规格的信息,判断规格库存
        LitemallGoodsProduct product = productService.findById(productId);
        if (product == null || product.getNumber() < number) {
            return ResponseUtil.fail(GOODS_UNSHELVE, "库存不足");
        }

        existCart.setNumber(number.shortValue());
        if (cartService.updateById(existCart) == 0) {
            return ResponseUtil.updatedDataFailed();
        }
        return ResponseUtil.ok();
    }

    /**
     * 购物车商品货品勾选状态
     * <p>
     * 如果原来没有勾选，则设置勾选状态；如果商品已经勾选，则设置非勾选状态。
     *
     * @param userId 用户ID
     * @param body   购物车商品信息， { productIds: xxx, isChecked: 1/0 }
     * @return 购物车信息
     */
    @PostMapping("checked")
    @LogAnno
    public Object checked(@LoginUser Integer userId, @RequestBody String body) {
        if (userId == null) {
            return ResponseUtil.unlogin();
        }
        if (body == null) {
            return ResponseUtil.badArgument();
        }

        List<Integer> cartIds = JacksonUtil.parseIntegerList(body, "cartIds");
        if (cartIds == null) {
            return ResponseUtil.badArgument();
        }

        Integer checkValue = JacksonUtil.parseInteger(body, "isChecked");
        if (checkValue == null) {
            return ResponseUtil.badArgument();
        }
        Boolean isChecked = (checkValue == 1);

        cartService.updateCheck(userId, cartIds, isChecked);
        return index(userId);
    }

    /**
     * 购物车商品删除
     *
     * @param userId 用户ID
     * @param body   购物车商品信息， { cartIds: xxx }
     * @return 购物车信息
     * 成功则
     * {
     * errno: 0,
     * errmsg: '成功',
     * data: xxx
     * }
     * 失败则 { errno: XXX, errmsg: XXX }
     */
    @PostMapping("delete")
    @LogAnno
    public Object delete(@LoginUser Integer userId, @RequestBody String body) {
        if (userId == null) {
            return ResponseUtil.unlogin();
        }
        if (body == null) {
            return ResponseUtil.badArgument();
        }

        List<Integer> cartIds = JacksonUtil.parseIntegerList(body, "cartIds");

        if (cartIds == null || cartIds.size() == 0) {
            return ResponseUtil.badArgument();
        }

        cartService.delete(cartIds, userId);
        return index(userId);
    }

    /**
     * 购物车商品货品数量
     * <p>
     * 如果用户没有登录，则返回空数据。
     *
     * @param userId 用户ID
     * @return 购物车商品货品数量
     */
    @GetMapping("goodscount")
    @LogAnno
    public Object goodscount(@LoginUser Integer userId) {
        if (userId == null) {
            return ResponseUtil.ok(0);
        }

        int goodsCount = 0;
        List<LitemallCart> cartList = cartService.queryByUid(userId);
        for (LitemallCart cart : cartList) {
            goodsCount += cart.getNumber();
        }

        return ResponseUtil.ok(goodsCount);
    }

    /**
     * 购物车下单
     *
     * @param userId    用户ID
     * @param cartIds    购物车商品ID：
     *                  如果购物车商品ID是空，则下单当前用户所有购物车商品；
     *                  如果购物车商品ID非空，则只下单当前购物车商品。
     * @param addressId 收货地址ID：
     *                  如果收货地址ID是空，则查询当前用户的默认地址。
     * @param couponId  优惠券ID：
     *                  如果优惠券ID是空，则自动选择合适的优惠券。
     * @return 购物车操作结果
     */
    @GetMapping("checkout")
    @LogAnno
    public Object checkout(@LoginUser Integer userId, @RequestParam("cartIds[]") ArrayList<Integer> cartIds, Integer addressId, Integer couponId, HttpServletRequest request) {
        if (userId == null) {
            return ResponseUtil.unlogin();
        }

        // 收货地址
        LitemallAddress checkedAddress = null;
        if (addressId == null || addressId.equals(0)) {
            checkedAddress = addressService.findDefault(userId);
            // 如果仍然没有地址，则是没有收获地址
            // 返回一个空的地址id=0，这样前端则会提醒添加地址
            if (checkedAddress == null) {
                checkedAddress = new LitemallAddress();
                checkedAddress.setId(0);
                addressId = 0;
            } else {
                addressId = checkedAddress.getId();
            }

        } else {
            checkedAddress = addressService.query(userId, addressId);
            // 如果null, 则报错
            if (checkedAddress == null) {
                return ResponseUtil.badArgumentValue();
            }
        }


        // 商品价格
        List<CartVo> checkedGoodsList = new ArrayList<>();
        List<LitemallCart> litemallCarts = new ArrayList<>();
        if (cartIds == null || cartIds.size() == 0 ) {
            litemallCarts = cartService.queryByUidAndChecked(userId);
        } else {
            litemallCarts = cartService.findByIds(cartIds);
        }
        for(LitemallCart cart : litemallCarts){
            CartVo vo = new CartVo();
            BeanUtils.copyProperties(cart, vo);
            checkedGoodsList.add(vo);
        }

        Map<String,Object> rtn = new HashMap<>();
        List<Map<String,Object>> list = new ArrayList<>();
        List<Integer> shopIds = checkedGoodsList.stream().map(CartVo::getShopId).collect(Collectors.toList());
        List<LitemallShop> shops = litemallShopService.getByIds(shopIds);
        //按门店分组
        Map<LitemallShop, List<CartVo>> groupShop = checkedGoodsList.stream().collect(Collectors
                .groupingBy(cart -> {
                    return shops.stream().filter(shop -> {
                        return shop.getId() == cart.getShopId();
                    }).findFirst().get();
                }, Collectors.toList()));

        //获取所有订单总价、税收总价

        BigDecimal totalPrice = new BigDecimal(0.00);
        BigDecimal totalTaxPrice = new BigDecimal(0.00);

        Set<Map.Entry<LitemallShop, List<CartVo>>> entries = groupShop.entrySet();
        Iterator<Map.Entry<LitemallShop, List<CartVo>>> iterator = entries.iterator();
        while(iterator.hasNext()){
            Map.Entry<LitemallShop, List<CartVo>> i = iterator.next();
            LitemallShop shop = i.getKey();
            /**
             * 获取门店对应的税费率
             */
            List<LitemallShopRegion> shopRegions = litemallShopRegionService.queryByShopId(shop.getId());
            List<LitemallTax> litemallTaxes = litemallTaxService.queryByRegionIds(shopRegions.stream().map(LitemallShopRegion::getRegionId).collect(Collectors.toList()));
            /**
             * 获取总税率/100
             */
            BigDecimal tax = new BigDecimal(0.00);
            for(LitemallTax item : litemallTaxes){
                tax = tax.add(item.getValue().divide(new BigDecimal(100.00)));
            }

            List<CartVo> carts = i.getValue();
            /**
             * 税费和总价
             */
            BigDecimal taxPrice = new BigDecimal(0.00);
            BigDecimal checkedGoodsPrice = new BigDecimal(0.00);

            for (CartVo cart : carts) {
                checkedGoodsPrice = checkedGoodsPrice.add(cart.getPrice().multiply(new BigDecimal(cart.getNumber())));

            }
            /**
             * 计算 商品总税费 = 商品总价 * 税率
             */
            taxPrice = taxPrice.add(tax.multiply(checkedGoodsPrice));

            // 计算优惠券可用情况
            int tmpCouponLength = 0;
            List<Integer> cartIds1 = carts.stream().map(CartVo::getId).collect(Collectors.toList());
            BigDecimal tmpCouponPrice = new BigDecimal(0.00);
            Integer tmpCouponId = 0;
            List<LitemallCouponUser> couponUserList = couponUserService.queryAll(userId);
            for(LitemallCouponUser couponUser : couponUserList){
                //检查优惠券是否可用
                LitemallCoupon coupon = couponVerifyService.checkCoupon(userId, couponUser.getCouponId(), cartIds1);
                if(coupon != null){
                    tmpCouponLength++;
                    //默认选中第一个符合条件的优惠券,并且判断是否上一个门店订单是否选中优惠券，如果有，则需要判断数量，数量不够时不能选择同一张优惠券
                    boolean match = list.stream().anyMatch(item -> {
                        return item.get("shopCouponId") != null && item.get("shopCouponId") == coupon.getId();
                    });
                    if(!match && tmpCouponId != 0){
                        if(coupon.getDiscountType() == Constants.DISCOUNT_TYPE_RATE){
                            tmpCouponPrice = checkedGoodsPrice.divide(new BigDecimal(100 - coupon.getDiscountRate()));
                        }else{
                            tmpCouponPrice = coupon.getDiscount();
                        }
                        tmpCouponId = coupon.getId();
                    }
                }
            }


            // 获取优惠券减免金额，优惠券可用数量
            int availableCouponLength = tmpCouponLength;

            // 根据订单商品总价计算运费，满88则免运费，否则8元；
            BigDecimal freightPrice = new BigDecimal(0.00);
            if (checkedGoodsPrice.compareTo(SystemConfig.getFreightLimit()) < 0) {
                freightPrice = SystemConfig.getFreight();
            }

            // 订单费用
            BigDecimal orderTotalPrice = checkedGoodsPrice.max(new BigDecimal(0.00)).add(taxPrice).add(freightPrice);

            BigDecimal actualPrice = orderTotalPrice;
            //添加到总价
            totalPrice = totalPrice.add(actualPrice);
            totalTaxPrice = totalTaxPrice.add(taxPrice);

            Map<String, Object> data = new HashMap<>();
            data.put("availableCouponLength", availableCouponLength);
            data.put("shopGoodsTotalPrice", checkedGoodsPrice);
            data.put("shopOrderTotalPrice", orderTotalPrice);
            data.put("shopActualPrice", actualPrice);
            data.put("shopCheckedGoodsList", checkedGoodsList.stream().filter(goods->{
                return shop.getId() == goods.getShopId();
            }));
            data.put("shopTaxTotalPrice",taxPrice);
            data.put("shopFreightPrice", freightPrice);
            data.put("shopCouponPrice", 0.00);
            data.put("shopCouponId", couponId);
            data.put("couponPrice", tmpCouponPrice);
            data.put("shop", shop);

            list.add(data);
        }

        rtn.put("checkedAddress", checkedAddress);
        rtn.put("orderTotalPrice",totalPrice);
        rtn.put("taxTotalPrice",totalTaxPrice);
        rtn.put("list",list);

        return ResponseUtil.ok(rtn);


    }
}