package org.linlinjava.litemall.admin.erp.rq;

/**
 * @author ：stephen
 * @date ：Created in 2020/7/1 11:37
 * @description：对接erp Material request
 */
public class MaterialRQ extends BaseRQ {

    private String apiName;
    private String name;
    private Integer currentPage = 1;
    private Integer pageSize = 10;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getApiName() {
        return apiName;
    }

    public void setApiName(String apiName) {
        this.apiName = apiName;
    }

    public Integer getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(Integer currentPage) {
        this.currentPage = currentPage;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }
}
