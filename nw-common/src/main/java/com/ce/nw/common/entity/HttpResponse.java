package com.ce.nw.common.entity;

import static com.ce.nw.common.entity.HttpStatusAndMsg.exs;

/**
 * @ClassName HttpResponse
 * @Description
 * @Author
 * @Date2019-09-27 15:56
 * @Version V1.0
 **/
public final class HttpResponse {

    /**--------------------------Result-------------------------------------------------*/
    public  static Result baseResponse(int status) {
        return baseResponse(status, null);
    }

    public  static Result baseResponse(int status, String msg) {

        if (msg != null) {
            return new Result(status, msg);
        } else {
            return new Result(status, exs.get(status));
        }
    }

    public static Result successResponse(){
        return baseResponse(200);
    }

    public static Result successResponse(String msg){
        return baseResponse(200,msg);
    }

    /**--------------------------Result-------------------------------------------------*/
    public  static Result simpleResponse(int status) {
        return simpleResponse(status, null, null);
    }

    public  static Result simpleResponse(int status, String msg) {
        return simpleResponse(status, msg, null);
    }

    public  static Result simpleResponse(int status, Object data) {
        return simpleResponse(status, null, data);
    }

    public  static Result simpleResponse(int status, String msg, Object data) {

        Result response = new Result();
        response.setCode(status);
        if (msg != null) {
            response.setMessage(msg);
        } else {
            response.setMessage(exs.get(status));
        }
        response.setData(data);
        return response;
    }

    public static Result successResult(Object data){
        return simpleResponse(200,data);
    }

    public static Result successResult(String msg,Object data){
        return simpleResponse(200,msg,data);
    }

    /**--------------------------PageAndSortResponse-------------------------------------------------*/
    /*
    public  static PageAndSortResponse pageAndSortResponse(int status, PageQueryItems query) {
        return pageAndSortResponse(status, null, query);
    }

    public  static PageAndSortResponse pageAndSortResponse(int status, String msg, PageQueryItems query) {
        PageAndSortResponse response = new PageAndSortResponse();
        response.setCurrentPage(query.getCurrentPage());
        response.setPageSize(query.getPageSize());
        response.setCount(query.getCount());
        response.setCode(status);
        response.setDatas(query.getItems());
        if (msg != null) {
            response.setMessage(msg);
        } else {
            response.setMessage(exs.get(status));
        }

        return response;
    }

    public static PageAndSortResponse successPageResponse(PageQueryItems items){
        return pageAndSortResponse(200,items);
    }
    public static PageAndSortResponse successPageResponse(String msg,PageQueryItems items){
        return pageAndSortResponse(200,msg,items);
    }
*/
    /**--------------------------ListResponse-------------------------------------------------*/

    /*
    public  static ListResponse listResponse(int status, Items items) {
        return listResponse(status, null, items);
    }

    public  static ListResponse listResponse(int status, String msg, Items items) {
        ListResponse response = new ListResponse();
        response.setCount(items.getCount());
        response.setDatas(items.getItems());

        if (msg != null) {
            response.setMessage(msg);
        } else {
            response.setMessage(exs.get(status));
        }

        return response;

    }

    public static ListResponse successListResponse(Items items){
        return listResponse(200,items);
    }

    public static ListResponse successListResponse(String msg,Items items){
        return listResponse(200,msg,items);
    }
*/
}
