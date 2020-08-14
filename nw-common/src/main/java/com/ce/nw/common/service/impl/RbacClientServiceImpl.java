package com.ce.nw.common.service.impl;

import com.ce.nw.common.dao.RbacClientDao;
import com.ce.nw.common.model.RbacClient;
import com.ce.nw.common.service.IRbacClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author ccx
 * @description
 * @date
 */
@Service
public class RbacClientServiceImpl implements IRbacClientService {

    @Autowired
    private RbacClientDao rbacClientDao;

    @Override
    public RbacClient findClientByClientId(String clientId) {
        List<RbacClient> list = this.rbacClientDao.findClientByClientId(clientId);
        if (list != null && list.size()>0) {
            return list.get(0);
        }
        return null;
    }

    public RbacClient get(String id) {
        return this.rbacClientDao.get(id);
    }

    public void save(RbacClient client) {
        this.rbacClientDao.save(client);
    }

    public void delete(String id) {
        this.rbacClientDao.deleteById(id);
    }

    public Page<RbacClient> getPageList(Pageable page, Specification<RbacClient> specification) {
        return this.rbacClientDao.findAll(specification, page);
    }

}
