package com.ce.nw.common.model;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Created by ccxxu on 2019-07-31.
 */
@Entity
@Table(name = "RBAC_RESOURCE")
public class RbacResource implements Serializable {

    /**
     * 主键id       db_column: FDID
     */
    @Length(max=32)
    private String fdid;
    /**
     * 创建时间       db_column: CREATETIME
     */

    private Date createtime;
    /**
     * 创建时间 字符串       db_column: CREATETIMESTRING
     */
    @Length(max=255)
    private String createtimestring;
    /**
     * 创建人       db_column: CREATOR
     */
    @Length(max=100)
    private String creator;
    /**
     * 更新时间       db_column: UPDATETIME
     */

    private Date updatetime;
    /**
     * 更新时间字串       db_column: UPDATETIMESTRING
     */
    @Length(max=255)
    private String updatetimestring;
    /**
     * 更新人       db_column: UPDATOR
     */
    @Length(max=100)
    private String updator;
    /**
     * 默认状态       db_column: DEFAULTSTATUS
     */
    @Length(max=2)
    private String defaultstatus;
    /**
     * 是否有子节点       db_column: HASSUB
     */
    @Length(max=10)
    private String hassub;
    /**
     * 树形结构       db_column: HIERARCHICAL
     */
    @Length(max=600)
    private String hierarchical;
    /**
     * id       db_column: ID
     */
    @Length(max=32)
    private String id;
    /**
     * 图片路径       db_column: IMGPATH
     */
    @Length(max=500)
    private String imgpath;
    /**
     * modal模态弹出显示的高度       db_column: MODALHEIGHT
     */
    @Length(max=10)
    private String modalheight;
    /**
     * 名称       db_column: NAME
     */
    @Length(max=150)
    private String name;
    /**
     * 排序       db_column: ORDERNUM
     */
    @NotNull
    private Long ordernum;
    /**
     * 树id       db_column: PID
     */
    @Length(max=32)
    private String pid;
    /**
     * responsemode       db_column: RESPONSEMODE
     */
    @Length(max=10)
    private String responsemode;
    /**
     * 是否作为菜单显示       db_column: SHOWMENU
     */
    @Length(max=10)
    private String showmenu;
    /**
     * 是否展开       db_column: STATE
     */
    @Length(max=10)
    private String state;
    /**
     * 导航类型       db_column: SHOWTYPE
     */
    @Length(max=10)
    private String showtype;
    /**
     * 地址       db_column: URL
     */
    @Length(max=500)
    private String url;

    /**
     * 菜单图标
     */
    private String icon;

    /**
     * 打开方式：menuIndex页签  menuBlank新窗口
     */
    private String target;

    /**
     * 子菜单
     */
    private List<RbacResource> children;


    //columns END

    public RbacResource(){
    }

    public RbacResource(String fdid){
        this.fdid = fdid;
    }

    public void setFdid(String value) {
        this.fdid = value;
    }

    @Id
    @GeneratedValue(generator="uuid")
    @GenericGenerator(name="uuid",strategy="uuid.hex")
    @Column(length=32)
    public String getFdid() {
        return this.fdid;
    }

    @Column(name = "CREATETIME", unique = false, nullable = true, insertable = true, updatable = true, length = 7)
    public Date getCreatetime() {
        return this.createtime;
    }

    public void setCreatetime(Date value) {
        this.createtime = value;
    }

    @Column(name = "CREATETIMESTRING", unique = false, nullable = true, insertable = true, updatable = true, length = 255)
    public String getCreatetimestring() {
        return this.createtimestring;
    }

    public void setCreatetimestring(String value) {
        this.createtimestring = value;
    }

    @Column(name = "CREATOR", unique = false, nullable = true, insertable = true, updatable = true, length = 100)
    public String getCreator() {
        return this.creator;
    }

    public void setCreator(String value) {
        this.creator = value;
    }

    @Column(name = "UPDATETIME", unique = false, nullable = true, insertable = true, updatable = true, length = 7)
    public Date getUpdatetime() {
        return this.updatetime;
    }

    public void setUpdatetime(Date value) {
        this.updatetime = value;
    }

