package com.ce.nw.auth.response;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author yuit
 * @date   2018/3/30 20:37
 *
 */
@Getter
@Setter
public class ListResponse extends BaseResponse {

    private long count;
    private List items;

    protected ListResponse(){

    }

    public long getCount() {
        return count;
    }

    public void setCount(long count) {
        this.count = count;
    }

    public List getItems() {
        return items;
    }

    public void setItems(List items) {
        this.items = items;
    }
}
