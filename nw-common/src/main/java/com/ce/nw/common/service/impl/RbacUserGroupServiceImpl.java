package com.ce.nw.common.service.impl;

import com.ce.nw.common.dao.RbacUserGroupDao;
import com.ce.nw.common.model.RbacUserGroup;
import com.ce.nw.common.service.IRbacUserGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by ccxxu on 2019-08-21.
 */
@Service
public class RbacUserGroupServiceImpl implements IRbacUserGroupService {

    @Autowired
    private RbacUserGroupDao rbacUserGroupDao;

    @Override
    @Transactional
    public void deleteByUserId(String userId) {
        this.rbacUserGroupDao.deleteByUserId(userId);
    }

    @Override
    public void saveorupdate(List<RbacUserGroup> list) {
        this.rbacUserGroupDao.saveAll(list);
    }

    public List<RbacUserGroup> findByUserId(String userId) {
        return this.rbacUserGroupDao.findByUserId(userId);
    }
}
