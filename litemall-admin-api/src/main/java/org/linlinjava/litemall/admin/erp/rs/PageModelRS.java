package org.linlinjava.litemall.admin.erp.rs;

import java.util.List;

/**
 * @author ：stephen
 * @date ：Created in 2020/7/1 11:49
 * @description：TODO
 */
public class PageModelRS<T> extends BaseRS{

    private Long total;
    private List<T> rows;

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    public List<T> getRows() {
        return rows;
    }

    public void setRows(List<T> rows) {
        this.rows = rows;
    }
}
