/*
 *  Copyright 2020 NW-MASTER
  **  class interface  @interface
 */
package com.ce.nw.log.service.dto;

import lombok.Data;
import com.ce.nw.common.annotation.Query;
import java.sql.Timestamp;
import java.util.List;

/**
 * 日志查询类
 * @author 燕园夜雨
 * @date 2019-6-4 09:23:07
 */
@Data
public class LogQueryCriteria {

    @Query(blurry = "username,description,address,requestIp,method,params")
    private String blurry;

    @Query
    private String logType;

    @Query(type = Query.Type.BETWEEN)
    private List<Timestamp> createTime;
}
