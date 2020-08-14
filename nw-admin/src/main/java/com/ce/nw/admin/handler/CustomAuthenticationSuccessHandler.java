package com.ce.nw.admin.handler;

import com.ce.nw.common.model.RbacResource;
import com.ce.nw.common.model.UserDetailModel;
import com.ce.nw.common.service.IRbacResourceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

/**
 * Created by ccxxu on 2019-08-05.
 */
@Component
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private IRbacResourceService rbacResourceService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {

        HttpSession session = request.getSession();
        Object principal = authentication.getPrincipal();
        if (principal instanceof UserDetailModel) {
            UserDetailModel user = (UserDetailModel) principal;
            session.setAttribute("s_member", user);
        }

        List<RbacResource> list = this.rbacResourceService.listAll();
        session.setAttribute("menus", list);

        logger.info("登录成功,{}", authentication);
        response.sendRedirect("/index");
    }
}
