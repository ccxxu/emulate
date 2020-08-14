package com.ce.nw.common.service;

import com.ce.nw.common.model.RbacGroupRole;

import java.util.List;

/**
 * Created by ccxxu on 2019-07-31.
 */
public interface IRbacGroupRoleService {

    List<RbacGroupRole> getList(String groupId);

    Integer getCountByGroupId(String groupId);

    Integer getCountByRoleId(String roleId);

    void deleteByGroupId(String groupId);

    void saveorupdate(List<RbacGroupRole> list);

}
