package com.ce.nw.common.service.impl;

import com.ce.nw.common.dao.RbacRoleResourceDao;
import com.ce.nw.common.model.RbacRoleResource;
import com.ce.nw.common.service.IRbacRoleResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by ccxxu on 2019-08-02.
 */
@Service
public class RbacRoleResourceServiceImpl implements IRbacRoleResourceService {

    @Autowired
    private RbacRoleResourceDao rbacRoleResourceDao;

    public Integer getCountByResourceId(String resourceId) {
        return this.rbacRoleResourceDao.getCountByResourceId(resourceId);
    }

    public Integer getCountByRoleId(String roleId) {
        return this.rbacRoleResourceDao.getCountByRoleId(roleId);
    }

    @Override
    public List<RbacRoleResource> findListByRoleId(String roleId) {
        return this.rbacRoleResourceDao.findListByRoleId(roleId);
    }

    @Override
    @Transactional
    public void deleteByRoleId(String roleId) {
        this.rbacRoleResourceDao.deleteByRoleId(roleId);
    }

    @Override
    public void saveorupdate(List<RbacRoleResource> list) {
        this.rbacRoleResourceDao.saveAll(list);
    }

}
