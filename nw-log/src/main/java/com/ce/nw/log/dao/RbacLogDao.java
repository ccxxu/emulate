package com.ce.nw.log.dao;

import com.ce.nw.common.model.RbacDict;
import com.ce.nw.log.domain.RbacLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Created by ccxxu on 2019-08-02.
 */
public interface RbacLogDao extends JpaRepository<RbacLog, String>, JpaSpecificationExecutor<RbacLog> {

    @Query(value="from RbacLog rd where rd.id = ?1")
    RbacLog get(String fdid);

}
