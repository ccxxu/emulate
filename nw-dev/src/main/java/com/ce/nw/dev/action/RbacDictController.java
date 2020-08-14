package com.ce.nw.dev.action;

import com.ce.nw.common.model.RbacDict;
import com.ce.nw.common.model.RbacResource;
import com.ce.nw.common.service.IRbacDictService;
import com.ce.nw.common.util.StringUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
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
public class RbacDictController {

    @Autowired
    private IRbacDictService rbacDictService;

    @RequestMapping("/admin/dict/list")
    public String list() {
        return "/dict/list";
    }

    @ApiOperation(value = "获取字典列表", notes = "获取字典列表")
    @ResponseBody
    @RequestMapping(value="/dict/tree", method = RequestMethod.POST)
    public String tree(RbacDict dict, HttpServletRequest request) {
        String pid = request.getParameter("id");
        String search = request.getParameter("search");
        try {
            List<RbacDict> list = null;
            if (StringUtil.isNotNullOrEmpty(search)) {
                Specification<RbacDict> specification = new Specification<RbacDict>() {
                    @Override
                    public Predicate toPredicate(Root<RbacDict> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                        List<Predicate> slist = new ArrayList<Predicate>();
//                        CONNECT BY PRIOR
                        if (StringUtil.isNotNullOrEmpty(dict.getName())) {
                            Predicate p1 = criteriaBuilder.like(root.get("name").as(String.class), "%"+dict.getName()+"%");
                            slist.add(p1);
                        }

                        return criteriaBuilder.and(slist.toArray(new Predicate[0]));
                    }
                };
                list = this.rbacDictService.getPageList(specification, new Sort(Sort.Direction.ASC, "ordernum"));
                if (list != null && list.size() > 0) {
                    List<String> ids = new ArrayList<>();
                    for (RbacDict rr :list) {
                        ids.add(rr.getId());
                    }
                    list = this.rbacDictService.listTree(ids);
                }
            } else {
                if (StringUtil.isEmpty(pid)) {
                    pid = "root";
                }
                list = this.rbacDictService.selectByPid(pid);
            }
            Map<String, Object> result = new HashMap<>();
            result.put("total", list.size());
            ObjectMapper mapper = new ObjectMapper();
            List<Map<String, Object>> resultlist = new ArrayList<>();
            for (RbacDict d : list) {
                Map<String, Object> map = mapper.convertValue(d, Map.class);
                map.put("id", d.getId()+"");
                map.put("name", d.getName());
                map.put("state", d.getState());
                if (!"root".equals(d.getPid())) {
                    map.put("_parentId", d.getPid());
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

    @RequestMapping(value = "/dict/add")
    public String add(ModelMap m, HttpServletRequest request) {
        String pid = request.getParameter("pid");
        RbacDict dict = new RbacDict();
        dict.setPid(pid);
        m.put("dict", dict);
        return "/dict/add";
    }

    @RequestMapping(value = "/dict/edit")
    public String edit(ModelMap m, HttpServletRequest request) {
        String id = request.getParameter("fdid");
        RbacDict dict = null;
        if (StringUtil.isNotNullOrEmpty(id)) {
            dict = this.rbacDictService.get(id);
        }
        if (dict == null) {
            dict = new RbacDict();
        }
        m.put("dict", dict);
        return "/dict/add";
    }

    @ResponseBody
    @RequestMapping(value = "/dict/save", method = RequestMethod.POST)
    public String save(ModelMap m, HttpServletRequest request, RbacDict dict) {
        Map<String, String> resultMap = new HashMap<>();
        String fdid = request.getParameter("fdid");
        String pid = request.getParameter("pid");
        String displayOrder = request.getParameter("displayOrder");
        ObjectMapper mapper = new ObjectMapper();
        RbacDict d = null;
        String oldpid = "error";
        try {
            if (StringUtil.isNotNullOrEmpty(fdid)) {
                d = this.rbacDictService.get(fdid);
                oldpid = d.getId();
            }
            if (d == null) {
                d = new RbacDict();
                d.setPid(pid);
            }
            if (StringUtil.isNotNullOrEmpty(dict) && StringUtil.isNotNullOrEmpty(dict.getId())) {
                d.setId(dict.getId());
            }
            if (StringUtil.isNotNullOrEmpty(dict) && StringUtil.isNotNullOrEmpty(dict.getCode())) {
                d.setCode(dict.getCode());
            }
            if (StringUtil.isNotNullOrEmpty(dict) && StringUtil.isNotNullOrEmpty(dict.getName())) {
                d.setName(dict.getName());
            }
            if (StringUtil.isNotNullOrEmpty(displayOrder)) {
                d.setDisplayOrder(Integer.valueOf(displayOrder));
            }
            if (StringUtil.isNotNullOrEmpty(dict) && StringUtil.isNotNullOrEmpty(dict.getFullname())) {
                d.setFullname(dict.getFullname());
            }
            if (StringUtil.isNotNullOrEmpty(dict) && StringUtil.isNotNullOrEmpty(dict.getState())) {
                d.setState(dict.getState());
            }
            if (StringUtil.isNotNullOrEmpty(dict) && StringUtil.isNotNullOrEmpty(dict.getEnable())) {
                d.setEnable(dict.getEnable());
            }
            this.rbacDictService.saveorupdate(d, oldpid);
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
    @RequestMapping(value = "/dict/del", method = RequestMethod.POST)
    public String delete(ModelMap m, HttpServletRequest request, RbacResource resource) {
        String id = request.getParameter("fdid");
        Map<String, String> resultMap = new HashMap<>();
        ObjectMapper mapper = new ObjectMapper();
        try {
            this.rbacDictService.delete(id);
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
