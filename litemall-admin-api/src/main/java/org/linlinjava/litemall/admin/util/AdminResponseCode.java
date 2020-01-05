package org.linlinjava.litemall.admin.util;

public class AdminResponseCode {
    //用户名无效
    public static final Integer ADMIN_INVALID_NAME = 601;
    //密码无效
    public static final Integer ADMIN_INVALID_PASSWORD = 602;
    //用户名已存在
    public static final Integer ADMIN_NAME_EXIST = 602;
    //不允许修改用户
    public static final Integer ADMIN_ALTER_NOT_ALLOWED = 603;
    //不能删除
    public static final Integer ADMIN_DELETE_NOT_ALLOWED = 604;
    //无效的账号
    public static final Integer ADMIN_INVALID_ACCOUNT = 605;
    //商品不能更新
    public static final Integer GOODS_UPDATE_NOT_ALLOWED = 610;
    //商品名已存在
    public static final Integer GOODS_NAME_EXIST = 611;
    //商品不存在
    public static final Integer GOODS_NOT_EXIST = 612;
    //商品不允许删除
    public static final Integer GOODS_NOT_ALLOW_DELETE = 617;
    //无权处理该商品
    public static final Integer GOODS_NOT_PERMISSION = 613;
    //有子分类不能删除
    public static final Integer GOODS_CATEGORY_HAS_CHILDREN = 614;
    //分类下有商品不能删除
    public static final Integer GOODS_CATEGORY_HAS_GOODS = 615;
    //商品暂未审核
    public static final Integer GOODS_NOT_REVIEW = 612;
    //订单不能确认
    public static final Integer ORDER_CONFIRM_NOT_ALLOWED = 620;
    //订单退款错误
    public static final Integer ORDER_REFUND_FAILED = 621;
    //订单已回复
    public static final Integer ORDER_REPLY_EXIST = 622;
    //订单不存在
    public static final Integer ORDER_NOT_EXIST = 623;
    //无权处理该订单
    public static final Integer ORDER_NOT_PERMISSION = 624;
    public static final Integer USER_INVALID_NAME = 630;
    public static final Integer USER_INVALID_PASSWORD = 631;
    public static final Integer USER_INVALID_MOBILE = 632;
    public static final Integer USER_NAME_EXIST = 633;
    public static final Integer USER_MOBILE_EXIST = 634;
    //角色名已存在
    public static final Integer ROLE_NAME_EXIST = 640;
    //超级权限不能修改
    public static final Integer ROLE_SUPER_SUPERMISSION = 641;
    //当前角色存在管理员
    public static final Integer ROLE_USER_EXIST = 642;
    //当前角色不允许删除
    public static final Integer ROLE_NOT_ALLOW_DELETE = 643;

    //门店不允许删除
    public static final Integer SHOP_NOT_ALLOW_DELETE = 650;
    //门店已存在店长
    public static final Integer SHOP_HAS_SHOPKEEPER = 651;
    //门店已存在经理
    public static final Integer SHOP_HAS_MANAGER = 652;
}
