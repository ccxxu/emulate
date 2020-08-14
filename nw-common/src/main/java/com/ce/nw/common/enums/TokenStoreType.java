package com.ce.nw.common.enums;

/**
 * @ClassName: TokenStoreType
 * @Description: 授权令牌的存储方式
 * @Author: ccx
 * @Date: 2019-09-28
 */
public enum TokenStoreType {
    /*
        内存
     */
    memory,
    /*
        redis
     */
    redis,
    /*
        json web token
     */
    jwt,
    /*
        数据库
     */
    jdbc
}
