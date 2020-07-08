package org.linlinjava.litemall.admin.erp.rq;

import java.math.BigDecimal;

/**
 * @author ：stephen
 * @date ：Created in 2020/7/1 15:43
 * @description：TODO
 */
public class PurchaseInfo {

    private String Type = "门店采购";
    private String SubType = "销售订单";
    private String OrganId;
    private String Salesman;
    private String AccountId;
    private String PayType = "现付";
    private String Remark;
    private BigDecimal TotalPrice;

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }

    public String getSubType() {
        return SubType;
    }

    public void setSubType(String subType) {
        SubType = subType;
    }

    public String getOrganId() {
        return OrganId;
    }

    public void setOrganId(String organId) {
        OrganId = organId;
    }

    public String getSalesman() {
        return Salesman;
    }

    public void setSalesman(String salesman) {
        Salesman = salesman;
    }

    public String getAccountId() {
        return AccountId;
    }

    public void setAccountId(String accountId) {
        AccountId = accountId;
    }

    public String getPayType() {
        return PayType;
    }

    public void setPayType(String payType) {
        PayType = payType;
    }

    public String getRemark() {
        return Remark;
    }

    public void setRemark(String remark) {
        Remark = remark;
    }

    public BigDecimal getTotalPrice() {
        return TotalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        TotalPrice = totalPrice;
    }
}
