package com.ce.nw.common.service.impl;

import com.ce.nw.common.dao.RbacUserDao;
import com.ce.nw.common.entity.Ztree;
import com.ce.nw.common.model.RbacUser;
import com.ce.nw.common.service.IRbacUserService;
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
 * Created by ccxxu on 2019-07-31.
 */
@Service
public class RbacUserServiceImpl implements IRbacUserService {

    @Autowired
    private RbacUserDao rbacUserDao;

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public RbacUser getUser() {
        RbacUser user = this.rbacUserDao.getOne(2910L);
        return user;
    }

    @Override
    public void save(RbacUser user) {
        this.rbacUserDao.save(user);
    }

    @Override
    public RbacUser getUserByUsername(String username) {
        List<RbacUser> user = this.rbacUserDao.findByUsername(username);
        if (user != null && user.size()>0) {
            return user.get(0);
        }
        return null;
    }

    public List<RbacUser> fingBySuperId(Long superId) {
        return this.rbacUserDao.fingBySuperId(superId);
    }

    public List<RbacUser> fingBySuperId2(Long superId) {
        return this.rbacUserDao.fingBySuperId2(superId);
    }

    public List<RbacUser> findUpBySuperId(Long superId) {
        return this.rbacUserDao.findUpBySuperId(superId);
    }

    public List<RbacUser> listTree(List<Long> ids) {
        return this.rbacUserDao.listTree(ids);
    }

    public List<RbacUser> getPageList(Specification<RbacUser> specification, Sort sort) {
        return this.rbacUserDao.findAll(specification, sort);
    }

    public Page<RbacUser> getPageList(Pageable page, Specification<RbacUser> specification) {
        return this.rbacUserDao.findAll(specification, page);
    }

    public RbacUser get(String userId) {
        if (StringUtil.isNotNullOrEmpty(userId)) {
            return this.rbacUserDao.get(Long.valueOf(userId));
        }
        return null;
    }

    @Override
    public void delete(Long userId) {
        this.rbacUserDao.deleteById(userId);
    }

    @Override
    public List<RbacUser> findAllDept() {
        List<RbacUser> list = this.rbacUserDao.findAllDept();
        return list;
    }

    @Override
    public List<Ztree> findDeptTree() {
        List<RbacUser> list = this.rbacUserDao.findAllDept();
        List<Ztree> ztrees = initZtree(list);
        return ztrees;
    }

    /**
     * 对象转部门树
     *
     * @param deptList 部门列表
     * @return 树结构列表
     */
    public List<Ztree> initZtree(List<RbacUser> deptList) {
        return initZtree(deptList, null);
    }

    /**
     * 对象转部门树
     *
     * @param deptList 部门列表
     * @param roleDeptList 角色已存在菜单列表
     * @return 树结构列表
     */
    public List<Ztree> initZtree(List<RbacUser> deptList, List<String> roleDeptList) {
        List<Ztree> ztrees = new ArrayList<Ztree>();
        boolean isCheck = StringUtil.isNotNullOrEmpty(roleDeptList);
        for (RbacUser dept : deptList) {
//            if ("1".equals(dept.getState())) {
                Ztree ztree = new Ztree();
                ztree.setId(dept.getUserId()+"");
                ztree.setpId(dept.getSuperId()+"");
                ztree.setName(dept.getAbbrName());
                ztree.setTitle(dept.getAliasName());
                if (isCheck) {
                    ztree.setChecked(roleDeptList.contains(dept.getUserId() + dept.getAbbrName()));
                }
                ztrees.add(ztree);
//            }
        }
        return ztrees;
    }

