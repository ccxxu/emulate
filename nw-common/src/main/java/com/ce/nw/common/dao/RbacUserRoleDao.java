package com.ce.nw.common.dao;

import com.ce.nw.common.model.RbacUserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by ccxxu on 2019-07-31.
 */
public interface RbacUserRoleDao extends JpaRepository<RbacUserRole, String> {

    @Query(value=" from RbacUserRole where userId= ?1")
    List<RbacUserRole> getListByUserId(String id);

    @Query(value="select count(*) from RbacUserRole where userId = ?1")
    Integer getCountByUserId(String userId);

    @Query(value="select count(*) from RbacUserRole where roleId = ?1")
    Integer getCountByRoleId(String roleId);

    @Modifying
    @Query(value = "delete from rbac_user_role where user_id = ?1", nativeQuery = true)
    void deleteByUserId(String userId);

}
