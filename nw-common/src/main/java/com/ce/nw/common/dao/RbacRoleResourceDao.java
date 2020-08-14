package com.ce.nw.common.dao;

import com.ce.nw.common.model.RbacRoleResource;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by ccxxu on 2019-08-02.
 */
public interface RbacRoleResourceDao extends JpaRepository<RbacRoleResource, String> {

    @Query(value="select count(*) from RbacRoleResource where resourceId = ?1")
    Integer getCountByResourceId(String resourceId);

    @Query(value="select count(*) from RbacRoleResource where roleId = ?1")
    Integer getCountByRoleId(String roleId);

    @Query(value = "from RbacRoleResource where roleId = ?1")
    List<RbacRoleResource> findListByRoleId(String roleId);

    @Modifying
    @Query(value = "delete from rbac_role_resource where role_id = ?1", nativeQuery = true)
    void deleteByRoleId(String roleId);

}
