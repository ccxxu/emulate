package com.ce.nw.auth.response;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author yuit
 * @date   2018/3/30 20:37
 *
 */
public class PageAndSortResponse extends BaseResponse {

    private Integer currentPage;
    private Integer pageSize;
    private long count;
    List items;

    protected PageAndSortResponse(){}

    public Integer getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(Integer currentPage) {
        this.currentPage = currentPage;
    }

    public long getCount() {
        return count;
    }

    public void setCount(long count) {
        this.count = count;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public List getItems() {
        return items;
    }

    public void setItems(List items) {
        this.items = items;
    }
}
