package com.ce.nw.test.domain;


import java.io.Serializable;

/**
 * @author ChengJianSheng
 * @date 2019-02-16
 */
public class BaseResponse<T> implements Serializable {
    private static final long serialVersionUID = -4272342803400464446L;

    public static final int SUCCESS_CODE = 200;
    public static final int FAILURE_CODE = 500;

    private int code;

    private String message;

    private boolean success;

    private T data;

    public BaseResponse(T data) {
        this.code = SUCCESS_CODE;
        this.success = true;
        this.data = data;
    }

    public BaseResponse(int code, String message, T data) {
        this.code = code;
        this.message = message;
        this.success = code == SUCCESS_CODE ? true : false;
        this.data = data;
    }


    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
