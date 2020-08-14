package com.ce.nw.common.dao;

import com.ce.nw.common.model.RbacClient;
import com.ce.nw.common.model.RbacUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Created by ccxxu on 2019-07-31.
 */
public interface RbacUserDao extends JpaRepository<RbacUser, Long>, JpaSpecificationExecutor<RbacUser> {

    @Query(value=" from RbacUser where useremail = concat(:username,'@mofcom')")
    List<RbacUser> findByUsername(@Param("username") String username);

    @Query(value=" from RbacUser where userId = ?1")
    RbacUser get(Long userId);

    @Query(value=" from RbacUser where objType<=1 and isValid=1 and state=1 and superId=?1 order by displayOrder")
    List<RbacUser> fingBySuperId(Long superId);

    @Query(value=" from RbacUser where objType<=1 and isValid=1 and state=1 and superId=?1 order by displayOrder")
    List<RbacUser> fingBySuperId2(Long superId);

    @Query(value="select * from rbac_user where obj_type<=1 and is_valid=1 and state=1 and USER_ID>1 " +
            "start with USER_ID = ?1 connect by PRIOR SUPER_ID=USER_ID", nativeQuery = true)
    List<RbacUser> findUpBySuperId(Long superId);

    @Query(value="select * from (select ru.*, row_number() over(partition by USER_ID order by DISPLAY_ORDER) rn " +
            "from rbac_user ru where USER_ID>1 and obj_type<=1 and is_valid=1 and state=1 " +
            "start with user_id in (:ids) connect by prior super_id=user_id ) xx where XX.rn=1 order by xx.display_order", nativeQuery = true)
    List<RbacUser> listTree(@Param(value = "ids") List<Long> ids);

    @Query(value="select * from rbac_user where obj_type=0 start with USER_ID = 1 " +
            "connect by PRIOR USER_ID=SUPER_ID order by display_order", nativeQuery = true)
    List<RbacUser> findAllDept();

}
