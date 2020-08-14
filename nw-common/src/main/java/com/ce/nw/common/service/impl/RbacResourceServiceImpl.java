package com.ce.nw.common.service.impl;

import com.ce.nw.common.dao.RbacResourceDao;
import com.ce.nw.common.model.RbacResource;
import com.ce.nw.common.service.IRbacResourceService;
import com.ce.nw.common.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.*;

/**
 * Created by ccxxu on 2019-08-02.
 */
@Service
public class RbacResourceServiceImpl implements IRbacResourceService {

    @Autowired
    private RbacResourceDao rbacResourceDao;

    @PersistenceContext
    private EntityManager entityManager;

    public RbacResource get(String id) {
        List<RbacResource> list = this.rbacResourceDao.selectById(id);
        if (list != null && list.size()>0) {
            return list.get(0);
        }
        return null;
    }

    public void delete(String id) {
        this.rbacResourceDao.deleteById(id);
    }

    public void saveorupdate(RbacResource resource) {
        this.rbacResourceDao.save(resource);
    }

    public List<RbacResource> selectByRoleId(String roleId) {
        return this.rbacResourceDao.selectByRoleId(roleId);
    }

    public List<RbacResource> selectByPid(String pid) {
        return this.rbacResourceDao.selectByPid(pid);
    }

    public List<RbacResource> listAll() {
        return this.rbacResourceDao.findAll();
    }

    public List<RbacResource> listAll(String type) {
        List<RbacResource> menus = new LinkedList<>();
        menus = this.rbacResourceDao.findAll();
        return getChildPerms(menus, "00");
    }

    public Page<RbacResource> getPageList(Pageable page, Specification<RbacResource> specification) {
        return this.rbacResourceDao.findAll(specification, page);
    }

    public List<RbacResource> getPageList(Specification<RbacResource> specification, Sort sort) {
        return this.rbacResourceDao.findAll(specification, sort);
    }

    public List<RbacResource> listTree(List<String> ids) {
        return this.rbacResourceDao.listTree(ids);
    }

    /**
     * 根据父节点的ID获取所有子节点
     *
     * @param list 分类表
     * @param pid 传入的父节点ID
     * @return String
     */
    public List<RbacResource> getChildPerms(List<RbacResource> list, String pid) {
        List<RbacResource> returnList = new ArrayList<>();
        for (Iterator<RbacResource> iterator = list.iterator(); iterator.hasNext();) {
            RbacResource t = iterator.next();
            // 一、根据传入的某个父节点ID,遍历该父节点的所有子节点
            if (pid.equals(t.getPid())) {
                recursionFn(list, t);
                returnList.add(t);
            }
        }
        return returnList;
    }

    /**
     * 递归列表
     *
     * @param list
     * @param t
     */
    private void recursionFn(List<RbacResource> list, RbacResource t) {
        // 得到子节点列表
        List<RbacResource> childList = getChildList(list, t);
        t.setChildren(childList);
        for (RbacResource tChild : childList)
        {
            if (hasChild(list, tChild))
            {
                // 判断是否有子节点
                Iterator<RbacResource> it = childList.iterator();
                while (it.hasNext())
                {
                    RbacResource n = it.next();
                    recursionFn(list, n);
                }
            }
        }
    }

    /**
     * 得到子节点列表
     */
    private List<RbacResource> getChildList(List<RbacResource> list, RbacResource t) {
        List<RbacResource> tlist = new ArrayList<>();
        Iterator<RbacResource> it = list.iterator();
        while (it.hasNext()) {
            RbacResource n = it.next();
            if (n.getPid().equals(t.getId())) {
                tlist.add(n);
            }
        }
        return tlist;
    }

    /**
     * 判断是否有子节点
     */
    private boolean hasChild(List<RbacResource> list, RbacResource t) {
        return getChildList(list, t).size() > 0 ? true : false;
    }

    public List<RbacResource> findAllResource() {
        return this.rbacResourceDao.findAllResource();
    }

    /**
     * 条件查询资源列表
     * @param resource
     * @return
     */
    public List<Map<String, String>> findAllResource(RbacResource resource) {
        List<Map<String, String>> list = new ArrayList<>();
        String sql = "select rr.fdid, rr.id, rr.name, rr.pid, rr.state, rr.ordernum, " +
                "to_char(rr.createtime, 'yyyy-MM-dd'), rr.showmenu from rbac_resource rr ";
        String where = "where 1=1 ";
        if (StringUtil.isNotNullOrEmpty(resource) && StringUtil.isNotNullOrEmpty(resource.getName())) {
            where += " and rr.name like :name ";
        }
        String cur = "start with rr.pid = '00' connect by prior rr.id = rr.pid order by ordernum";
        Query query = entityManager.createNativeQuery(sql+where+cur);
        if (StringUtil.isNotNullOrEmpty(resource) && StringUtil.isNotNullOrEmpty(resource.getName())) {
            query.setParameter("name", "%"+resource.getName()+"%");
        }
        List<Object[]> result = query.getResultList();
        for (int ii=0; ii<result.size(); ii++) {
            Object[] obj = result.get(ii);
            Map<String, String> res = new HashMap<String, String>();
            if (StringUtil.isNotNullOrEmpty(obj[0])) {
                res.put("fdid", obj[0]+"");
            }
            if (StringUtil.isNotNullOrEmpty(obj[1])) {
                res.put("id", obj[1]+"");
            }
            if (StringUtil.isNotNullOrEmpty(obj[2])) {
                res.put("name", obj[2]+"");
            }
            if (StringUtil.isNotNullOrEmpty(obj[3])) {
                res.put("pid", obj[3]+"");
            }
            if (StringUtil.isNotNullOrEmpty(obj[4])) {
                res.put("state", obj[4]+"");
            }
            if (StringUtil.isNotNullOrEmpty(obj[5])) {
                res.put("ordernum", obj[5]+"");
            }
            if (StringUtil.isNotNullOrEmpty(obj[6])) {
                res.put("createDate", obj[6]+"");
            }
            if (StringUtil.isNotNullOrEmpty(obj[7])) {
                res.put("showmenu", obj[7]+"");
            }
            list.add(res);
        }
        return list;
    }

}
