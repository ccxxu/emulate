package com.ce.nw.log.service.impl;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.json.JSONObject;
import com.ce.nw.common.util.StringUtils;
import com.ce.nw.log.dao.RbacLogDao;
import com.ce.nw.log.domain.RbacLog;
import com.ce.nw.log.service.IRbacLogService;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.*;

/**
 * Created by ccxxu on 2019-08-02.
 */
@Service
public class RbacLogServiceImpl implements IRbacLogService {

    @Autowired
    private RbacLogDao rbacLogDao;

    public RbacLog get(String fdid) {
        return this.rbacLogDao.get(fdid);
    }

    public void delete(String id) {
        this.rbacLogDao.deleteById(id);
    }

    public void saveorupdate(RbacLog log) {
        this.rbacLogDao.save(log);
    }

    public void update(String newpid, String oldpid) {
//        this.rbacLogDao.update(newpid, oldpid);
    }

    public List<RbacLog> selectByPid(String pid) {
//        return this.rbacLogDao.selectByPid(pid);
        return null;
    }

    public List<RbacLog> listAll() {
        return this.rbacLogDao.findAll();
    }

    public Page<RbacLog> getPageList(Pageable page, Specification<RbacLog> specification) {
        return this.rbacLogDao.findAll(specification, page);
    }

    public List<RbacLog> getPageList(Specification<RbacLog> specification, Sort sort) {
        return this.rbacLogDao.findAll(specification, sort);
    }

    @Override
    public void download(List<RbacLog> logs, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> list = new ArrayList<>();
        for (RbacLog log : logs) {
            Map<String,Object> map = new LinkedHashMap<>();
            map.put("用户名", log.getUsername());
            map.put("IP", log.getRequestIp());
            map.put("IP来源", log.getAddress());
            map.put("描述", log.getDescription());
            map.put("浏览器", log.getBrowser());
            map.put("请求耗时/毫秒", log.getTime());
            map.put("异常详情", new String(ObjectUtil.isNotNull(log.getExceptionDetail()) ? log.getExceptionDetail() : "".getBytes()));
            map.put("创建日期", log.getCreateTime());
            list.add(map);
        }
//        FileUtil.downloadExcel(list, response);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void save(String username, String browser, String ip, ProceedingJoinPoint joinPoint, RbacLog log){

        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        com.ce.nw.log.annotation.Log aopLog = method.getAnnotation(com.ce.nw.log.annotation.Log.class);

        // 方法路径
        String methodName = joinPoint.getTarget().getClass().getName()+"."+signature.getName()+"()";

        StringBuilder params = new StringBuilder("{");
        //参数值
        List<Object> argValues = new ArrayList<>(Arrays.asList(joinPoint.getArgs()));
        //参数名称
        for (Object argValue : argValues) {
            params.append(argValue).append(" ");
        }
        // 描述
        if (log != null) {
            log.setDescription(aopLog.value());
        }
        assert log != null;
        log.setRequestIp(ip);

        String loginPath = "login";
        if(loginPath.equals(signature.getName())){
            try {
                username = new JSONObject(argValues.get(0)).get("username").toString();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        log.setAddress(StringUtils.getCityInfo(log.getRequestIp()));
        log.setMethod(methodName);
        log.setUsername(username);
        log.setParams(params.toString() + " }");
        log.setBrowser(browser);
        this.rbacLogDao.save(log);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delAllByError() {
//        logRepository.deleteByLogType("ERROR");
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delAllByInfo() {
//        logRepository.deleteByLogType("INFO");
    }

}
