package com.ce.nw.log.service;

import com.ce.nw.log.domain.RbacLog;
import org.aspectj.lang.ProceedingJoinPoint;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.scheduling.annotation.Async;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Created by ccxxu on 2019-08-02.
 */
public interface IRbacLogService {

    void saveorupdate(RbacLog log);

    List<RbacLog> listAll();

    Page<RbacLog> getPageList(Pageable page, Specification<RbacLog> specification);

    List<RbacLog> getPageList(Specification<RbacLog> specification, Sort sort);

    /**
     * 导出日志
     * @param logs 待导出的数据
     * @param response /
     * @throws IOException /
     */
    void download(List<RbacLog> logs, HttpServletResponse response) throws IOException;

    /**
     * 保存日志数据
     * @param username 用户
     * @param browser 浏览器
     * @param ip 请求IP
     * @param joinPoint /
     * @param log 日志实体
     */
    @Async
    void save(String username, String browser, String ip, ProceedingJoinPoint joinPoint, RbacLog log);

    /**
     * 删除所有错误日志
     */
    void delAllByError();

    /**
     * 删除所有INFO日志
     */
    void delAllByInfo();

}
