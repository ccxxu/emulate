package com.ce.nw.admin.action;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.session.SessionInformation;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Created by ccxxu on 2019-07-31.
 */
@Controller
public class LoginController {
    private Logger logger = LoggerFactory.getLogger(LoginController.class);

    @Autowired
    private SessionRegistry sessionRegistry;

    @RequestMapping("/")
    public String showHome() {
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        logger.info("当前登陆用户：" + name);

        return "home";
    }

    @RequestMapping(value="/login", method = RequestMethod.GET)
    public String showLogin() {
        return "login";
    }

    /**
     * 首页
     *
     * @return
     */
    @RequestMapping("/desktop")
    public String desktop() {
        return "desktop";
    }

    @RequestMapping("/admin")
    @ResponseBody
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String printAdmin() {
        System.out.println("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
        return "如果你看见这句话，说明你有ROLE_ADMIN角色";
    }

    @RequestMapping("/admin/article/issue")
    @ResponseBody
    @PreAuthorize("hasPermission('/admin/article/issue', '发布信息')")
    public String printInfo() {
        System.out.println("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
        return "如果你看见这句话，说明你有/admin/article/issue路径，具有发布信息权限";
    }

    @RequestMapping("/user")
    @ResponseBody
    @PreAuthorize("hasRole('ROLE_USER')")
    public String printUser() {
        return "如果你看见这句话，说明你有ROLE_USER角色";
    }

    @RequestMapping("/login/error")
    public void loginError(HttpServletRequest request, HttpServletResponse response) {
        response.setContentType("text/html;charset=utf-8");
        AuthenticationException exception = (AuthenticationException)request.getSession().getAttribute("SPRING_SECURITY_LAST_EXCEPTION");
        try {
            response.getWriter().write(exception.toString());
        }catch (IOException e) {
            e.printStackTrace();
        }
    }

    @RequestMapping("/login/invalid")
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ResponseBody
    public String invalid(){
        return "Session 已过期，请重新登录！";
    }

    @GetMapping("/kick")
    @ResponseBody
    public String removeUserSessionByUsername( @RequestParam String username) {
        int count = 0;

        List<Object> users = this.sessionRegistry.getAllPrincipals();
        for (Object principal : users) {
            if (principal instanceof User) {
                String principalName = ((User) principal).getUsername();
                if (principalName.equals(username)) {
                    List<SessionInformation> sessionsInfo = sessionRegistry.getAllSessions(principal, false);
                    if (null != sessionsInfo && sessionsInfo.size() > 0) {
                        for (SessionInformation sessionInformation : sessionsInfo) {
                            sessionInformation.expireNow();
                            count++;
                        }
                    }
                }
            }
        }

        return "操作成功，清理session共"+count+"个";
    }

    /**
     * 修改用户资料
     */
    @RequestMapping("/main/info")
    public String changeInfo() {
        return "main/info";
    }

    @RequestMapping("/change/password")
    public String changePassword() {
        System.out.println("123456");
        return "main/pwd";
    }

}
