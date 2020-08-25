/**
 *  Copyright 2020 CIECC
 *  定义常用静态常量
 */
package com.ce.nw.common.util;

/**
 * 常用静态常量
 * @author 燕园夜雨
 * @date 2018-12-26
 */
public class ElAdminConstant {

    /**
     * win 系统
     */
    public static final String WIN = "win";

    /**
     * mac 系统
     */
    public static final String MAC = "mac";

    /**
     * 常用接口
     */
    public static class Url{
        // 免费图床
        public static final String SM_MS_URL = "https://sm.ms/api";
        // IP归属地查询
        public static final String IP_URL = "http://whois.pconline.com.cn/ipJson.jsp?ip=%s&json=true";
    }
}
