package com.ce.nw.admin.action;

import com.ce.nw.admin.page.TableDataView;
import com.ce.nw.common.entity.AjaxResult;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ce.nw.common.model.RbacGroupRole;
import com.ce.nw.common.model.RbacRole;
import com.ce.nw.common.model.RbacUserRole;
import com.ce.nw.common.service.IRbacGroupRoleService;
import com.ce.nw.common.service.IRbacRoleResourceService;
import com.ce.nw.common.service.IRbacRoleService;
import com.ce.nw.common.service.IRbacUserRoleService;
import com.ce.nw.common.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
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
@RequestMapping("/admin/role")
public class RbacRoleController extends BaseController{

    @Autowired
    private IRbacRoleService rbacRoleService;

    @Autowired
    private IRbacUserRoleService rbacUserRoleService;

    @Autowired
    private IRbacGroupRoleService rbacGroupRoleService;

    @Autowired
    private IRbacRoleResourceService rbacRoleResourceService;

    @RequestMapping("/list")
    public String list() {
        return "/role/list";
    }

    @ResponseBody
    @RequestMapping(value="/list", method = RequestMethod.POST)
    public TableDataView list(String pageNum, String pageSize, RbacRole role) {
        try {
            startPage();
            Pageable page1 = PageRequest.of(Integer.parseInt(pageNum) - 1, Integer.parseInt(pageSize));
            Specification<RbacRole> specification = new Specification<RbacRole>() {
                @Override
                public Predicate toPredicate(Root<RbacRole> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {

                    List<Predicate> list = new ArrayList<Predicate>();
                    if (StringUtil.isNotNullOrEmpty(role.getName())) {
                        Predicate p1 = criteriaBuilder.like(root.get("name").as(String.class), "%"+role.getName()+"%");
                        list.add(p1);
                    }
                    if (StringUtil.isNotNullOrEmpty(role.getCode())) {
                        Predicate p1 = criteriaBuilder.like(root.get("name").as(String.class), "%"+role.getCode()+"%");
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
            return getDataTable(pageInfo);
        } catch (Exception e) {
            e.printStackTrace();
            return new TableDataView();
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

    @RequestMapping(value = "/add")
    public String add(ModelMap m, HttpServletRequest request) {
        return "/role/add";
    }

    @RequestMapping(value = "/role/resource")
    public String resource(ModelMap m, HttpServletRequest request) {
        return "/role/resource";
    }

    @RequestMapping(value = "/edit/{id}")
    public String edit(@PathVariable("id") String fdid, ModelMap m, HttpServletRequest request) {
        RbacRole role = null;
        if (StringUtil.isNotNullOrEmpty(fdid)) {
            role = this.rbacRoleService.get(fdid);
        }
        if (role == null) {
            role = new RbacRole();
        }
        m.put("role", role);
        return "/role/edit";
    }

    @ResponseBody
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public AjaxResult save(ModelMap m, HttpServletRequest request, RbacRole role) {
        try {
            this.rbacRoleService.saveorupdate(role);
            return toAjax(1);
        } catch (Exception e) {
            e.printStackTrace();
            return toAjax(0);
        }
    }

    @ResponseBody
    @RequestMapping(value = "/remove", method = RequestMethod.POST)
    public AjaxResult del(ModelMap m, HttpServletRequest request, String ids) {
        try {
            if (StringUtil.isNotNullOrEmpty(ids)) {
                String[] idss = ids.split(",");
                for (String id :idss) {
                    this.rbacRoleService.delete(id);
                }
            }
//            if (StringUtil.isNotNullOrEmpty(id)) {
//                Integer count1 = this.rbacUserRoleService.getCountByRoleId(id);
//                Integer count2 = this.rbacRoleResourceService.getCountByRoleId(id);
//                if (count1>0 || count2>0) {
//                    resultMap.put("status", "relation");
//                    resultMap.put("msg", "当前角色信息与其他表有关联关系");
//                } else {
//                    this.rbacRoleService.delete(id);
//                    resultMap.put("status", "true");
//                    resultMap.put("msg", "SUCCESS!");
//                }
//            }
            return toAjax(1);
        } catch (Exception e) {
            e.printStackTrace();
            return toAjax(0);
        }
    }

}
