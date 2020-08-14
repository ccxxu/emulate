package com.ce.nw.common.dao;

import com.ce.nw.common.model.RbacGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by ccxxu on 2019-07-31.
 */
public interface RbacGroupDao extends JpaRepository<RbacGroup, String>, JpaSpecificationExecutor<RbacGroup> {

    @Query(value="from RbacGroup where fdid = ?1")
    RbacGroup get(String id);

    @Query(value="from RbacGroup where name = ?1")
    List<RbacGroup> selectByName(String roleName);

}
