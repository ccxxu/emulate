package com.ce.nw.common.service.impl;

import com.ce.nw.common.dao.RbacGroupDao;
import com.ce.nw.common.model.RbacGroup;
import com.ce.nw.common.service.IRbacGroupService;
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
public class RbacGroupServiceImpl implements IRbacGroupService {

    @Autowired
    private RbacGroupDao rbacGroupDao;

    @Override
    public RbacGroup get(String id) {
        return this.rbacGroupDao.get(id);
    }

    @Override
    public RbacGroup selectByName(String roleName) {
        List<RbacGroup> list = this.rbacGroupDao.selectByName(roleName);
        if (list != null && list.size()>0) {
            return list.get(0);
        }
        return null;
    }

    @Override
    public void saveorupdate(RbacGroup group) {
        this.rbacGroupDao.save(group);
    }

    public Page<RbacGroup> getPageList(Pageable page, Specification<RbacGroup> specification) {
        return this.rbacGroupDao.findAll(specification, page);
    }

    @Override
    public List<RbacGroup> getPageList(Specification<RbacGroup> specification) {
        return this.rbacGroupDao.findAll(specification);
    }

    @Override
    public void delete(String id) {
        this.rbacGroupDao.deleteById(id);
    }

    public void deleteGroupByIds(String ids) {
        String[] groupIds = ids.split(",");
        for (String groupId : groupIds) {
            delete(groupId);
        }
    }

}
