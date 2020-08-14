package com.ce.nw.dev.action;

import com.ce.nw.common.model.RbacUserRole;
import com.ce.nw.common.service.IRbacUserRoleService;
import com.ce.nw.common.util.StringUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by ccxxu on 2019-08-13.
 */
@Controller
public class RbacUserRoleController {

    @Autowired
    private IRbacUserRoleService rbacUserRoleService;

    @ResponseBody
    @RequestMapping(value="/user/role/save", method = RequestMethod.POST)
    public String save(HttpServletRequest request) {
        Map<String, String> resultMap = new HashMap<>();
        String userId = request.getParameter("userId");
        String roles = request.getParameter("roles");
        ObjectMapper mapper = new ObjectMapper();
        try {
            List<RbacUserRole> list = new ArrayList<>();
            if (StringUtil.isNotNullOrEmpty(userId) && StringUtil.isNotNullOrEmpty(roles)) {
                this.rbacUserRoleService.deleteByUserId(userId);
                String[] roleArr = roles.split(",");
                for (int i=0; i<roleArr.length; i++) {
                    RbacUserRole ur = new RbacUserRole();
                    ur.setRoleId(roleArr[i]);
                    ur.setUserId(userId);
                    list.add(ur);
                }
            }
            this.rbacUserRoleService.saveorupdate(list);
            resultMap.put("status", "true");
            resultMap.put("msg", "SUCCESS!");
            return mapper.writeValueAsString(resultMap);
        } catch (Exception e) {
            e.printStackTrace();
            resultMap.put("status", "false");
            resultMap.put("msg", "系统错误!");
            try {
                return mapper.writeValueAsString(resultMap);
            } catch (JsonProcessingException e1) {
                e1.printStackTrace();
                return "";
            }
        }
    }

}
