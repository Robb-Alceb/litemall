package org.linlinjava.litemall.web.web;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.linlinjava.litemall.core.util.JacksonUtil;
import org.linlinjava.litemall.core.util.ResponseUtil;
import org.linlinjava.litemall.db.domain.*;
import org.linlinjava.litemall.db.service.*;
import org.linlinjava.litemall.web.annotation.LoginShop;
import org.linlinjava.litemall.web.annotation.LoginUser;
import org.omg.CORBA.INTERNAL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.*;

import static org.linlinjava.litemall.web.util.WebResponseCode.*;

/**
 * @Author: stephen
 * @Date: 2020/2/14 16:34
 * @Version: 1.0
 * @Description: 购物车
 */


@RestController
@RequestMapping("/web/cart")
@Validated
public class WebCartController {
    private final Log logger = LogFactory.getLog(WebCartController.class);

    @Autowired
    private LitemallCartService cartService;
    @Autowired
    private LitemallGoodsService goodsService;
    @Autowired
    private LitemallGoodsProductService productService;
    @Autowired
    private LitemallGoodsSpecificationService specificationService;

    /**
     * 用户购物车信息
     *
     * @param userId 用户ID
     * @return 用户购物车信息
     */
    @GetMapping("index")
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
    public Object add(@LoginShop Integer shopId, @LoginUser Integer userId, @RequestBody LitemallCart cart) {
        if (userId == null) {
            return ResponseUtil.unlogin();
        }
        if (cart == null) {
            return ResponseUtil.badArgument();
        }
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
                List<LitemallGoodsSpecification> litemallGoodsSpecifications = specificationService.findByIds(specIds);
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
     * 修改购物车商品货品数量
     *
     * @param userId 用户ID
     * @param cart   购物车商品信息， { id: xxx, goodsId: xxx, productId: xxx, number: xxx }
     * @return 修改结果
     */
    @PostMapping("update")
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

        //减少为0
        if(number == 0){
            cartService.deleteById(id);
            return goodscount(userId);
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
        return goodscount(userId);
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

    @PostMapping("clear")
    public Object clear(@LoginUser Integer userId) {
        if (userId == null) {
            return ResponseUtil.unlogin();
        }
        cartService.delete(userId);
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
}
