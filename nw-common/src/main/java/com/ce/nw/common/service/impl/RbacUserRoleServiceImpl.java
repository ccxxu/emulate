package com.ce.nw.common.service.impl;

import com.ce.nw.common.dao.RbacUserRoleDao;
import com.ce.nw.common.model.RbacUserRole;
import com.ce.nw.common.service.IRbacUserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by ccxxu on 2019-07-31.
 */
@Service
public class RbacUserRoleServiceImpl implements IRbacUserRoleService {

    @Autowired
    private RbacUserRoleDao rbacUserRoleDao;

    public Integer getCountByUserId(String userId) {
        return this.rbacUserRoleDao.getCountByUserId(userId);
    }

    public Integer getCountByRoleId(String roleId) {
        return this.rbacUserRoleDao.getCountByRoleId(roleId);
    }

    @Override
    @Transactional
    public void deleteByUserId(String userId) {
        this.rbacUserRoleDao.deleteByUserId(userId);
    }

    @Override
    public void saveorupdate(List<RbacUserRole> list) {
        this.rbacUserRoleDao.saveAll(list);
    }

    @Override
    public List<RbacUserRole> getList(String userId) {
        return this.rbacUserRoleDao.getListByUserId(userId);
    }

}
