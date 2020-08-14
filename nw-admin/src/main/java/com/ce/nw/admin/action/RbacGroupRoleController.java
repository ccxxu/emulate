package com.ce.nw.admin.action;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ce.nw.common.model.RbacGroupRole;
import com.ce.nw.common.service.IRbacGroupRoleService;
import com.ce.nw.common.util.StringUtil;
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
public class RbacGroupRoleController {

    @Autowired
    private IRbacGroupRoleService rbacGroupRoleService;

    @ResponseBody
    @RequestMapping(value="/group/role/save", method = RequestMethod.POST)
    public String save(HttpServletRequest request) {
        Map<String, String> resultMap = new HashMap<>();
        String groupId = request.getParameter("groupId");
        String roles = request.getParameter("roles");
        ObjectMapper mapper = new ObjectMapper();
        try {
            List<RbacGroupRole> list = new ArrayList<>();
            if (StringUtil.isNotNullOrEmpty(groupId) && StringUtil.isNotNullOrEmpty(roles)) {
                this.rbacGroupRoleService.deleteByGroupId(groupId);
                String[] roleArr = roles.split(",");
                for (int i=0; i<roleArr.length; i++) {
                    RbacGroupRole gr = new RbacGroupRole();
                    gr.setRoleId(roleArr[i]);
                    gr.setGroupId(groupId);
                    list.add(gr);
                }
            }
            this.rbacGroupRoleService.saveorupdate(list);
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
