package org.linlinjava.litemall.admin.beans.condition;

import lombok.Data;

import java.util.Date;

@Data
public class ShopCondition {
    private String name;
    private String address;
    private Date addTimeFrom;
    private Date addTimeTo;
    private Integer status;
}
