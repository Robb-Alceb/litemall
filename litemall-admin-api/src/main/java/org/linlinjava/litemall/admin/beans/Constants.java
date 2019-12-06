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
     * 新增商品
     */
    public static final String CREATE_GOODS="新增商品：";
    /**
     * 修改商品
     */
    public static final String UPDATE_GOODS="修改商品：";
    /**
     * 修改商品
     */
    public static final String DELETE_GOODS="删除商品：";
    /**
     * 商品上架
     */
    public static final String GOODS_PUSH="商品上架：";
    /**
     * 商品下架
     */
    public static final String GOODS_PUSH_NOT="商品下架：";
    /**
     * 商品上架
     */
    public static final String GOODS_NEW="商品新品：";
    /**
     * 商品下架
     */
    public static final String GOODS_NEW_NOT="商品非新品：";
    /**
     * 商品上架
     */
    public static final String GOODS_COMMENTED="商品推荐：";
    /**
     * 商品下架
     */
    public static final String GOODS_COMMENTED_NOT="商品非推荐：";
    /**
     * 订单申请
     */
    public static final String ORDER_APPLYING="订单申请";
    /**
     * 同意调货
     */
    public static final String ORDER_PASS="同意调货";
    /**
     * 不同意调货
     */
    public static final String ORDER_NO_PASS="不同意调货";
    /**
     * 支付货款
     */
    public static final String ORDER_PAY="支付货款";
    /**
     * 同意发货
     */
    public static final String DELIVER_GOODS="同意发货";
    /**
     * 取消发货
     */
    public static final String CANCEL_DELIVER_GOODS="取消发货";
    /**
     * 确认收货
     */
    public static final String TAKE_DELIVERY="确认收货";




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
