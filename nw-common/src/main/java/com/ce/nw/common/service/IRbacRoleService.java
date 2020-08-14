package com.ce.nw.common.service;

import com.ce.nw.common.model.RbacRole;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

/**
 * Created by ccxxu on 2019-07-31.
 */
public interface IRbacRoleService {

    RbacRole get(String id);

    RbacRole selectByName(String roleName);

    void saveorupdate(RbacRole role);

    Page<RbacRole> getPageList(Pageable page, Specification<RbacRole> specification);

    List<RbacRole> getList(Specification<RbacRole> specification);

    void delete(String id);

}
