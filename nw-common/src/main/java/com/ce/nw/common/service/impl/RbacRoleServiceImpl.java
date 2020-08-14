package com.ce.nw.common.service.impl;

import com.ce.nw.common.dao.RbacRoleDao;
import com.ce.nw.common.model.RbacRole;
import com.ce.nw.common.service.IRbacRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by ccxxu on 2019-07-31.
 */
@Service
public class RbacRoleServiceImpl implements IRbacRoleService {

    @Autowired
    private RbacRoleDao rbacRoleDao;

    @Override
    public RbacRole get(String id) {
        return this.rbacRoleDao.get(id);
    }

    @Override
    public RbacRole selectByName(String roleName) {
        List<RbacRole> list = this.rbacRoleDao.selectByName(roleName);
        if (list != null && list.size()>0) {
            return list.get(0);
        }
        return null;
    }

    @Override
    public void saveorupdate(RbacRole role) {
        this.rbacRoleDao.save(role);
    }

    public Page<RbacRole> getPageList(Pageable page, Specification<RbacRole> specification) {
        return this.rbacRoleDao.findAll(specification, page);
    }

    public List<RbacRole> getList(Specification<RbacRole> specification) {
        return this.rbacRoleDao.findAll(specification);
    }

    @Override
    public void delete(String id) {
        this.rbacRoleDao.deleteById(id);
    }

}
