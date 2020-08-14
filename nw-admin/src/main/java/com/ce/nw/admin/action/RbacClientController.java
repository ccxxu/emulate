package com.ce.nw.admin.action;

import com.ce.nw.admin.page.TableDataView;
import com.ce.nw.common.entity.AjaxResult;
import com.ce.nw.common.model.RbacClient;
import com.ce.nw.common.service.*;
import com.ce.nw.common.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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
import java.util.Date;
import java.util.List;

/**
 * Created by ccxxu on 2019-08-13.
 */
@Controller
@RequestMapping("/admin/client")
public class RbacClientController extends BaseController {

    @Autowired
    private IRbacClientService rbacClientService;

    @RequestMapping("/list")
    public String list() {
        return "/client/list";
    }

    @ResponseBody
    @RequestMapping(value="/list", method = RequestMethod.POST)
    public TableDataView list(String pageNum, String pageSize, RbacClient client) {
        try {
            startPage();
            Pageable page1 = PageRequest.of(Integer.parseInt(pageNum) - 1, Integer.parseInt(pageSize));
            Specification<RbacClient> specification = new Specification<RbacClient>() {
                @Override
                public Predicate toPredicate(Root<RbacClient> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {

                    List<Predicate> list = new ArrayList<Predicate>();
                    if (StringUtil.isNotNullOrEmpty(client.getClientId())) {
                        Predicate p1 = criteriaBuilder.like(root.get("name").as(String.class), "%"+client.getClientId()+"%");
                        list.add(p1);
                    }
                    if (StringUtil.isNotNullOrEmpty(client.getRedirectUri())) {
                        Predicate p1 = criteriaBuilder.like(root.get("redirectUri").as(String.class), "%"+client.getRedirectUri()+"%");
                        list.add(p1);
                    }

                    return criteriaBuilder.and(list.toArray(new Predicate[0]));
                }
            };
            Page<RbacClient> pageInfo = this.rbacClientService.getPageList(page1, specification);
            return getDataTable(pageInfo);
        } catch (Exception e) {
            e.printStackTrace();
            return new TableDataView();
        }
    }

    @RequestMapping(value = "/add")
    public String add(ModelMap m, HttpServletRequest request) {
        RbacClient client = new RbacClient();
        client.setAccessTokenValidity(1800);
        client.setRefreshTokenValidity(1800);
        m.put("client", client);
        return "/client/add";
    }

    @RequestMapping(value = "/edit/{id}")
    public String edit(@PathVariable("id") String id, ModelMap m, HttpServletRequest request) {
        RbacClient client = null;
        if (StringUtil.isNotNullOrEmpty(id)) {
            client = this.rbacClientService.get(id);
        }
        if (client == null) {
            client = new RbacClient();
        }
        m.put("client", client);
        return "/client/edit";
    }

    @ResponseBody
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public AjaxResult save(ModelMap m, HttpServletRequest request, RbacClient client) {
        RbacClient c = null;
        try {
            if (StringUtil.isNotNullOrEmpty(client) && StringUtil.isNotNullOrEmpty(client.getId())) {
                c = this.rbacClientService.get(client.getId());
            }
            if (c == null) {
                c = new RbacClient();
                c.setCreateTime(new Date());
            } else {
                c.setModifyTime(new Date());
            }
            c.setAccessTokenValidity(client.getAccessTokenValidity());
            c.setRefreshTokenValidity(client.getRefreshTokenValidity());
            c.setAdditionalInformation(client.getAdditionalInformation());
            c.setAuthorities(client.getAuthorities());
            c.setAutoapprove(client.getAutoapprove());
            c.setClientId(client.getClientId());
            c.setClientName(client.getClientName());
            c.setClientPwd(client.getClientPwd());
            BCryptPasswordEncoder bcryptPasswordEncoder = new BCryptPasswordEncoder();
            c.setClientSecret(bcryptPasswordEncoder.encode(client.getClientPwd()));
            c.setEnable(client.getEnable());
            c.setClientSort(client.getClientSort());
            c.setResourceIds(client.getResourceIds());
            c.setRedirectUri(client.getRedirectUri());
            c.setGrantTypes(client.getGrantTypes());
            c.setScope(client.getScope());
            this.rbacClientService.save(c);
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
                for (String id : idss) {
                    this.rbacClientService.delete(id);
                }
            }
            return toAjax(1);
        } catch (Exception e) {
            e.printStackTrace();
            return toAjax(0);
        }
    }

}
