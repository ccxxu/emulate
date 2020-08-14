package com.ce.nw.common.service.impl;

import com.ce.nw.common.dao.RbacDictDao;
import com.ce.nw.common.model.RbacDict;
import com.ce.nw.common.service.IRbacDictService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by ccxxu on 2019-08-02.
 */
@Service
public class RbacDictServiceImpl implements IRbacDictService {

    @Autowired
    private RbacDictDao rbacDictDao;

    public RbacDict getById(String id) {
        List<RbacDict> list = this.rbacDictDao.selectById(id);
        if (list != null && list.size()>0) {
            return list.get(0);
        }
        return null;
    }

    public RbacDict get(String fdid) {
        return this.rbacDictDao.get(fdid);
    }

    public void delete(String id) {
        this.rbacDictDao.deleteById(id);
    }

    public void saveorupdate(RbacDict dict) {
        this.rbacDictDao.save(dict);
    }

    @Transactional
    public void saveorupdate(RbacDict dict, String oldpid) {
        this.rbacDictDao.update(dict.getId(), oldpid);
        this.rbacDictDao.save(dict);
    }

    public void update(String newpid, String oldpid) {
        this.rbacDictDao.update(newpid, oldpid);
    }

    public List<RbacDict> selectByPid(String pid) {
        return this.rbacDictDao.selectByPid(pid);
    }

    public List<RbacDict> listAll() {
        return this.rbacDictDao.findAll();
    }

    public Page<RbacDict> getPageList(Pageable page, Specification<RbacDict> specification) {
        return this.rbacDictDao.findAll(specification, page);
    }

    public List<RbacDict> getPageList(Specification<RbacDict> specification, Sort sort) {
        return this.rbacDictDao.findAll(specification, sort);
    }

    public List<RbacDict> listTree(List<String> ids) {
        return this.rbacDictDao.listTree(ids);
    }

}
