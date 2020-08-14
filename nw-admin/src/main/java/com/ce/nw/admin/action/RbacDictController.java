package com.ce.nw.admin.action;

import com.ce.nw.admin.page.TableDataView;
import com.ce.nw.common.entity.AjaxResult;
import io.swagger.annotations.ApiOperation;
import com.ce.nw.common.model.RbacDict;
import com.ce.nw.common.service.IRbacDictService;
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
import java.util.List;

/**
 * Created by ccxxu on 2019-08-08.
 */
@Controller
@RequestMapping("/admin/dict")
public class RbacDictController extends BaseController {

    @Autowired
    private IRbacDictService rbacDictService;

    @RequestMapping("/list/{pid}")
    public String list(@PathVariable("pid") String pid, ModelMap m) {
        RbacDict dict = new RbacDict();
        if(StringUtil.isNotNullOrEmpty(pid)) {
            dict.setPid(pid);
        } else {
            dict.setPid("root");
        }
        m.put("dict", dict);
        return "/dict/list";
    }

    @ApiOperation(value = "获取字典列表", notes = "获取字典列表")
    @ResponseBody
    @RequestMapping(value="/list", method = RequestMethod.POST)
    public TableDataView tree(String pageNum, String pageSize, RbacDict dict, HttpServletRequest request) {
        try {
            startPage();
            Pageable page1 = PageRequest.of(Integer.parseInt(pageNum) - 1, Integer.parseInt(pageSize));
            Specification<RbacDict> specification = new Specification<RbacDict>() {
                @Override
                public Predicate toPredicate(Root<RbacDict> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {

                    List<Predicate> list = new ArrayList<Predicate>();
                    if (StringUtil.isNotNullOrEmpty(dict.getName())) {
                        Predicate p1 = criteriaBuilder.like(root.get("name").as(String.class), "%"+dict.getName()+"%");
                        list.add(p1);
                    }
                    if (StringUtil.isNotNullOrEmpty(dict.getCode())) {
                        Predicate p1 = criteriaBuilder.like(root.get("name").as(String.class), "%"+dict.getCode()+"%");
                        list.add(p1);
                    }
                    if (StringUtil.isNotNullOrEmpty(dict.getPid())) {
                        Predicate p1 = criteriaBuilder.equal(root.get("pid").as(String.class), dict.getPid());
                        list.add(p1);
                    }

                    return criteriaBuilder.and(list.toArray(new Predicate[0]));
                }
            };
            Page<RbacDict> pageInfo = this.rbacDictService.getPageList(page1, specification);
            return getDataTable(pageInfo);
        } catch (Exception e) {
            e.printStackTrace();
            return new TableDataView();
        }
    }

    @RequestMapping(value = "/add/{id}")
    public String add(@PathVariable("id") String pid, ModelMap m, HttpServletRequest request) {
        RbacDict dict = new RbacDict();
        if (StringUtil.isNotNullOrEmpty(pid)) {
            dict.setPid(pid);
        } else {
            dict.setPid("root");
        }
        m.put("dict", dict);
        return "/dict/add";
    }

    @RequestMapping(value = "/edit/{id}")
    public String edit(@PathVariable("id") String id, ModelMap m, HttpServletRequest request) {
        RbacDict dict = null;
        if (StringUtil.isNotNullOrEmpty(id)) {
            dict = this.rbacDictService.get(id);
        }
        if (dict == null) {
            dict = new RbacDict();
        }
        m.put("dict", dict);
        return "/dict/edit";
    }

    @ResponseBody
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public AjaxResult save(ModelMap m, HttpServletRequest request, RbacDict dict) {
        RbacDict d = null;
        try {
            if (StringUtil.isNotNullOrEmpty(dict) && StringUtil.isNotNullOrEmpty(dict.getFdid())) {
                d = this.rbacDictService.get(dict.getFdid());
            }
            if (d == null) {
                d = new RbacDict();
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
            if (StringUtil.isNotNullOrEmpty(dict) && StringUtil.isNotNullOrEmpty(dict.getDisplayOrder())) {
                d.setDisplayOrder(dict.getDisplayOrder());
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
            if (StringUtil.isNotNullOrEmpty(dict) && StringUtil.isNotNullOrEmpty(dict.getPid())) {
                d.setPid(dict.getPid());
            }
            this.rbacDictService.saveorupdate(d);
            return toAjax(1);
        } catch (Exception e) {
            e.printStackTrace();
            return toAjax(0);
        }
    }

    @ResponseBody
    @RequestMapping(value = "/remove", method = RequestMethod.POST)
    public AjaxResult delete(ModelMap m, HttpServletRequest request, String ids) {
        try {
            if (StringUtil.isNotNullOrEmpty(ids)) {
                String[] idss = ids.split(",");
                for (String id : idss) {
                    this.rbacDictService.delete(id);
                }
            }
            return toAjax(1);
        } catch (Exception e) {
            e.printStackTrace();
            return toAjax(0);
        }
    }


}
