package com.ce.nw.common.service;

import com.ce.nw.common.model.RbacClient;
import com.ce.nw.common.model.RbacGroup;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

/**
 * @ClassNameIRbacClientService
 * @Description
 * @Author
 * @Date2019-09-28 14:26
 * @Version V1.0
 **/
public interface IRbacClientService {

    RbacClient findClientByClientId(String clientId);

    RbacClient get(String id);

    void save(RbacClient client);

    void delete(String id);

    Page<RbacClient> getPageList(Pageable page, Specification<RbacClient> specification);

}
