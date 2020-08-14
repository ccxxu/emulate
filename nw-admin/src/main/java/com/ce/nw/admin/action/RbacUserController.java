package com.ce.nw.admin.action;

import com.ce.nw.admin.page.TableDataView;
import com.ce.nw.common.entity.AjaxResult;
import com.ce.nw.common.model.RbacResource;
import com.ce.nw.common.model.RbacUser;
import com.ce.nw.common.model.UserDetailModel;
import com.ce.nw.common.service.IRbacUserService;
import com.ce.nw.common.util.MD5Util;
import com.ce.nw.common.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.*;

/**
 * Created by ccxxu on 2019-08-08.
 */
@Controller
@RequestMapping("/admin/user")
public class RbacUserController extends BaseController {

    @Autowired
    private IRbacUserService rbacUserService;

    @RequestMapping("/list")
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
    @RequestMapping(value="/list", method = RequestMethod.POST)
    public TableDataView list(RbacUser user, HttpServletRequest request) {
        try {
            TableDataView view = new TableDataView();
            Map<String, String> searchMap = new HashMap<>();
            String parentId = request.getParameter("deptId");
            String pageNum = request.getParameter("pageNum");
            String pageSize = request.getParameter("pageSize");
            searchMap.put("parentId", parentId);
            searchMap.put("pageNum", pageNum);
            searchMap.put("pageSize", pageSize);
            List<Map<String, String>> list = this.rbacUserService.findUserBySuperId(searchMap);
            Integer total = this.rbacUserService.findUserCountBySuperId(searchMap);

            view.setCode(0);
            view.setRows(list);
            view.setTotal(total);

            return view;
        } catch (Exception e) {
            e.printStackTrace();
            return new TableDataView();
        }
    }

    @RequestMapping(value = "/add/{id}")
    public String add(@PathVariable("id") String pid, ModelMap m, HttpServletRequest request) {
        RbacUser user = new RbacUser();
        if (StringUtil.isNotNullOrEmpty(pid)) {
            user.setSuperId(Long.valueOf(pid));
        } else {
            user.setSuperId(1L);
        }
        m.put("user", user);
        return "/user/add";
    }

    @RequestMapping(value = "/edit/{id}")
    public String edit(@PathVariable("id") String userId, ModelMap m, HttpServletRequest request) {
        RbacUser user = null;
        if (StringUtil.isNotNullOrEmpty(userId)) {
            user = this.rbacUserService.get(userId);
        }
        if (user == null) {
            user = new RbacUser();
        }
        m.put("user", user);
        return "/user/edit";
    }

    @ResponseBody
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public AjaxResult save(ModelMap m, HttpServletRequest request, RbacUser user) {
        RbacUser u = null;
        try {
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
            if (StringUtil.isNotNullOrEmpty(user) && StringUtil.isNotNullOrEmpty(user.getIsValid())) {
                u.setIsValid(user.getIsValid());
            } else {
                u.setIsValid(0);
            }
            u.setObjType(1);
            if (StringUtil.isNotNullOrEmpty(user) && StringUtil.isNotNullOrEmpty(user.getState())) {
                u.setState(user.getState());
            } else {
                u.setState(0);
            }
            this.rbacUserService.save(u);
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
                String[] uids = ids.split(",");
                for (String uid : uids) {
                    this.rbacUserService.delete(Long.valueOf(uid));
                }
            }
            return toAjax(1);
        } catch (Exception e) {
            e.printStackTrace();
            return toAjax(0);
        }
    }

    @ResponseBody
    @RequestMapping(value = "/user/reset", method = RequestMethod.POST)
    public AjaxResult resetPwd(ModelMap m, HttpServletRequest request) {
        String userId = request.getParameter("userId");
        try {
            if (StringUtil.isNotNullOrEmpty(userId)) {
                RbacUser user = this.rbacUserService.get(userId);
                if (user != null) {
                    user.setPassword("a00000000000");
                    user.setPasswordmd(MD5Util.md5("a00000000000"));
                    this.rbacUserService.save(user);
                }
            }
            return toAjax(1);
        } catch (Exception e) {
            e.printStackTrace();
            return toAjax(0);
        }
    }

    @ResponseBody
    @RequestMapping(value = "/change/password", method = RequestMethod.POST)
    public AjaxResult resetPwd02(ModelMap m, HttpServletRequest request, RbacResource resource) {
        HttpSession session = request.getSession();
        String opwd = request.getParameter("opwd");
        String npwdo = request.getParameter("npwdo");
        String npwdt = request.getParameter("npwdt");
        UserDetailModel udm = (UserDetailModel) session.getAttribute("s_member");
        try {
            if (StringUtil.isNotNullOrEmpty(opwd)) {
                if (StringUtil.isNotNullOrEmpty(udm) && StringUtil.isNotNullOrEmpty(udm.getId())) {
                    RbacUser user = this.rbacUserService.get(udm.getId());
                    if (user != null && opwd.equals(user.getPassword()) && npwdo.equals(npwdt)) {
                        user.setPassword(npwdo);
                        user.setPasswordmd(MD5Util.md5(npwdo));
                        this.rbacUserService.save(user);
                    }
                }
            }
            return toAjax(1);
        } catch (Exception e) {
            e.printStackTrace();
            return toAjax(0);
        }
    }


}
