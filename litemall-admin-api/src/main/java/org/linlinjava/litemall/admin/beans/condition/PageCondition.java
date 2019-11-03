package org.linlinjava.litemall.admin.beans.condition;

import lombok.Data;

@Data
public class PageCondition {
    private int page;
    private int limit;
    private String sort;
    private String order;
}
