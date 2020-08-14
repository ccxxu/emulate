package com.ce.nw.dev.action;

import com.ce.nw.common.model.RbacResource;
import com.ce.nw.common.model.RbacRoleResource;
import com.ce.nw.common.service.IRbacResourceService;
import com.ce.nw.common.service.IRbacRoleResourceService;
import com.ce.nw.common.util.StringUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
 * Created by ccxxu on 2019-08-08.
 */
@Controller
public class RbacResourceController {

    @Autowired
    private IRbacResourceService rbacResourceService;

    @Autowired
    private IRbacRoleResourceService rbacRoleResourceService;

    @RequestMapping("/admin/resource/list")
    public String list() {
        return "/resource/list";
    }

    @ResponseBody
    @RequestMapping(value="/resource/list", method = RequestMethod.POST)
    public String list(String page, String rows, RbacResource resource) {
        try {
            Pageable page1 = PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(rows));
            Specification<RbacResource> specification = new Specification<RbacResource>() {
                @Override
                public Predicate toPredicate(Root<RbacResource> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {

                    List<Predicate> list = new ArrayList<Predicate>();
                    if (StringUtil.isNotNullOrEmpty(resource.getName())) {
                        Predicate p1 = criteriaBuilder.equal(root.get("name").as(String.class), resource.getName());
                        list.add(p1);
                    }

                    return criteriaBuilder.and(list.toArray(new Predicate[0]));
                }
            };
            Page<RbacResource> pageInfo = this.rbacResourceService.getPageList(page1, specification);
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
    @RequestMapping(value="/resource/tree", method = RequestMethod.POST)
    public String tree(String page, String rows, RbacResource resource, HttpServletRequest request) {
        String pid = request.getParameter("id");
        String search = request.getParameter("search");
        try {
            List<RbacResource> list = null;
            if (StringUtil.isNotNullOrEmpty(search)) {
                Specification<RbacResource> specification = new Specification<RbacResource>() {
                    @Override
                    public Predicate toPredicate(Root<RbacResource> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                        List<Predicate> slist = new ArrayList<Predicate>();
                        if (StringUtil.isNotNullOrEmpty(resource.getId())) {
                            Predicate p1 = criteriaBuilder.like(root.get("id").as(String.class), "%"+resource.getId()+"%");
                            slist.add(p1);
                        }

//                        CONNECT BY PRIOR
                        if (StringUtil.isNotNullOrEmpty(resource.getName())) {
                            Predicate p1 = criteriaBuilder.like(root.get("name").as(String.class), "%"+resource.getName()+"%");
                            slist.add(p1);
                        }

                        return criteriaBuilder.and(slist.toArray(new Predicate[0]));
                    }
                };
                list = this.rbacResourceService.getPageList(specification, new Sort(Sort.Direction.ASC, "ordernum"));
                if (list != null && list.size() > 0) {
                    List<String> ids = new ArrayList<>();
                    for (RbacResource rr :list) {
                        ids.add(rr.getId());
                    }
                    list = this.rbacResourceService.listTree(ids);
                }
            } else {
                if (StringUtil.isEmpty(pid)) {
                    pid = "00";
                }
                list = this.rbacResourceService.selectByPid(pid);
            }
            Map<String, Object> result = new HashMap<>();
            result.put("total", list.size());
            ObjectMapper mapper = new ObjectMapper();
            List<Map<String, Object>> resultlist = new ArrayList<>();
            for (RbacResource u : list) {
                Map<String, Object> map = mapper.convertValue(u, Map.class);
                map.put("id", u.getId()+"");
                map.put("name", u.getName());
                map.put("state", u.getState());
                if (!"00".equals(u.getPid())) {
                    map.put("_parentId", u.getPid());
                }
                resultlist.add(map);
            }
            result.put("rows", resultlist);
            String json = mapper.writeValueAsString(result);
            System.out.println("json=="+json);
            return json;
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    @RequestMapping(value = "/resource/add")
    public String add(ModelMap m, HttpServletRequest request) {
        String pid = request.getParameter("pid");
        RbacResource prr = this.rbacResourceService.get(pid);
        RbacResource resource = new RbacResource();
        if (prr == null) {
            resource.setPid("00");
        } else {
            resource.setPid(prr.getId());
        }
        m.put("resource", resource);
        return "/resource/add";
    }

    @RequestMapping(value = "/resource/edit")
    public String edit(ModelMap m, HttpServletRequest request) {
        String id = request.getParameter("id");
        RbacResource resource = null;
        if (StringUtil.isNotNullOrEmpty(id)) {
            resource = this.rbacResourceService.get(id);
        }
        if (resource == null) {
            resource = new RbacResource();
        }
        m.put("resource", resource);
        return "/resource/add";
    }

    @ResponseBody
    @RequestMapping(value = "/resource/save", method = RequestMethod.POST)
    public String save(ModelMap m, HttpServletRequest request, RbacResource resource) {
        Map<String, String> resultMap = new HashMap<>();
        ObjectMapper mapper = new ObjectMapper();
        try {
            this.rbacResourceService.saveorupdate(resource);
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
    @RequestMapping(value = "/resource/del", method = RequestMethod.POST)
    public String delete(ModelMap m, HttpServletRequest request, RbacResource resource) {
        String fdid = request.getParameter("fdid");
        Map<String, String> resultMap = new HashMap<>();
        ObjectMapper mapper = new ObjectMapper();
        try {
            this.rbacResourceService.delete(fdid);
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
    @RequestMapping(value="/resource/treeT", method = RequestMethod.POST)
    public String treeT(HttpServletRequest request) {
        String pid = request.getParameter("id");
        String roleId = request.getParameter("roleId");
        List<RbacResource> list = null;
        try {
            if (StringUtil.isEmpty(pid)) {
                pid = "00";
            }
            list = this.rbacResourceService.selectByPid(pid);
            ObjectMapper mapper = new ObjectMapper();

            List<RbacRoleResource> rrlist = this.rbacRoleResourceService.findListByRoleId(roleId);
            List<Map<String, Object>> resultlist = new ArrayList<>();
            for (RbacResource u : list) {
                Map<String, Object> map = new HashMap<>();
                map.put("id", u.getId()+"");
                map.put("text", u.getName());
                map.put("state", u.getState());
                if (!"00".equals(u.getPid())) {
                    map.put("_parentId", u.getPid());
                }
                map.put("checked", false);
                for (int kk=0; rrlist!=null&&rrlist.size()>0&&kk<rrlist.size();kk++) {
                    RbacRoleResource rrr = rrlist.get(kk);
                    if (rrr.getResourceId().equals(u.getId())) {
                        map.put("checked", true);
                        break;
                    }
                }
                resultlist.add(map);
            }
            String json = mapper.writeValueAsString(resultlist);
            System.out.println("json=="+json);
            return json;
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

}
