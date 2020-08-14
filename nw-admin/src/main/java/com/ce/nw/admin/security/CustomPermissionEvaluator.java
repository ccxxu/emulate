package com.ce.nw.admin.security;

import com.ce.nw.common.model.RbacResource;
import com.ce.nw.common.model.RbacRole;
import com.ce.nw.common.model.UserDetailModel;
import com.ce.nw.common.service.IRbacResourceService;
import com.ce.nw.common.service.IRbacRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

/**
 * Created by ccxxu on 2019-08-02.
 */
@Component
public class CustomPermissionEvaluator implements PermissionEvaluator {

    @Autowired
    private IRbacRoleService rbacRoleService;

    @Autowired
    private IRbacResourceService rbacResourceService;

    @Override
    public boolean hasPermission(Authentication authentication, Object targetUrl, Object targetPermission) {
        Object principal = authentication.getPrincipal();
        if (principal instanceof UserDetailModel) {
            UserDetailModel user = (UserDetailModel)authentication.getPrincipal();
            Collection<GrantedAuthority> authorities = user.getAuthorities();
            System.out.println("targetUrl="+targetUrl+",targetPerssion="+targetPermission);
            for (GrantedAuthority authority : authorities) {
                String roleName = authority.getAuthority();
                RbacRole role = this.rbacRoleService.selectByName(roleName);
                List<RbacResource> list = this.rbacResourceService.selectByRoleId(role.getFdid());
                for (RbacResource rr : list) {
                    System.out.println(rr.getUrl()+":"+rr.getName());
                    if(targetUrl.equals(rr.getUrl()) && targetPermission.equals(rr.getName())) {
                        return true;
                    }
                }
            }
        }
        System.out.println("----------------------------------------------");
        return false;
    }

    @Override
    public boolean hasPermission(Authentication authentication, Serializable serializable, String s, Object o) {
        return false;
    }
}
