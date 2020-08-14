package com.ce.nw.admin.action;

import com.ce.nw.common.model.RbacResource;
import com.ce.nw.common.model.UserDetailModel;
import com.ce.nw.common.service.IRbacResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by ccxxu on 2019-07-31.
 */
@Controller
public class IndexController implements ErrorController{

    private static final String PATH = "/error";

    @Autowired
    private IRbacResourceService rbacResourceService;

    @GetMapping("/index")
    public String index(ModelMap mm, Authentication authentication) {
        Object principal = authentication.getPrincipal();
        if (principal instanceof UserDetailModel) {
            UserDetailModel user = (UserDetailModel) principal;
            mm.put("user", user);
        }

        List<RbacResource> list = this.rbacResourceService.listAll("00");
        mm.put("menus", list);
        mm.put("copyrightYear", "2019");
        mm.put("demoEnabled", true);
        return "index";
    }

    @RequestMapping(value=PATH)
    public String error() {
        return "login";
    }

    @Override
    public String getErrorPath() {
        return PATH;
    }

    // 系统介绍
    @GetMapping("/system/main")
    public String main(ModelMap mmap)
    {
        mmap.put("version", "4.1.0");
        return "main";
    }
}
