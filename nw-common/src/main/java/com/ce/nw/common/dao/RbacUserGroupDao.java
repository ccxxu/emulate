package com.ce.nw.common.dao;

import com.ce.nw.common.model.RbacUserGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by ccxxu on 2019-08-21.
 */
public interface RbacUserGroupDao extends JpaRepository<RbacUserGroup, String> {

    @Modifying
    @Query(value = "delete from rbac_user_group where user_id = ?1", nativeQuery = true)
    void deleteByUserId(String userId);

    @Query(value = " from RbacUserGroup where userId = ?1")
    List<RbacUserGroup> findByUserId(String userId);

}
