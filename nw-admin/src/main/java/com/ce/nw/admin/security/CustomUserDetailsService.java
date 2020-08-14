package com.ce.nw.admin.security;

import com.ce.nw.common.model.RbacRole;
import com.ce.nw.common.model.RbacUser;
import com.ce.nw.common.model.RbacUserRole;
import com.ce.nw.common.model.UserDetailModel;
import com.ce.nw.common.service.IRbacRoleService;
import com.ce.nw.common.service.IRbacUserRoleService;
import com.ce.nw.common.service.IRbacUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by ccxxu on 2019-07-31.
 */
@Service("userDetailsService")
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private IRbacUserService rbacUserService;

    @Autowired
    private IRbacRoleService rbacRoleService;

    @Autowired
    private IRbacUserRoleService rbacUserRoleService;

    @Override
    public UserDetailModel loadUserByUsername(String username) throws UsernameNotFoundException {
        Collection<GrantedAuthority> authorities = new ArrayList<>();
        // 从数据库中取出用户信息
        RbacUser user = this.rbacUserService.getUserByUsername(username);
        System.out.println("Call Security...");
        // 判断用户是否存在
        if(user == null) {
            throw new UsernameNotFoundException("用户名不存在");
        }

        // 添加权限
        List<RbacUserRole> userRoles = this.rbacUserRoleService.getList(user.getUserId()+"");
        for (RbacUserRole userRole : userRoles) {
            RbacRole role = this.rbacRoleService.get(userRole.getRoleId());
            System.out.println("=="+role.getName());
            authorities.add(new SimpleGrantedAuthority(role.getName()));
        }

        UserDetailModel udm = new UserDetailModel(username, user.getPasswordmd(), authorities);
        udm.setId(user.getUserId()+"");
        udm.setRealName(user.getAbbrName());

        // 返回UserDetails实现类
        return udm;

    }
}
