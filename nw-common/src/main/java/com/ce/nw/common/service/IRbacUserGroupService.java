package com.ce.nw.common.service;

import com.ce.nw.common.model.RbacUserGroup;

import java.util.List;

/**
 * Created by ccxxu on 2019-08-21.
 */
public interface IRbacUserGroupService {

    void deleteByUserId(String userId);

    void saveorupdate(List<RbacUserGroup> list);

    List<RbacUserGroup> findByUserId(String userId);

}
