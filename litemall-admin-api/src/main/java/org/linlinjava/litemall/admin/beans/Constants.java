package org.linlinjava.litemall.admin.beans;

public class Constants extends org.linlinjava.litemall.db.beans.Constants {

    /**
     * 修改成员权限
     */
    public static final String UPDATE_PERMISSION="修改成员权限：";

    /**
     * 修改门店信息
     */
    public static final String UPDATE_SHOP="修改门店基础信息：";

    /**
     * 新增门店
     */
    public static final String CREATE_SHOP="新增门店：";

    /**
     * 添加店长权限
     */
    public static final String ADD_SHOPKEEPER="添加店长权限：";

    /**
     * 添加经理权限
     */
    public static final String ADD_MANAGER="添加经理权限：";

    /**
     * 删除门店
     */
    public static final String DELETE_MANAGER="删除门店：";



    /**
     * 商品审核状态：（1：待审核；2：已审核；3：未通过）
     */
    public static final Integer GOODS_REVIEW_PENDING = 1;
    public static final Integer GOODS_REVIEW_APPROVE = 2;
    public static final Integer GOODS_REVIEW_REJECT = 3;
    /**
     * 商品日志类型：（1：审核日志；2:普通日志）
     */
    public static final Integer GOODS_LOG_REVIEW = 1;
    public static final Integer GOODS_LOG_COMMON = 2;

    /**
     * 日志信息enum
     */
    public enum LogMessage {
        //商品审核日志
        GOODS_REVIEW_PENDING_LOG("待审核:"),
        GOODS_REVIEW_APPROVE_LOG( "已审核:"),
        GOODS_REVIEW_REJECT_LOG("未通过:");


        private String value;

        LogMessage(String value){
            this.value = value;
        }

        public String getValue(){
            return this.value;
        }
    }
}
