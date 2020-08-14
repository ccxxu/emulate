package com.ce.nw.common.dao;

import com.ce.nw.common.model.RbacClient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @ClassName RbacClientDao
 * @Description
 * @Author
 * @Date 2019-09-28 14:23
 * @Version V1.0
 **/
public interface RbacClientDao extends JpaRepository<RbacClient, String>, JpaSpecificationExecutor<RbacClient> {

    @Query("from RbacClient c where c.clientId = ?1")
    List<RbacClient> findClientByClientId(String clientId);

    @Query("from RbacClient c where c.id = ?1")
    RbacClient get(String id);

}
