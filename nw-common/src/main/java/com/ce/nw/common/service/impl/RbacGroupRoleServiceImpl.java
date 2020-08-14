package com.ce.nw.common.service.impl;

import com.ce.nw.common.dao.RbacGroupRoleDao;
import com.ce.nw.common.model.RbacGroupRole;
import com.ce.nw.common.service.IRbacGroupRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by ccxxu on 2019-07-31.
 */
@Service
public class RbacGroupRoleServiceImpl implements IRbacGroupRoleService {

    @Autowired
    private RbacGroupRoleDao rbacGroupRoleDao;

    public Integer getCountByGroupId(String groupId) {
        return this.rbacGroupRoleDao.getCountByGroupId(groupId);
    }

    public Integer getCountByRoleId(String roleId) {
        return this.rbacGroupRoleDao.getCountByRoleId(roleId);
    }

    @Override
    @Transactional
    public void deleteByGroupId(String groupId) {
        this.rbacGroupRoleDao.deleteByGroupId(groupId);
    }

    @Override
    public void saveorupdate(List<RbacGroupRole> list) {
        this.rbacGroupRoleDao.saveAll(list);
    }

    @Override
    public List<RbacGroupRole> getList(String groupId) {
        return this.rbacGroupRoleDao.getListByGroupId(groupId);
    }

}
