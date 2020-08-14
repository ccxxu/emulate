package com.ce.nw.dev.action;

import com.ce.nw.common.model.RbacRoleResource;
import com.ce.nw.common.service.IRbacRoleResourceService;
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
public class RbacRoleResourceController {

    @Autowired
    private IRbacRoleResourceService rbacRoleResourceService;

    @ResponseBody
    @RequestMapping(value="/role/resource/save", method = RequestMethod.POST)
    public String save(HttpServletRequest request) {
        Map<String, String> resultMap = new HashMap<>();
        String roleId = request.getParameter("roleId");
        String reIds = request.getParameter("reIds");
        ObjectMapper mapper = new ObjectMapper();
        try {
            List<RbacRoleResource> list = new ArrayList<>();
            if (StringUtil.isNotNullOrEmpty(roleId) && StringUtil.isNotNullOrEmpty(reIds)) {
                this.rbacRoleResourceService.deleteByRoleId(roleId);
                String[] reIdArr = reIds.split(",");
                for (int i=0; i<reIdArr.length; i++) {
                    RbacRoleResource rr = new RbacRoleResource();
                    rr.setRoleId(roleId);
                    rr.setResourceId(reIdArr[i]);
                    list.add(rr);
                }
            }
            this.rbacRoleResourceService.saveorupdate(list);
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
