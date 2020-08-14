package com.ce.nw.common.service;

import com.ce.nw.common.model.RbacResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;
import java.util.Map;

/**
 * Created by ccxxu on 2019-08-02.
 */
public interface IRbacResourceService {

    RbacResource get(String id);

    void saveorupdate(RbacResource resource);

    void delete(String id);

    List<RbacResource> selectByRoleId(String roleId);

    List<RbacResource> selectByPid(String pid);

    List<RbacResource> listAll();

    List<RbacResource> listAll(String type);

    Page<RbacResource> getPageList(Pageable page, Specification<RbacResource> specification);

    List<RbacResource> getPageList(Specification<RbacResource> specification, Sort sort);

    List<RbacResource> listTree(List<String> ids);

    List<RbacResource> findAllResource();

    List<Map<String, String>> findAllResource(RbacResource resource);

}
