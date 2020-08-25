/*
 *  Copyright 2020 NW-MASTER
  **  class interface  @interface
 */
package com.ce.nw.log.service.mapstruct;

import com.ce.nw.common.base.BaseMapper;
import com.ce.nw.log.domain.Log;
import com.ce.nw.log.service.dto.LogErrorDTO;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

/**
 * @author 燕园夜雨
 * @date 2019-5-22
 */
@Mapper(componentModel = "spring",unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface LogErrorMapper extends BaseMapper<LogErrorDTO, Log> {

}