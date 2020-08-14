package com.ce.nw.admin.action;

import com.ce.nw.admin.page.TableDataView;
import com.ce.nw.common.entity.AjaxResult;
import com.ce.nw.common.service.IRbacGroupService;
import com.ce.nw.common.service.IRbacRoleResourceService;
import com.ce.nw.common.service.IRbacUserGroupService;
import com.ce.nw.common.service.IRbacUserRoleService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ce.nw.common.model.RbacGroup;
import com.ce.nw.common.model.RbacRole;
import com.ce.nw.common.model.RbacUserGroup;
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
@RequestMapping("/admin/group")
public class RbacGroupController extends BaseController {

    @Autowired
    private IRbacGroupService rbacGroupService;

    @Autowired
    private IRbacUserRoleService rbacUserRoleService;

    @Autowired
    private IRbacUserGroupService rbacUserGroupService;

    @Autowired
    private IRbacRoleResourceService rbacRoleResourceService;

    @RequestMapping("/list")
    public String list() {
        return "/group/list";
    }

    @ResponseBody
    @RequestMapping(value="/list", method = RequestMethod.POST)
    public TableDataView list(String pageNum, String pageSize, RbacGroup group) {
        try {
            startPage();
            Pageable page1 = PageRequest.of(Integer.parseInt(pageNum) - 1, Integer.parseInt(pageSize));
            Specification<RbacGroup> specification = new Specification<RbacGroup>() {
                @Override
                public Predicate toPredicate(Root<RbacGroup> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {

                    List<Predicate> list = new ArrayList<Predicate>();
                    if (StringUtil.isNotNullOrEmpty(group.getName())) {
                        Predicate p1 = criteriaBuilder.like(root.get("name").as(String.class), "%"+group.getName()+"%");
                        list.add(p1);
                    }
                    if (StringUtil.isNotNullOrEmpty(group.getDescc())) {
                        Predicate p1 = criteriaBuilder.like(root.get("notes").as(String.class), "%"+group.getDescc()+"%");
                        list.add(p1);
                    }

                    return criteriaBuilder.and(list.toArray(new Predicate[0]));
                }
            };
            Page<RbacGroup> pageInfo = this.rbacGroupService.getPageList(page1, specification);
            return getDataTable(pageInfo);
        } catch (Exception e) {
            e.printStackTrace();
            return new TableDataView();
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

    @RequestMapping(value = "/add")
    public String add(ModelMap m, HttpServletRequest request) {
        RbacGroup group = new RbacGroup();
        m.put("group", group);
        return "/group/add";
    }

    @RequestMapping(value = "/group/role")
    public String role(ModelMap m, HttpServletRequest request) {
        return "/group/role";
    }

    @RequestMapping(value = "/edit/{fdid}")
    public String edit(@PathVariable("fdid") String fdid, ModelMap m, HttpServletRequest request) {
        RbacGroup group = null;
        if (StringUtil.isNotNullOrEmpty(fdid)) {
            group = this.rbacGroupService.get(fdid);
        }
        if (group == null) {
            group = new RbacGroup();
        }
        m.put("group", group);
        return "/group/edit";
    }

    @ResponseBody
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public AjaxResult save(ModelMap m, HttpServletRequest request, RbacGroup group) {
        try {
            this.rbacGroupService.saveorupdate(group);
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
                String[] groupIds = ids.split(",");
                Integer count1 = 0;
                Integer count2 = 0;
                for (String groupId : groupIds) {
                    count1 += this.rbacUserRoleService.getCountByRoleId(groupId);
                    count2 += this.rbacRoleResourceService.getCountByRoleId(groupId);
                }
                if (count1>0 || count2>0) {
                    return toAjax(0);
                } else {
                    this.rbacGroupService.deleteGroupByIds(ids);
                    return toAjax(1);
                }
            }
            return toAjax(1);
        } catch (Exception e) {
            e.printStackTrace();
            return toAjax(0);
        }
    }

}
