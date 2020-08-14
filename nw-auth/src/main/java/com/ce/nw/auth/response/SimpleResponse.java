package com.ce.nw.auth.response;

import lombok.Getter;
import lombok.Setter;

/**
 * @author yuit
 * @date  2018/3/30 20:37
 *
 */
@Getter
@Setter
public class SimpleResponse extends BaseResponse {

    private Object item;

    protected SimpleResponse() {
    }

    protected SimpleResponse(int status, String msg, Object item) {
        super(status, msg);
        this.item = item;
    }

    public Object getItem() {
        return item;
    }

    public void setItem(Object item) {
        this.item = item;
    }
}
