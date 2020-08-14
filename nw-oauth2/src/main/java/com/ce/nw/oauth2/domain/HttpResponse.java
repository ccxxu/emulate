package com.ce.nw.oauth2.domain;

import com.ce.nw.common.entity.HttpStatusAndMsg;

/**
 * @author yuit
 * @date  2018/3/30 20:37
 *
 */
public final class HttpResponse {

    /**--------------------------BaseResponse-------------------------------------------------*/
    public  static BaseResponse baseResponse(int status) {
        return baseResponse(status, null);
    }

    public  static BaseResponse baseResponse(int status, String msg) {

        if (msg != null) {
            return new BaseResponse(status, msg);
        } else {
            return new BaseResponse(status, HttpStatusAndMsg.exs.get(status));
        }
    }

    public static BaseResponse successResponse(){
        return baseResponse(200);
    }

    public static BaseResponse successResponse(String msg){
        return baseResponse(200,msg);
    }

}
