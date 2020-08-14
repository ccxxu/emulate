package com.ce.nw.dev.action;

import com.ce.nw.common.model.RbacGroup;
import com.ce.nw.common.model.RbacRole;
import com.ce.nw.common.model.RbacUserGroup;
import com.ce.nw.common.service.IRbacGroupService;
import com.ce.nw.common.service.IRbacRoleResourceService;
import com.ce.nw.common.service.IRbacUserGroupService;
import com.ce.nw.common.service.IRbacUserRoleService;
import com.ce.nw.common.util.StringUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by ccxxu on 2019-08-13.
 */
@Controller
public class RbacGroupController {

    @Autowired
    private IRbacGroupService rbacGroupService;

    @Autowired
    private IRbacUserRoleService rbacUserRoleService;

    @Autowired
    private IRbacUserGroupService rbacUserGroupService;

    @Autowired
    private IRbacRoleResourceService rbacRoleResourceService;

    @RequestMapping("/admin/group/list")
    public String list() {
        return "/group/list";
    }

    @ResponseBody
    @RequestMapping(value="/group/list", method = RequestMethod.POST)
    public String list(String page, String rows, RbacRole role) {
        try {
            Pageable page1 = PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(rows));
            Specification<RbacGroup> specification = new Specification<RbacGroup>() {
                @Override
                public Predicate toPredicate(Root<RbacGroup> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {

                    List<Predicate> list = new ArrayList<Predicate>();
                    if (StringUtil.isNotNullOrEmpty(role.getName())) {
                        Predicate p1 = criteriaBuilder.like(root.get("name").as(String.class), "%"+role.getName()+"%");
                        list.add(p1);
                    }
                    if (StringUtil.isNotNullOrEmpty(role.getNotes())) {
                        Predicate p1 = criteriaBuilder.like(root.get("notes").as(String.class), "%"+role.getNotes()+"%");
                        list.add(p1);
                    }

                    return criteriaBuilder.and(list.toArray(new Predicate[0]));
                }
            };
            Page<RbacGroup> pageInfo = this.rbacGroupService.getPageList(page1, specification);
            Map<String, Object> result = new HashMap<>();
            result.put("total", pageInfo.getTotalElements());
            result.put("rows", pageInfo.getContent());
            ObjectMapper mapper = new ObjectMapper();
            String json = mapper.writeValueAsString(result);
            return json;
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    @ResponseBody
    @RequestMapping(value="/user/group", method = RequestMethod.POST)
    public String usergroup(RbacRole role, String userId) {
        try {
            Specification<RbacGroup> specification = new Specification<RbacGroup>() {
                @Override
                public Predicate toPredicate(Root<RbacGroup> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {

                    List<Predicate> list = new ArrayList<Predicate>();
                    if (StringUtil.isNotNullOrEmpty(role.getName())) {
                        Predicate p1 = criteriaBuilder.like(root.get("name").as(String.class), "%"+role.getName()+"%");
                        list.add(p1);
                    }
                    return criteriaBuilder.and(list.toArray(new Predicate[0]));
                }
            };
            List<RbacGroup> grouplist = this.rbacGroupService.getPageList(specification);

            if (grouplist!=null && grouplist.size()>0) {
                if (StringUtil.isNotNullOrEmpty(userId)) {
                    List<RbacUserGroup> uglist = this.rbacUserGroupService.findByUserId(userId);
                    if (StringUtil.isNotNullOrEmpty(uglist)) {
                        for (int i=0; i<grouplist.size();i++) {
                            RbacGroup rg = grouplist.get(i);
                            for (int k=0;k<uglist.size();k++) {
                                RbacUserGroup ug = uglist.get(k);
                                if (rg.getFdid().equals(ug.getGroupId())) {
                                    rg.setChecked(true);
                                    break;
                                }
                            }
                        }
                    }
                }
            }
            Map<String, Object> result = new HashMap<>();
            result.put("total", grouplist.size());
            result.put("rows", grouplist);
            ObjectMapper mapper = new ObjectMapper();
            String json = mapper.writeValueAsString(result);
            return json;
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    @RequestMapping(value = "/group/add")
    public String add(ModelMap m, HttpServletRequest request) {
        RbacGroup group = new RbacGroup();
        m.put("group", group);
        return "/group/add";
    }

    @RequestMapping(value = "/group/role")
    public String role(ModelMap m, HttpServletRequest request) {
        return "/group/role";
    }

    @RequestMapping(value = "/group/edit")
    public String edit(ModelMap m, HttpServletRequest request) {
        String id = request.getParameter("fdid");
        RbacGroup group = null;
        if (StringUtil.isNotNullOrEmpty(id)) {
            group = this.rbacGroupService.get(id);
        }
        if (group == null) {
            group = new RbacGroup();
        }
        m.put("group", group);
        return "/group/add";
    }

    @ResponseBody
    @RequestMapping(value = "/group/save", method = RequestMethod.POST)
    public String save(ModelMap m, HttpServletRequest request, RbacGroup group) {
        Map<String, String> resultMap = new HashMap<>();
        ObjectMapper mapper = new ObjectMapper();
        try {
            this.rbacGroupService.saveorupdate(group);
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

    @ResponseBody
    @RequestMapping(value = "/group/del", method = RequestMethod.POST)
    public String del(ModelMap m, HttpServletRequest request, String id) {
        Map<String, String> resultMap = new HashMap<>();
        ObjectMapper mapper = new ObjectMapper();
        try {
            if (StringUtil.isNotNullOrEmpty(id)) {
                Integer count1 = this.rbacUserRoleService.getCountByRoleId(id);
                Integer count2 = this.rbacRoleResourceService.getCountByRoleId(id);
                if (count1>0 || count2>0) {
                    resultMap.put("status", "relation");
                    resultMap.put("msg", "当前角色信息与其他表有关联关系");
                } else {
                    this.rbacGroupService.delete(id);
                    resultMap.put("status", "true");
                    resultMap.put("msg", "SUCCESS!");
                }
            }

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
