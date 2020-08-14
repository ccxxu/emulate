package com.ce.nw.common.service;

import com.ce.nw.common.model.RbacDict;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

/**
 * Created by ccxxu on 2019-08-02.
 */
public interface IRbacDictService {

    RbacDict getById(String id);

    RbacDict get(String fdid);

    void saveorupdate(RbacDict dict);

    void saveorupdate(RbacDict dict, String oldpid);

    void update(String newpid, String oldpid);

    void delete(String id);

    List<RbacDict> selectByPid(String pid);

    List<RbacDict> listAll();

    Page<RbacDict> getPageList(Pageable page, Specification<RbacDict> specification);

    List<RbacDict> getPageList(Specification<RbacDict> specification, Sort sort);

    List<RbacDict> listTree(List<String> ids);

}
