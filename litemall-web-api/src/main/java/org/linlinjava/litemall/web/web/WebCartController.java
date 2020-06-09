package org.linlinjava.litemall.web.web;
import org.apache.commons.lang3.ObjectUtils;
import org.linlinjava.litemall.core.util.JacksonUtil;
import org.linlinjava.litemall.core.util.ResponseUtil;
import org.linlinjava.litemall.db.domain.*;
import org.linlinjava.litemall.db.service.*;
import org.linlinjava.litemall.web.annotation.LogAnno;
import org.linlinjava.litemall.web.annotation.LoginShop;
import org.linlinjava.litemall.web.annotation.LoginUser;
import org.linlinjava.litemall.web.dto.CartDto;
import org.linlinjava.litemall.web.service.WebCartService;
import org.linlinjava.litemall.web.util.WebResponseEnum;
import org.linlinjava.litemall.web.vo.AccessoryVo;
import org.linlinjava.litemall.web.vo.CartTaxVo;
import org.linlinjava.litemall.web.vo.CartVo;
import org.linlinjava.litemall.web.vo.GoodsDetailVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

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

    @Autowired
    private WebCartService webCartService;

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

        return webCartService.index(userId);
    }

    /**
     * 加入商品到购物车
     * <p>
     * 如果已经存在购物车货品，则增加数量；
     * 否则添加新的购物车货品项。
     *
     * @param userId 用户ID
     * @param cartDto   购物车商品信息， { goodsId: xxx, productId: xxx, number: xxx }
     * @return 加入购物车操作结果
     */
    @PostMapping("add")
    @LogAnno
    public Object add(@LoginShop Integer shopId, @LoginUser Integer userId, @RequestBody CartDto cartDto) {
        if (userId == null) {
            return ResponseUtil.unlogin();
        }
        return webCartService.add(shopId, userId, cartDto);
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
        return webCartService.update(userId, cart);
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
        return webCartService.delete(userId, body);
    }

    @PostMapping("clear")
    @LogAnno
    public Object clear(@LoginUser Integer userId) {
        if (userId == null) {
            return ResponseUtil.unlogin();
        }
        return webCartService.clear(userId);
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
        return webCartService.goodscount(userId);
    }

    @GetMapping("checkout")
    @LogAnno
    public Object checkout(@LoginShop Integer shopId, @LoginUser Integer userId) {
        if (userId == null) {
            return ResponseUtil.unlogin();
        }
        return webCartService.checkout(shopId, userId);
    }
}
