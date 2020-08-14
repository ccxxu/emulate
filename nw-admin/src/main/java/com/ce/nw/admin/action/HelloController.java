package com.ce.nw.admin.action;

import com.ce.nw.common.model.RbacUser;
import com.ce.nw.common.service.IRbacUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by ccxxu on 2019-07-12.
 */

@RestController
public class HelloController {

    @Autowired
    private IRbacUserService rbacUserService;

    @RequestMapping("/hello")
    public String hello() {
        RbacUser user = this.rbacUserService.getUser();
        return "Hello Spring Boot=";
    }

}
