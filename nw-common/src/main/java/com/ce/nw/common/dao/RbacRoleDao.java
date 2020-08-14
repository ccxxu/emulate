package com.ce.nw.common.dao;

import com.ce.nw.common.model.RbacRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by ccxxu on 2019-07-31.
 */
public interface RbacRoleDao extends JpaRepository<RbacRole, String>, JpaSpecificationExecutor<RbacRole> {

    @Query(value="from RbacRole where id = ?1")
    RbacRole get(String id);

    @Query(value="from RbacRole where name = ?1")
    List<RbacRole> selectByName(String roleName);
}
