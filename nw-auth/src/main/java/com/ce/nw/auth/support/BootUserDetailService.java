package com.ce.nw.auth.support;

import com.ce.nw.common.model.RbacRole;
import com.ce.nw.common.model.RbacUser;
import com.ce.nw.common.model.RbacUserRole;
import com.ce.nw.common.service.IRbacRoleService;
import com.ce.nw.common.service.IRbacUserRoleService;
import com.ce.nw.common.service.IRbacUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author yuit
 * @date time 2018/10/11  9:13
 *
 **/
@Component
public final class BootUserDetailService implements UserDetailsService {

    @Autowired
    private IRbacUserService rbacUserService;

    @Autowired
    private IRbacRoleService rbacRoleService;

    @Autowired
    private IRbacUserRoleService rbacUserRoleService;

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
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

        // 可用性 :true:可用 false:不可用
        boolean enabled = true;
        // 过期性 :true:没过期 false:过期
        boolean accountNonExpired = true;
        // 有效性 :true:凭证有效 false:凭证无效
        boolean credentialsNonExpired = true;
        // 锁定性 :true:未锁定 false:已锁定
        boolean accountNonLocked = true;

        User result = new User(username, user.getBcryptPwd(), enabled, accountNonExpired,
                credentialsNonExpired, accountNonLocked, authorities);
//        GrantedAuthority authority = new SimpleGrantedAuthority("ROLE_USER");
        return result;
    }
}
