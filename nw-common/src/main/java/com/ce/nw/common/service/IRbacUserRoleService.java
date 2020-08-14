package com.ce.nw.common.service;

import com.ce.nw.common.model.RbacUserRole;

import java.util.List;

/**
 * Created by ccxxu on 2019-07-31.
 */
public interface IRbacUserRoleService {

    List<RbacUserRole> getList(String userId);

    Integer getCountByUserId(String userId);

    Integer getCountByRoleId(String roleId);

    void deleteByUserId(String userId);

    void saveorupdate(List<RbacUserRole> list);

}
