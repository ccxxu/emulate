package com.ce.nw.log.dao;

import com.ce.nw.common.model.RbacDict;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Created by ccxxu on 2019-08-02.
 */
public interface RbacDictDao extends JpaRepository<RbacDict, String>, JpaSpecificationExecutor<RbacDict> {

    @Query(value="from RbacDict rd where rd.pid = ?1 order by rd.displayOrder")
    List<RbacDict> selectByPid(String pid);

    @Query(value="from RbacDict rd where rd.id = ?1")
    List<RbacDict> selectById(String id);

    @Query(value="from RbacDict rd where rd.fdid = ?1")
    RbacDict get(String fdid);

    @Query(value="update rbac_dict set pid=?1 where pid=?2", nativeQuery=true)
    @Modifying
    void update(String newpid, String oldpid);

    @Query(value = "select * from RBAC_DICT rd where rd.id in (select DISTINCT(ID) from RBAC_DICT start with id in (:ids) connect by prior pid=id ) order by rd.displayOrder", nativeQuery = true)
    List<RbacDict> listTree(@Param(value = "ids") List<String> ids);

}
