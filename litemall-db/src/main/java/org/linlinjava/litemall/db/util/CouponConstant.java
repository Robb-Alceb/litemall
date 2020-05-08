package org.linlinjava.litemall.db.util;

public class CouponConstant {
    public static final Short TYPE_COMMON = 0;
    public static final Short TYPE_REGISTER = 1;
    public static final Short TYPE_CODE = 2;

    //优惠券商品类型
    public static final Short GOODS_TYPE_ALL = 0;       //全场通用
    public static final Short GOODS_TYPE_CATEGORY = 1;  //指定类型
    public static final Short GOODS_TYPE_ARRAY = 2;     //指定商品

    public static final Short STATUS_NORMAL = 0;
    public static final Short STATUS_USED = 1;
    public static final Short STATUS_EXPIRED = 2;
    public static final Short STATUS_OUT = 3;

    public static final Short TIME_TYPE_DAYS = 0;
    public static final Short TIME_TYPE_TIME = 1;
}
