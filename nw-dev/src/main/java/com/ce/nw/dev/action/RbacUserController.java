package com.ce.nw.dev.action;

import com.ce.nw.common.model.RbacResource;
import com.ce.nw.common.model.RbacUser;
import com.ce.nw.common.model.UserDetailModel;
import com.ce.nw.common.service.IRbacUserService;
import com.ce.nw.common.util.MD5Util;
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
import javax.servlet.http.HttpSession;
import java.util.*;

/**
 * Created by ccxxu on 2019-08-08.
 */
@Controller
public class RbacUserController {

    @Autowired
    private IRbacUserService rbacUserService;

    @RequestMapping("/user/list")
    public String list() {
        return "/user/list";
    }

    @RequestMapping("/user/tree")
    public String tree() {
        return "/user/tree";
    }

    @RequestMapping("/user/group")
    public String group() {
        return "/user/group";
    }

    @RequestMapping("/user/role")
    public String role() {
        return "/user/role";
    }

    @ResponseBody
    @RequestMapping(value="/user/list", method = RequestMethod.POST)
    public String list(String page, String rows, RbacUser user) {
        try {
            Pageable page1 = PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(rows));
            Specification<RbacUser> specification = new Specification<RbacUser>() {
                @Override
                public Predicate toPredicate(Root<RbacUser> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {

                    List<Predicate> list = new ArrayList<Predicate>();
                    if (StringUtil.isNotNullOrEmpty(user.getPhone())) {
                        Predicate p1 = criteriaBuilder.equal(root.get("phone").as(String.class), user.getPhone());
                        list.add(p1);
                    }

                    return criteriaBuilder.and(list.toArray(new Predicate[0]));
                }
            };
            Page<RbacUser> pageInfo = this.rbacUserService.getPageList(page1, specification);
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
    @RequestMapping(value="/user/tree", method = RequestMethod.POST)
    public String tree(String page, String rows, RbacUser user, HttpServletRequest request) {
        String superSid = request.getParameter("id");
        String search = request.getParameter("search");
        try {
            List<RbacUser> list = new ArrayList<RbacUser>();
            if (StringUtil.isNotNullOrEmpty(search)) {
                Specification<RbacUser> specification = new Specification<RbacUser>() {
                    @Override
                    public Predicate toPredicate(Root<RbacUser> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                        List<Predicate> slist = new ArrayList<Predicate>();
                        if (StringUtil.isNotNullOrEmpty(user.getAbbrName())) {
                            Predicate p1 = criteriaBuilder.like(root.get("abbrName").as(String.class), "%"+user.getAbbrName()+"%");
                            slist.add(p1);
                        }
                        if (StringUtil.isNotNullOrEmpty(user.getUseremail())) {
                            Predicate p1 = criteriaBuilder.like(root.get("useremail").as(String.class), "%"+user.getUseremail()+"%");
                            slist.add(p1);
                        }
                        if (StringUtil.isNotNullOrEmpty(user.getLoginName())) {
                            Predicate p1 = criteriaBuilder.like(root.get("loginName").as(String.class), "%"+user.getLoginName()+"%");
                            slist.add(p1);
                        }
                        slist.add(criteriaBuilder.equal(root.get("state").as(Integer.class), 1));
                        slist.add(criteriaBuilder.equal(root.get("isValid").as(Integer.class), 1));
                        slist.add(criteriaBuilder.le(root.get("objType").as(Integer.class), 1));
                        return criteriaBuilder.and(slist.toArray(new Predicate[0]));
                    }
                };
                List<RbacUser> tlist = this.rbacUserService.getPageList(specification, new Sort(Sort.Direction.ASC, "displayOrder"));
                if (tlist != null && tlist.size() > 0) {
                    List<Long> ids = new ArrayList<>();
                    int count = 0;
                    Set<RbacUser> set = new HashSet<RbacUser>();
                    for (RbacUser rr :tlist) {
                        count++;
                        ids.add(rr.getUserId());
                        if (count%998==0) {
                            List<RbacUser> ulist = this.rbacUserService.listTree(ids);
                            set.addAll(ulist);
                            ids.clear();
                        }
                    }
                    List<RbacUser> ulist = this.rbacUserService.listTree(ids);
                    set.addAll(ulist);

                    System.out.println("========="+set.size());
                    Iterator<RbacUser> it = set.iterator();
                    while(it.hasNext()) {
                        RbacUser pre = it.next();
                        System.out.print(pre.getDisplayOrder()+",");
                    }
                    System.out.println();
                    Set<RbacUser> sortSet = new TreeSet<RbacUser>(new Comparator<RbacUser>() {
                        @Override
                        public int compare(RbacUser o1, RbacUser o2) {
                            int a = 0, b = 0;
                            if (StringUtil.isNotNullOrEmpty(o1) && StringUtil.isNotNullOrEmpty(o1.getDisplayOrder())) {
                                a = o1.getDisplayOrder().intValue();
                            } else {
                                return 1;
                            }
                            if (StringUtil.isNotNullOrEmpty(o2) && StringUtil.isNotNullOrEmpty(o2.getDisplayOrder())) {
                                b = o2.getDisplayOrder().intValue();
                            } else {
                                return -1;
                            }
                            return a-b==0?o1.getUserId().intValue()-o2.getUserId().intValue():a-b;
                        }
                    });
                    sortSet.addAll(set);
                    it = sortSet.iterator();
                    while(it.hasNext()) {
                        RbacUser pre = it.next();
                        list.add(pre);
                        System.out.print(pre.getDisplayOrder()+",");
                    }
                    System.out.println();
                }
            } else {
                if (StringUtil.isEmpty(superSid)) {
                    superSid = "1";
                    list = this.rbacUserService.fingBySuperId(Long.valueOf(superSid));
                } else {
                    list = this.rbacUserService.fingBySuperId2(Long.valueOf(superSid));
                }
            }

            Map<String, Object> result = new HashMap<>();
            result.put("total", list.size());
            ObjectMapper mapper = new ObjectMapper();
            List<Map<String, Object>> resultlist = new ArrayList<>();
            for (RbacUser u : list) {
                Map<String, Object> map = mapper.convertValue(u, Map.class);
                map.put("id", u.getUserId()+"");
                map.put("name", u.getAbbrName());
                if (StringUtil.isNotNullOrEmpty(u.getObjType()) && u.getObjType().intValue()==0) {
                    map.put("state", "closed");
                } else {
                    map.put("state", "open");
                }
                if (!"1".equals(u.getSuperId()+"")) {
                    map.put("_parentId", u.getSuperId()+"");
                }
                resultlist.add(map);
            }
            result.put("rows", resultlist);

            String json = mapper.writeValueAsString(result);
            return json;
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    @ResponseBody
    @RequestMapping(value="/user/treeT", method = RequestMethod.POST)
    public String treeNode(HttpServletRequest request) {
        String superSid = request.getParameter("id");
        try {
            List<RbacUser> list = new ArrayList<RbacUser>();
            if (StringUtil.isEmpty(superSid)) {
                superSid = "1";
                list = this.rbacUserService.fingBySuperId(Long.valueOf(superSid));
            } else {
                list = this.rbacUserService.fingBySuperId2(Long.valueOf(superSid));
            }
            ObjectMapper mapper = new ObjectMapper();
            List<Map<String, Object>> resultlist = new ArrayList<>();
            for (RbacUser u : list) {
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("id", u.getUserId()+"");
                map.put("text", u.getAbbrName());
                if (StringUtil.isNotNullOrEmpty(u.getObjType()) && u.getObjType().intValue()==0) {
                    map.put("state", "closed");
                } else {
                    map.put("state", "open");
                }
                resultlist.add(map);
            }

            String json = mapper.writeValueAsString(resultlist);
            return json;
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    @RequestMapping(value = "/user/add")
    public String add(ModelMap m, HttpServletRequest request) {
        String pid = request.getParameter("pid");
        RbacUser ru = this.rbacUserService.get(pid);
        RbacUser user = new RbacUser();
        if (ru == null) {
            user.setSuperId(1L);
        } else {
            user.setSuperId(Long.valueOf(pid));
        }
        m.put("user", user);
        return "/user/add";
    }

    @RequestMapping(value = "/user/add2")
    public String add2(ModelMap m, HttpServletRequest request) {
        String pid = request.getParameter("pid");
        RbacUser ru = this.rbacUserService.get(pid);
        RbacUser user = new RbacUser();
        if (ru == null) {
            user.setSuperId(1L);
        } else {
            user.setSuperId(Long.valueOf(pid));
        }
        List<RbacUser> list = this.rbacUserService.findUpBySuperId(user.getSuperId());
        for (int i=list.size()-1; i>=0; i--) {
            RbacUser u = list.get(i);
            m.put("dept"+(list.size()-1-i), u.getAbbrName());
        }
        m.put("user", user);
        return "/user/add2";
    }

    @RequestMapping(value = "/user/edit")
    public String edit(ModelMap m, HttpServletRequest request) {
        String id = request.getParameter("id");
        RbacUser user = null;
        if (StringUtil.isNotNullOrEmpty(id)) {
            user = this.rbacUserService.get(id);
        }
        if (user == null) {
            user = new RbacUser();
        }
        m.put("user", user);
        return "/user/add";
    }

    @RequestMapping(value = "/user/edit2")
    public String edit2(ModelMap m, HttpServletRequest request) {
        String id = request.getParameter("id");
        RbacUser user = null;
        if (StringUtil.isNotNullOrEmpty(id)) {
            user = this.rbacUserService.get(id);
            List<RbacUser> list = this.rbacUserService.findUpBySuperId(user.getSuperId());
            for (int i=list.size()-1; i>=0; i--) {
                RbacUser u = list.get(i);
                m.put("dept"+(list.size()-1-i), u.getAbbrName());
            }
        }
        if (user == null) {
            user = new RbacUser();
        }
        m.put("user", user);
        return "/user/add2";
    }

    @ResponseBody
    @RequestMapping(value = "/user/save", method = RequestMethod.POST)
    public String save(ModelMap m, HttpServletRequest request, RbacUser user) {
        Map<String, String> resultMap = new HashMap<>();
        ObjectMapper mapper = new ObjectMapper();
        try {
            RbacUser u = null;
            if (StringUtil.isNotNullOrEmpty(user) && StringUtil.isNotNullOrEmpty(user.getUserId())) {
                u = this.rbacUserService.get(user.getUserId()+"");
            }
            if (u == null) {
                u = user;
                u.setState(1);
            }
            u.setAbbrName(user.getAbbrName());
            u.setAliasName(user.getAliasName());
            u.setSex(user.getSex());
            u.setLevelId(user.getLevelId());
            u.setLoginName(user.getLoginName());
            u.setDisplayOrder(user.getDisplayOrder());
            u.setUseremail(user.getUseremail());
            u.setOutemail(user.getOutemail());
            u.setPhone(user.getPhone());
            u.setMobile(user.getMobile());
            u.setIsValid(user.getIsValid());
            this.rbacUserService.save(u);
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
    @RequestMapping(value = "/user/del", method = RequestMethod.POST)
    public String delete(ModelMap m, HttpServletRequest request, RbacResource resource) {
        String userId = request.getParameter("userId");
        Map<String, String> resultMap = new HashMap<>();
        ObjectMapper mapper = new ObjectMapper();
        try {
            if (StringUtil.isNotNullOrEmpty(userId)) {
                this.rbacUserService.delete(Long.valueOf(userId));
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

    @ResponseBody
    @RequestMapping(value = "/user/reset", method = RequestMethod.POST)
    public String resetPwd(ModelMap m, HttpServletRequest request, RbacResource resource) {
        String userId = request.getParameter("userId");
        Map<String, String> resultMap = new HashMap<>();
        ObjectMapper mapper = new ObjectMapper();
        try {
            if (StringUtil.isNotNullOrEmpty(userId)) {
                RbacUser user = this.rbacUserService.get(userId);
                user.setPassword("a00000000000");
                user.setPasswordmd(MD5Util.md5("a00000000000"));
                this.rbacUserService.save(user);
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

    @ResponseBody
    @RequestMapping(value = "/change/password", method = RequestMethod.POST)
    public String resetPwd02(ModelMap m, HttpServletRequest request, RbacResource resource) {
        HttpSession session = request.getSession();
        String opwd = request.getParameter("opwd");
        String npwdo = request.getParameter("npwdo");
        String npwdt = request.getParameter("npwdt");
        UserDetailModel udm = (UserDetailModel) session.getAttribute("s_member");
        Map<String, String> resultMap = new HashMap<>();
        ObjectMapper mapper = new ObjectMapper();
        try {
            if (StringUtil.isNotNullOrEmpty(opwd)) {
                if (StringUtil.isNotNullOrEmpty(udm) && StringUtil.isNotNullOrEmpty(udm.getId())) {
                    RbacUser user = this.rbacUserService.get(udm.getId());
                    if (opwd.equals(user.getPassword()) && npwdo.equals(npwdt)) {
                        user.setPassword(npwdo);
                        user.setPasswordmd(MD5Util.md5(npwdo));
                        this.rbacUserService.save(user);
                    }
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

    @ResponseBody
    @RequestMapping(value = "/user/dept/save", method = RequestMethod.POST)
    public String resetDept(ModelMap m, HttpServletRequest request, RbacResource resource) {
        String userId = request.getParameter("userId");
        String superId = request.getParameter("superId");
        Map<String, String> resultMap = new HashMap<>();
        ObjectMapper mapper = new ObjectMapper();
        try {
            if (StringUtil.isNotNullOrEmpty(userId) && StringUtil.isNotNullOrEmpty(superId)) {
                RbacUser user = this.rbacUserService.get(userId);
                if (user != null) {
                    user.setSuperId(Long.valueOf(superId));
                    this.rbacUserService.save(user);
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
