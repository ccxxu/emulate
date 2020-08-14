package com.ce.nw.admin.action;

import com.ce.nw.common.entity.AjaxResult;
import com.ce.nw.common.entity.Ztree;
import com.ce.nw.common.model.RbacUser;
import com.ce.nw.common.service.IRbacUserService;
import com.ce.nw.common.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * Created by ccxxu on 2019-08-08.
 */
@Controller
@RequestMapping("/admin/dept")
public class RbacDeptController extends BaseController {

    @Autowired
    private IRbacUserService rbacUserService;

    @RequestMapping("/list")
    public String list() {
        return "/dept/list";
    }

    @ResponseBody
    @RequestMapping(value="/list", method = RequestMethod.POST)
    public List<Map<String, String>> list(ModelMap m, HttpServletRequest request) {
        String an = request.getParameter("an");
        Map<String, String> searchMap = new HashMap<>();
        searchMap.put("an", an);
        List<Map<String, String>> deptlist = this.rbacUserService.findDeptBySuperId(searchMap);
        return deptlist;
    }

    /**
     * 加载部门列表树
     */
    @GetMapping("/treeData")
    @ResponseBody
    public List<Ztree> treeData() {
        List<Ztree> ztrees = this.rbacUserService.findDeptTree();
        return ztrees;
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

    @RequestMapping(value = "/add/{id}")
    public String add(@PathVariable("id") String pid, ModelMap m) {
        RbacUser user = new RbacUser();
        user.setSuperId(Long.valueOf(pid));
        m.put("user", user);
        return "/dept/add";
    }

    @RequestMapping(value = "/edit/{id}")
    public String edit(@PathVariable("id") String userId, ModelMap m) {
        RbacUser user = null;
        if (StringUtil.isNotNullOrEmpty(userId)) {
            user = this.rbacUserService.get(userId);
        }
        if (user == null) {
            user = new RbacUser();
        }
        m.put("user", user);
        return "/dept/edit";
    }

    @ResponseBody
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public AjaxResult save(RbacUser user) {
        RbacUser u = null;
        try {
            if (StringUtil.isNotNullOrEmpty(user) && StringUtil.isNotNullOrEmpty(user.getUserId())) {
                u = this.rbacUserService.get(user.getUserId()+"");
            }
            if (u == null) {
                u = new RbacUser();
                u.setState(1);
            }
            u.setAbbrName(user.getAbbrName());
            u.setAliasName(user.getAliasName());
            u.setSex(user.getSex());
            u.setObjName(user.getAbbrName());
            u.setDeptNo(user.getDeptNo());
            u.setLevelId(user.getLevelId());
            u.setLoginName(user.getLoginName());
            u.setDisplayOrder(user.getDisplayOrder());
            u.setSuperId(Long.valueOf(user.getSuperId()));
            u.setUseremail(user.getUseremail());
            u.setOutemail(user.getOutemail());
            u.setPhone(user.getPhone());
            u.setIsValid(1);
            u.setObjType(0);
            u.setMobile(user.getMobile());
            u.setIsValid(user.getIsValid());
            this.rbacUserService.save(u);
            return toAjax(1);
        } catch (Exception e) {
            e.printStackTrace();
            return toAjax(0);
        }
    }

    @ResponseBody
    @RequestMapping(value = "/remove/{id}", method = RequestMethod.GET)
    public AjaxResult delete(@PathVariable("id") String userId) {
        try {
            if (StringUtil.isNotNullOrEmpty(userId)) {
                this.rbacUserService.delete(Long.valueOf(userId));
            }
            return toAjax(1);
        } catch (Exception e) {
            e.printStackTrace();
            return toAjax(0);
        }
    }

}
