package com.ce.nw.common.dao;

import com.ce.nw.common.model.RbacResource;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Created by ccxxu on 2019-08-02.
 */
public interface RbacResourceDao extends JpaRepository<RbacResource, String>, JpaSpecificationExecutor<RbacResource> {

    @Query(value="from RbacResource rr where rr.fdid in (select resourceId from RbacRoleResource rrr where rrr.roleId = ?1)")
    List<RbacResource> selectByRoleId(String roleId);

    @Query(value="from RbacResource rr where rr.pid = ?1 order by rr.ordernum")
    List<RbacResource> selectByPid(String pid);

    @Query(value="from RbacResource rr where rr.fdid = ?1")
    List<RbacResource> selectById(String id);

    @Query(value = "select * from RBAC_RESOURCE rr where rr.fdid in (select DISTINCT(FDID) from RBAC_RESOURCE  start with id in (:ids) connect by prior pid=id ) order by rr.ordernum", nativeQuery = true)
    List<RbacResource> listTree(@Param(value = "ids") List<String> ids);

    @Query(value="select * from RBAC_RESOURCE start with PID = '00' connect by PRIOR ID=PID order by ordernum", nativeQuery = true)
    List<RbacResource> findAllResource();
}
