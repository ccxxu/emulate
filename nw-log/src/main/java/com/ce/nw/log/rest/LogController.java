/*
 *  Copyright 2020 NW-MASTER
  **  class interface  @interface
 */
package com.ce.nw.log.rest;

import com.ce.nw.common.util.SecurityUtils;
import com.ce.nw.common.util.StringUtil;
import com.ce.nw.log.annotation.Log;
import com.ce.nw.log.domain.RbacLog;
import com.ce.nw.log.service.IRbacLogService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author 燕园夜雨
 * @date 2018-11-24
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/logs")
@Api(tags = "系统：日志管理")
public class LogController {

    @Autowired
    private IRbacLogService rbacLogService;

    @Log("导出数据")
    @ApiOperation("导出数据")
    @GetMapping(value = "/download")
    @PreAuthorize("@el.check()")
    public void download(HttpServletResponse response) throws IOException {
        Specification<RbacLog> specification = new Specification<RbacLog>() {
            @Override
            public Predicate toPredicate(Root<RbacLog> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> list = new ArrayList<Predicate>();
                Predicate p1 = criteriaBuilder.equal(root.get("logType").as(String.class), "INFO");
                list.add(p1);
                return criteriaBuilder.and(list.toArray(new Predicate[0]));
            }
        };
        List<RbacLog> pageInfo = this.rbacLogService.getPageList(specification, null);
        rbacLogService.download(pageInfo, response);
    }

    @Log("导出错误数据")
    @ApiOperation("导出错误数据")
    @GetMapping(value = "/error/download")
    @PreAuthorize("@el.check()")
    public void downloadErrorLog(HttpServletResponse response) throws IOException {
        Specification<RbacLog> specification = new Specification<RbacLog>() {
            @Override
            public Predicate toPredicate(Root<RbacLog> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> list = new ArrayList<Predicate>();
                Predicate p1 = criteriaBuilder.equal(root.get("logType").as(String.class), "ERROR");
                list.add(p1);
                return criteriaBuilder.and(list.toArray(new Predicate[0]));
            }
        };
        List<RbacLog> pageInfo = this.rbacLogService.getPageList(specification, null);
        rbacLogService.download(pageInfo, response);
    }
    @GetMapping
    @ApiOperation("日志查询")
    @PreAuthorize("@el.check()")
    public ResponseEntity<Object> query(String pageNum, String pageSize, RbacLog log,
                                        HttpServletRequest request,
                                        Pageable pageable){
        try {
            pageable = PageRequest.of(Integer.parseInt(pageNum) - 1, Integer.parseInt(pageSize));
            Specification<RbacLog> specification = new Specification<RbacLog>() {
                @Override
                public Predicate toPredicate(Root<RbacLog> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                    List<Predicate> list = new ArrayList<Predicate>();
                    if (StringUtil.isNotNullOrEmpty(log.getUsername())) {
                        Predicate p1 = criteriaBuilder.like(root.get("username").as(String.class), "%"+log.getUsername()+"%");
                        list.add(p1);
                    }
                    Predicate p1 = criteriaBuilder.equal(root.get("logType").as(String.class), "INFO");
                    list.add(p1);
                    return criteriaBuilder.and(list.toArray(new Predicate[0]));
                }
            };
            Page<RbacLog> pageInfo = this.rbacLogService.getPageList(pageable, specification);
            return new ResponseEntity<>(pageInfo, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.OK);
        }
    }

    @GetMapping(value = "/user")
    @ApiOperation("用户日志查询")
    public ResponseEntity<Object> queryUserLog(String pageNum, String pageSize, RbacLog log,
                                               HttpServletRequest request,
                                               Pageable pageable){
        try {
            pageable = PageRequest.of(Integer.parseInt(pageNum) - 1, Integer.parseInt(pageSize));
            Specification<RbacLog> specification = new Specification<RbacLog>() {
                @Override
                public Predicate toPredicate(Root<RbacLog> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                    List<Predicate> list = new ArrayList<Predicate>();
                    Predicate p1 = criteriaBuilder.equal(root.get("blurry").as(String.class), SecurityUtils.getCurrentUsername());
                    list.add(p1);
                    p1 = criteriaBuilder.equal(root.get("logType").as(String.class), "INFO");
                    list.add(p1);
                    return criteriaBuilder.and(list.toArray(new Predicate[0]));
                }
            };
            Page<RbacLog> pageInfo = this.rbacLogService.getPageList(pageable, specification);
            return new ResponseEntity<>(pageInfo, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.OK);
        }
    }

    @GetMapping(value = "/error")
    @ApiOperation("错误日志查询")
    @PreAuthorize("@el.check()")
    public ResponseEntity<Object> queryErrorLog(String pageNum, String pageSize, RbacLog log,
                                                HttpServletRequest request,
                                                Pageable pageable){
        try {
            pageable = PageRequest.of(Integer.parseInt(pageNum) - 1, Integer.parseInt(pageSize));
            Specification<RbacLog> specification = new Specification<RbacLog>() {
                @Override
                public Predicate toPredicate(Root<RbacLog> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                    List<Predicate> list = new ArrayList<Predicate>();
                    Predicate p1 = criteriaBuilder.equal(root.get("logType").as(String.class), "ERROR");
                    list.add(p1);
                    return criteriaBuilder.and(list.toArray(new Predicate[0]));
                }
            };
            Page<RbacLog> pageInfo = this.rbacLogService.getPageList(pageable, specification);
            return new ResponseEntity<>(pageInfo, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.OK);
        }
    }

    @GetMapping(value = "/error/{id}")
    @ApiOperation("日志异常详情查询")
    @PreAuthorize("@el.check()")
    public ResponseEntity<Object> queryErrorLogs(@PathVariable Long id){
//        return new ResponseEntity<>(logService.findByErrDetail(id), HttpStatus.OK);
        return new ResponseEntity<>(null, HttpStatus.OK);
    }

    @DeleteMapping(value = "/del/error")
    @Log("删除所有ERROR日志")
    @ApiOperation("删除所有ERROR日志")
    @PreAuthorize("@el.check()")
    public ResponseEntity<Object> delAllErrorLog(){
        this.rbacLogService.delAllByError();
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping(value = "/del/info")
    @Log("删除所有INFO日志")
    @ApiOperation("删除所有INFO日志")
    @PreAuthorize("@el.check()")
    public ResponseEntity<Object> delAllInfoLog(){
        this.rbacLogService.delAllByInfo();
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