    public List<Map<String, String>> findDeptBySuperId(Map<String, String> searchMap) {
        List<Map<String, String>> list = new ArrayList<>();
        String sql = "select ru.user_id, ru.abbr_name, ru.useremail, ru.phone, " +
                "(select abbr_name from rbac_user ch where ch.user_id=ru.super_id)," +
                "ru.state,to_char(ru.create_date, 'yyyy-MM-dd'), ru.obj_name, " +
                "ru.display_order, ru.super_id from rbac_user ru ";
        String where = "where ru.obj_type=0 ";
        String an = searchMap.get("an");
        if (StringUtil.isNotNullOrEmpty(an)) {
            where += " and ru.abbr_name like :an ";
        }
        String cur = " start with ru.user_id = :userId connect by prior ru.user_id = ru.super_id order by ru.display_order";
        Query query = entityManager.createNativeQuery(sql+where+cur);
        String parentId = searchMap.get("parentId");
        if (StringUtil.isNotNullOrEmpty(parentId)) {
            query.setParameter("userId", Integer.valueOf(parentId));
        } else {
            query.setParameter("userId", 1);
        }
        if (StringUtil.isNotNullOrEmpty(an)) {
            query.setParameter("an", "%"+an+"%");
        }
        String pageNum = searchMap.get("pageNum");
        String pageSize = searchMap.get("pageSize");
        if (StringUtil.isNotNullOrEmpty(pageNum) && StringUtil.isNotNullOrEmpty(pageSize)) {
            query.setFirstResult((Integer.valueOf(pageNum)-1)*Integer.valueOf(pageSize));
            query.setMaxResults(Integer.valueOf(pageSize));
        }
        List<Object[]> result = query.getResultList();
        for (int ii=0; ii<result.size(); ii++) {
            Object[] obj = result.get(ii);
            Map<String, String> user = new HashMap<String, String>();
            if (StringUtil.isNotNullOrEmpty(obj[0])) {
                user.put("userId", obj[0]+"");
            }
            if (StringUtil.isNotNullOrEmpty(obj[1])) {
                user.put("abbrName", obj[1]+"");
            }
            if (StringUtil.isNotNullOrEmpty(obj[2])) {
                user.put("useremail", obj[2]+"");
            }
            if (StringUtil.isNotNullOrEmpty(obj[3])) {
                user.put("phone", obj[3]+"");
            }
            if (StringUtil.isNotNullOrEmpty(obj[4])) {
                user.put("deptName", obj[4]+"");
            }
            if (StringUtil.isNotNullOrEmpty(obj[5])) {
                user.put("state", obj[5]+"");
            }
            if (StringUtil.isNotNullOrEmpty(obj[6])) {
                user.put("createDate", obj[6]+"");
            }
            if (StringUtil.isNotNullOrEmpty(obj[7])) {
                user.put("objName", obj[7]+"");
            }
            if (StringUtil.isNotNullOrEmpty(obj[8])) {
                user.put("displayOrder", obj[8]+"");
            }
            if (StringUtil.isNotNullOrEmpty(obj[9])) {
                user.put("superId", obj[9]+"");
            }
            list.add(user);
        }
        return list;
    }

    public List<Map<String, String>> findUserBySuperId(Map<String, String> searchMap) {
        List<Map<String, String>> list = new ArrayList<>();
        String sql = "select ru.user_id, ru.abbr_name, ru.useremail, ru.phone, " +
                "(select abbr_name from rbac_user ch where ch.user_id=ru.super_id)," +
                "ru.state,to_char(ru.create_date, 'yyyy-MM-dd') from rbac_user ru ";
        String where = "where ru.obj_type=1 ";
        String cur = "start with ru.user_id = :userId connect by prior ru.user_id = ru.super_id";
        Query query = entityManager.createNativeQuery(sql+where+cur);
        String parentId = searchMap.get("parentId");
        if (StringUtil.isNotNullOrEmpty(parentId)) {
            query.setParameter("userId", Integer.valueOf(parentId));
        } else {
            query.setParameter("userId", 1);
        }
        String pageNum = searchMap.get("pageNum");
        String pageSize = searchMap.get("pageSize");
        if (StringUtil.isNotNullOrEmpty(pageNum) && StringUtil.isNotNullOrEmpty(pageSize)) {
            query.setFirstResult((Integer.valueOf(pageNum)-1)*Integer.valueOf(pageSize));
            query.setMaxResults(Integer.valueOf(pageSize));
        }
        List<Object[]> result = query.getResultList();
        for (int ii=0; ii<result.size(); ii++) {
            Object[] obj = result.get(ii);
            Map<String, String> user = new HashMap<String, String>();
            if (StringUtil.isNotNullOrEmpty(obj[0])) {
                user.put("userId", obj[0]+"");
            }
            if (StringUtil.isNotNullOrEmpty(obj[1])) {
                user.put("abbrName", obj[1]+"");
            }
            if (StringUtil.isNotNullOrEmpty(obj[2])) {
                user.put("useremail", obj[2]+"");
            }
            if (StringUtil.isNotNullOrEmpty(obj[3])) {
                user.put("phone", obj[3]+"");
            }
            if (StringUtil.isNotNullOrEmpty(obj[4])) {
                user.put("deptName", obj[4]+"");
            }
            if (StringUtil.isNotNullOrEmpty(obj[5])) {
                user.put("state", obj[5]+"");
            }
            if (StringUtil.isNotNullOrEmpty(obj[6])) {
                user.put("createDate", obj[6]+"");
            }
            list.add(user);
        }
        return list;
    }

    public Integer findUserCountBySuperId(Map<String, String> searchMap) {
        String sql = "select count(1) from rbac_user ";
        String where = "where obj_type=1 ";
        String cur = "start with user_id=:userId connect by prior user_id=super_id";
        Query query = entityManager.createNativeQuery(sql+where+cur);
        String parentId = searchMap.get("parentId");
        if (StringUtil.isNotNullOrEmpty(parentId)) {
            query.setParameter("userId", Integer.valueOf(parentId));
        } else {
            query.setParameter("userId", 1);
        }
        Object obj = query.getSingleResult();
        if (StringUtil.isNotNullOrEmpty(obj)) {
            return Integer.valueOf(obj+"");
        }
        return 0;
    }
}
