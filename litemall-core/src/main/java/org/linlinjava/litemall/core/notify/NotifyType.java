package org.linlinjava.litemall.core.notify;

public enum NotifyType {
    PAY_SUCCEED("paySucceed"),
    SHIP("ship"),
    REFUND("refund"),
    SUBSCRIBE_PAY_SUCCEED("subscribePaySucceed"),
    SUBSCRIBE_PAY_ERROR("subscribePayError"),
    CAPTCHA("captcha");

    private String type;

    NotifyType(String type) {
        this.type = type;
    }

    public String getType() {
        return this.type;
    }
}