    @Column(name = "UPDATETIMESTRING", unique = false, nullable = true, insertable = true, updatable = true, length = 255)
    public String getUpdatetimestring() {
        return this.updatetimestring;
    }

    public void setUpdatetimestring(String value) {
        this.updatetimestring = value;
    }

    @Column(name = "UPDATOR", unique = false, nullable = true, insertable = true, updatable = true, length = 100)
    public String getUpdator() {
        return this.updator;
    }

    public void setUpdator(String value) {
        this.updator = value;
    }

    @Column(name = "DEFAULTSTATUS", unique = false, nullable = true, insertable = true, updatable = true, length = 2)
    public String getDefaultstatus() {
        return this.defaultstatus;
    }

    public void setDefaultstatus(String value) {
        this.defaultstatus = value;
    }

    @Column(name = "HASSUB", unique = false, nullable = true, insertable = true, updatable = true, length = 10)
    public String getHassub() {
        return this.hassub;
    }

    public void setHassub(String value) {
        this.hassub = value;
    }

    @Column(name = "HIERARCHICAL", unique = false, nullable = true, insertable = true, updatable = true, length = 600)
    public String getHierarchical() {
        return this.hierarchical;
    }

    public void setHierarchical(String value) {
        this.hierarchical = value;
    }

    @Column(name = "ID", unique = false, nullable = true, insertable = true, updatable = true, length = 32)
    public String getId() {
        return this.id;
    }

    public void setId(String value) {
        this.id = value;
    }

    @Column(name = "IMGPATH", unique = false, nullable = true, insertable = true, updatable = true, length = 500)
    public String getImgpath() {
        return this.imgpath;
    }

    public void setImgpath(String value) {
        this.imgpath = value;
    }

    @Column(name = "MODALHEIGHT", unique = false, nullable = true, insertable = true, updatable = true, length = 10)
    public String getModalheight() {
        return this.modalheight;
    }

    public void setModalheight(String value) {
        this.modalheight = value;
    }

    @Column(name = "NAME", unique = false, nullable = true, insertable = true, updatable = true, length = 150)
    public String getName() {
        return this.name;
    }

    public void setName(String value) {
        this.name = value;
    }

    @Column(name = "ORDERNUM", unique = false, nullable = false, insertable = true, updatable = true, length = 10)
    public Long getOrdernum() {
        return this.ordernum;
    }

    public void setOrdernum(Long value) {
        this.ordernum = value;
    }

    @Column(name = "PID", unique = false, nullable = true, insertable = true, updatable = true, length = 32)
    public String getPid() {
        return this.pid;
    }

    public void setPid(String value) {
        this.pid = value;
    }

    @Column(name = "RESPONSEMODE", unique = false, nullable = true, insertable = true, updatable = true, length = 10)
    public String getResponsemode() {
        return this.responsemode;
    }

    public void setResponsemode(String value) {
        this.responsemode = value;
    }

    @Column(name = "SHOWMENU", unique = false, nullable = true, insertable = true, updatable = true, length = 10)
    public String getShowmenu() {
        return this.showmenu;
    }

    public void setShowmenu(String value) {
        this.showmenu = value;
    }

    @Column(name = "STATE", unique = false, nullable = true, insertable = true, updatable = true, length = 10)
    public String getState() {
        return this.state;
    }

    public void setState(String value) {
        this.state = value;
    }

    @Column(name = "SHOWTYPE", unique = false, nullable = true, insertable = true, updatable = true, length = 10)
    public String getShowtype() {
        return this.showtype;
    }

    public void setShowtype(String value) {
        this.showtype = value;
    }

    @Column(name = "URL", unique = false, nullable = true, insertable = true, updatable = true, length = 500)
    public String getUrl() {
        return this.url;
    }

    public void setUrl(String value) {
        this.url = value;
    }

    @Column(name = "ICON", unique = false, nullable = true, insertable = true, updatable = true, length = 255)
    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    @Column(name = "TARGET", unique = false, nullable = true, insertable = true, updatable = true, length = 255)
    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    @Transient
    public List<RbacResource> getChildren() {
        return children;
    }

    public void setChildren(List<RbacResource> children) {
        this.children = children;
    }
}
