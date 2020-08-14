package com.ce.nw.dev.action;

import com.ce.nw.common.model.RbacUserGroup;
import com.ce.nw.common.service.IRbacUserGroupService;
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
public class RbacUserGroupController {

    @Autowired
    private IRbacUserGroupService rbacUserGroupService;

    @ResponseBody
    @RequestMapping(value="/user/group/save", method = RequestMethod.POST)
    public String save(HttpServletRequest request) {
        Map<String, String> resultMap = new HashMap<>();
        String userId = request.getParameter("userId");
        String groups = request.getParameter("groups");
        ObjectMapper mapper = new ObjectMapper();
        try {
            List<RbacUserGroup> list = new ArrayList<>();
            if (StringUtil.isNotNullOrEmpty(userId) && StringUtil.isNotNullOrEmpty(groups)) {
                this.rbacUserGroupService.deleteByUserId(userId);
                String[] groupArr = groups.split(",");
                for (int i=0; i<groupArr.length; i++) {
                    RbacUserGroup ug = new RbacUserGroup();
                    ug.setGroupId(groupArr[i]);
                    ug.setUserId(userId);
                    list.add(ug);
                }
            }
            this.rbacUserGroupService.saveorupdate(list);
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
