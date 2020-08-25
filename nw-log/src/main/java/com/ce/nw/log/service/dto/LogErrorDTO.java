/*
 *  Copyright 2020 NW-MASTER
  **  class interface  @interface
 */
package com.ce.nw.log.service.dto;

import lombok.Data;
import java.io.Serializable;
import java.sql.Timestamp;

/**
* @author 燕园夜雨
* @date 2019-5-22
*/
@Data
public class LogErrorDTO implements Serializable {

    private Long id;

    private String username;

    private String description;

    private String method;

    private String params;

    private String browser;

    private String requestIp;

    private String address;

    private Timestamp createTime;
}