package com.ce.nw.common.service;

import com.ce.nw.common.model.RbacRoleResource;

import java.util.List;

/**
 * Created by ccxxu on 2019-08-02.
 */
public interface IRbacRoleResourceService {

    Integer getCountByResourceId(String resourceId);

    Integer getCountByRoleId(String roleId);

    List<RbacRoleResource> findListByRoleId(String roleId);

    void deleteByRoleId(String roleId);

    void saveorupdate(List<RbacRoleResource> list);

}
