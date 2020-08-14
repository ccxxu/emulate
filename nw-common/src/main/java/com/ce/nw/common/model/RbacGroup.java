package com.ce.nw.common.model;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by ccxxu on 2019-07-31.
 */
@Entity
@Table(name = "RBAC_GROUP")
public class RbacGroup implements Serializable {

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
     * 创建时间串       db_column: CREATETIMESTRING
     */
    @Length(max=255)
    private String createtimestring;

    /**
     * 创建人呢       db_column: CREATOR
     */
    @Length(max=100)
    private String creator;

    /**
     * 更新时间       db_column: UPDATETIME
     */
    private Date updatetime;

    /**
     * 更新时间串       db_column: UPDATETIMESTRING
     */
    @Length(max=255)
    private String updatetimestring;

    /**
     * 更新人       db_column: UPDATOR
     */
    @Length(max=100)
    private String updator;

    /**
     * 描述       db_column: DESCC
     */
    @Length(max=1000)
    private String descc;

    /**
     * 名称       db_column: NAME
     */
    @Length(max=100)
    private String name;

    /**
     * state       db_column: STATE
     */
    @Length(max=10)
    private String state;

    /**
     * 排序       db_column: GROUP_SORT
     */
    private Integer groupSort;

    /**
     * id       db_column: ID
     */
    @Length(max=100)
    private String id;

    private boolean checked = false;

    public RbacGroup(){

    }

    public RbacGroup(String fdid){
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

    @Column(name = "DESCC", unique = false, nullable = true, insertable = true, updatable = true, length = 1000)
    public String getDescc() {
        return this.descc;
    }

    public void setDescc(String value) {
        this.descc = value;
    }

    @Column(name = "NAME", unique = false, nullable = true, insertable = true, updatable = true, length = 100)
    public String getName() {
        return this.name;
    }

    public void setName(String value) {
        this.name = value;
    }

    @Column(name = "STATE", unique = false, nullable = true, insertable = true, updatable = true, length = 5)
    public String getState() {
        return this.state;
    }

    public void setState(String value) {
        this.state = value;
    }

    @Column(name = "GROUP_SORT", unique = false, nullable = true, insertable = true, updatable = true, length = 10)
    public Integer getGroupSort() {
        return groupSort;
    }

    public void setGroupSort(Integer groupSort) {
        this.groupSort = groupSort;
    }

    @Column(name = "ID", unique = false, nullable = true, insertable = true, updatable = true, length = 100)
    public String getId() {
        return this.id;
    }

    public void setId(String value) {
        this.id = value;
    }

    @Transient
    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }
}
