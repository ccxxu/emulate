package com.ce.nw.admin.action;

import com.ce.nw.common.entity.AjaxResult;
import com.ce.nw.common.model.RbacResource;
import com.ce.nw.common.service.IRbacResourceService;
import com.ce.nw.common.service.IRbacRoleResourceService;
import com.ce.nw.common.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * 资源管理维护控制器
 * Created by ccxxu on 2019-08-08.
 */
@Controller
@RequestMapping("/admin/resource")
public class RbacResourceController extends BaseController {

    @Autowired
    private IRbacResourceService rbacResourceService;

    @Autowired
    private IRbacRoleResourceService rbacRoleResourceService;

    @RequestMapping("/list")
    public String list() {
        return "/resource/list";
    }

    @ResponseBody
    @RequestMapping(value="/list", method = RequestMethod.POST)
    public List<Map<String, String>> list(RbacResource resource) {
        return this.rbacResourceService.findAllResource(resource);
    }

    @RequestMapping(value = "/add/{id}")
    public String add(@PathVariable("id") String pid, ModelMap m, HttpServletRequest request) {
        RbacResource resource = new RbacResource();
        if (StringUtil.isNotNullOrEmpty(pid)) {
            resource.setPid(pid);
        } else {
            resource.setPid("00");
        }
        m.put("resource", resource);
        return "/resource/add";
    }

    @RequestMapping(value = "/edit/{id}")
    public String edit(@PathVariable("id") String fdid, ModelMap m, HttpServletRequest request) {
        RbacResource resource = null;
        if (StringUtil.isNotNullOrEmpty(fdid)) {
            resource = this.rbacResourceService.get(fdid);
        }
        if (resource == null) {
            resource = new RbacResource();
        }
        m.put("resource", resource);
        return "/resource/edit";
    }

    @ResponseBody
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public AjaxResult save(ModelMap m, HttpServletRequest request, RbacResource resource) {
        RbacResource res = null;
        try {
            if (StringUtil.isNotNullOrEmpty(resource) && StringUtil.isNotNullOrEmpty(resource.getFdid())) {
                res = this.rbacResourceService.get(resource.getFdid());
            }
            if (res == null) {
                res = new RbacResource();
            }
            res.setName(resource.getName());
            res.setId(resource.getId());
            res.setState(resource.getState());
            res.setShowmenu(resource.getShowmenu());
            res.setUrl(resource.getUrl());
            res.setPid(resource.getPid());
            res.setOrdernum(resource.getOrdernum());
            this.rbacResourceService.saveorupdate(res);
            return toAjax(1);
        } catch (Exception e) {
            e.printStackTrace();
            return toAjax(0);
        }
    }

    @ResponseBody
    @RequestMapping(value = "/remove/{id}", method = RequestMethod.GET)
    public AjaxResult delete(@PathVariable("id") String fdid, ModelMap m, HttpServletRequest request, RbacResource resource) {
        try {
            this.rbacResourceService.delete(fdid);
            return toAjax(1);
        } catch (Exception e) {
            e.printStackTrace();
            return toAjax(0);
        }
    }

}
