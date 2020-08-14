package com.ce.nw.dev.action;

import com.ce.nw.common.model.RbacGroupRole;
import com.ce.nw.common.model.RbacRole;
import com.ce.nw.common.model.RbacUserRole;
import com.ce.nw.common.service.IRbacGroupRoleService;
import com.ce.nw.common.service.IRbacRoleResourceService;
import com.ce.nw.common.service.IRbacRoleService;
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
public class RbacRoleController {

    @Autowired
    private IRbacRoleService rbacRoleService;

    @Autowired
    private IRbacUserRoleService rbacUserRoleService;

    @Autowired
    private IRbacGroupRoleService rbacGroupRoleService;

    @Autowired
    private IRbacRoleResourceService rbacRoleResourceService;

    @RequestMapping("/admin/role/list")
    public String list() {
        return "/role/list";
    }

    @ResponseBody
    @RequestMapping(value="/role/list", method = RequestMethod.POST)
    public String list(String page, String rows, RbacRole role) {
        try {
            Pageable page1 = PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(rows));
            Specification<RbacRole> specification = new Specification<RbacRole>() {
                @Override
                public Predicate toPredicate(Root<RbacRole> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {

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
            Page<RbacRole> pageInfo = this.rbacRoleService.getPageList(page1, specification);
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
    @RequestMapping(value="/user/role", method = RequestMethod.POST)
    public String rolelist(RbacRole role, String userId) {
        try {
            Specification<RbacRole> specification = getSpecification(role);
            List<RbacRole> rolelist = this.rbacRoleService.getList(specification);

            if (rolelist!=null && rolelist.size()>0) {
                if (StringUtil.isNotNullOrEmpty(userId)) {
                    List<RbacUserRole> urlist = this.rbacUserRoleService.getList(userId);
                    if (StringUtil.isNotNullOrEmpty(urlist)) {
                        for (int i=0; i<rolelist.size();i++) {
                            RbacRole rr = rolelist.get(i);
                            for (int k=0;k<urlist.size();k++) {
                                RbacUserRole ur = urlist.get(k);
                                if (rr.getFdid().equals(ur.getRoleId())) {
                                    rr.setChecked(true);
                                    break;
                                }
                            }
                        }
                    }
                }
            }

            Map<String, Object> result = new HashMap<>();
            result.put("total", rolelist.size());
            result.put("rows", rolelist);
            ObjectMapper mapper = new ObjectMapper();
            String json = mapper.writeValueAsString(result);
            return json;
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    private Specification<RbacRole> getSpecification(RbacRole role) {
        Specification<RbacRole> specification = new Specification<RbacRole>() {
            @Override
            public Predicate toPredicate(Root<RbacRole> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {

                List<Predicate> list = new ArrayList<Predicate>();
                if (StringUtil.isNotNullOrEmpty(role.getNotes())) {
                    Predicate p1 = criteriaBuilder.like(root.get("notes").as(String.class), "%"+role.getNotes()+"%");
                    list.add(p1);
                }

                return criteriaBuilder.and(list.toArray(new Predicate[0]));
            }
        };
        return specification;
    }

    @ResponseBody
    @RequestMapping(value="/group/role", method = RequestMethod.POST)
    public String rolelist02(RbacRole role, String groupId) {
        try {
            Specification<RbacRole> specification = getSpecification(role);
            List<RbacRole> rolelist = this.rbacRoleService.getList(specification);

            if (rolelist!=null && rolelist.size()>0) {
                if (StringUtil.isNotNullOrEmpty(groupId)) {
                    List<RbacGroupRole> urlist = this.rbacGroupRoleService.getList(groupId);
                    if (StringUtil.isNotNullOrEmpty(urlist)) {
                        for (int i=0; i<rolelist.size();i++) {
                            RbacRole rr = rolelist.get(i);
                            for (int k=0;k<urlist.size();k++) {
                                RbacGroupRole ur = urlist.get(k);
                                if (rr.getFdid().equals(ur.getRoleId())) {
                                    rr.setChecked(true);
                                    break;
                                }
                            }
                        }
                    }
                }
            }

            Map<String, Object> result = new HashMap<>();
            result.put("total", rolelist.size());
            result.put("rows", rolelist);
            ObjectMapper mapper = new ObjectMapper();
            String json = mapper.writeValueAsString(result);
            return json;
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    @RequestMapping(value = "/role/add")
    public String add(ModelMap m, HttpServletRequest request) {
        RbacRole role = new RbacRole();
        m.put("role", role);
        return "/role/add";
    }

    @RequestMapping(value = "/role/resource")
    public String resource(ModelMap m, HttpServletRequest request) {
        return "/role/resource";
    }

    @RequestMapping(value = "/role/edit")
    public String edit(ModelMap m, HttpServletRequest request) {
        String id = request.getParameter("fdid");
        RbacRole role = null;
        if (StringUtil.isNotNullOrEmpty(id)) {
            role = this.rbacRoleService.get(id);
        }
        if (role == null) {
            role = new RbacRole();
        }
        m.put("role", role);
        return "/role/add";
    }

    @ResponseBody
    @RequestMapping(value = "/role/save", method = RequestMethod.POST)
    public String save(ModelMap m, HttpServletRequest request, RbacRole role) {
        Map<String, String> resultMap = new HashMap<>();
        ObjectMapper mapper = new ObjectMapper();
        try {
            this.rbacRoleService.saveorupdate(role);
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
    @RequestMapping(value = "/role/del", method = RequestMethod.POST)
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
                    this.rbacRoleService.delete(id);
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
