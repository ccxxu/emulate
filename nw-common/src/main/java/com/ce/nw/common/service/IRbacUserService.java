package com.ce.nw.common.service;

import com.ce.nw.common.entity.Ztree;
import com.ce.nw.common.model.RbacUser;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;
import java.util.Map;

/**
 * Created by ccxxu on 2019-07-31.
 */
public interface IRbacUserService {

    RbacUser getUser();

    void save(RbacUser user);

    RbacUser get(String userId);

    void delete(Long userId);

    RbacUser getUserByUsername(String username);

    List<RbacUser> fingBySuperId(Long superId);

    List<RbacUser> fingBySuperId2(Long superId);

    List<RbacUser> findUpBySuperId(Long superId);

    List<RbacUser> listTree(List<Long> ids);

    List<RbacUser> getPageList(Specification<RbacUser> specification, Sort sort);

    Page<RbacUser> getPageList(Pageable page, Specification<RbacUser> specification);

    List<RbacUser> findAllDept();

    List<Ztree> findDeptTree();

    List<Map<String, String>> findDeptBySuperId(Map<String, String> searchMap);

    List<Map<String, String>> findUserBySuperId(Map<String, String> searchMap);

    Integer findUserCountBySuperId(Map<String, String> searchMap);

}
