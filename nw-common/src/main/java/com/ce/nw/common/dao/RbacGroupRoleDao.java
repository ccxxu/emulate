package com.ce.nw.common.dao;

import com.ce.nw.common.model.RbacGroupRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by ccxxu on 2019-07-31.
 */
public interface RbacGroupRoleDao extends JpaRepository<RbacGroupRole, String> {

    @Query(value=" from RbacGroupRole where groupId= ?1")
    List<RbacGroupRole> getListByGroupId(String id);

    @Query(value="select count(*) from RbacGroupRole where groupId = ?1")
    Integer getCountByGroupId(String groupId);

    @Query(value="select count(*) from RbacGroupRole where roleId = ?1")
    Integer getCountByRoleId(String roleId);

    @Modifying
    @Query(value = "delete from rbac_group_role where group_id = ?1", nativeQuery = true)
    void deleteByGroupId(String groupId);

}
