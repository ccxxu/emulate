package com.ce.nw.common.service;

import com.ce.nw.common.model.RbacGroup;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

/**
 * Created by ccxxu on 2019-07-31.
 */
public interface IRbacGroupService {

    RbacGroup get(String id);

    RbacGroup selectByName(String groupName);

    void saveorupdate(RbacGroup group);

    Page<RbacGroup> getPageList(Pageable page, Specification<RbacGroup> specification);

    List<RbacGroup> getPageList(Specification<RbacGroup> specification);

    void delete(String id);

    void deleteGroupByIds(String ids);

}
