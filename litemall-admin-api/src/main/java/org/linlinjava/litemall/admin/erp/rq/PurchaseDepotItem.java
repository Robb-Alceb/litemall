package org.linlinjava.litemall.admin.erp.rq;

/**
 * @author ：stephen
 * @date ：Created in 2020/7/1 16:16
 * @description：TODO
 */
public class PurchaseDepotItem {
    private String MaterialId;
    private String OperNumber;
    private String Unit;
    private String Remark;

    public String getMaterialId() {
        return MaterialId;
    }

    public void setMaterialId(String materialId) {
        MaterialId = materialId;
    }

    public String getOperNumber() {
        return OperNumber;
    }

    public void setOperNumber(String operNumber) {
        OperNumber = operNumber;
    }

    public String getUnit() {
        return Unit;
    }

    public void setUnit(String unit) {
        Unit = unit;
    }

    public String getRemark() {
        return Remark;
    }

    public void setRemark(String remark) {
        Remark = remark;
    }
}
