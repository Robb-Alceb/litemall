package org.linlinjava.litemall.core.payment;

/**
 * @author ：stephen
 * @date ：Created in 11/23/2019 5:21 PM
 * @description：TODO
 */
public enum DefaultCurType {

    CNY("人民币"),
    USD("美元"),
    HKD("港币"),
    MOP("澳门元"),
    EUR("欧元"),
    TWD("新台币"),
    KRW("韩元"),
    JPY("日元"),
    SGD("新加坡元"),
    AUD("澳大利亚元");
    /**
     * 币种名称
     */
    private String name;

    /**
     * 构造函数
     * @param name
     */
    DefaultCurType(String name) {
        this.name = name;
    }

    /**
     * 获取货币类型
     *
     * @return 货币类型
     */
    public String getType() {
        return this.name();
    }
    /**
     * 货币名称
     *
     * @return 货币名称
     */
    public String getName() {
        return name;
    }
}
