package com.ce.nw.oauth2.domain;

/**
 * @author yuit
 * @date   2018/3/30 20:37
 *
 */
public  class BaseResponse  {

    private int status;
    private String msg;

    protected BaseResponse() {
    }

    protected BaseResponse(int status, String msg) {
        this.status = status;
        this.msg = msg;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
