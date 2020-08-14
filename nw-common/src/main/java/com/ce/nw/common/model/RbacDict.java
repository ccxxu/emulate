package com.ce.nw.common.model;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by ccxxu on 2019-07-31.
 */
@Entity
@Table(name = "RBAC_DICT")
public class RbacDict implements Serializable {

    /**
     * 主键id       db_column: FDID
     */
    @Length(max=32)
    private String fdid;

    /**
     * ID
     */
    @Length(max=50)
    private String id;

    /**
     * 代码键-Key
     */
    @Length(max=50)
    private String code;

    /**
     * 是否有效
     */
    @Length(max=2)
    private String enable;

    /**
     * 名称       db_column: NAME
     */
    @Length(max=255)
    private String name;

    /**
     * state       db_column: STATE
     */
    @Length(max=255)
    private String fullname;

    private Integer displayOrder;

    @Length(max = 50)
    private String pid;

    @Length(max = 20)
    private String state;

    public RbacDict(){

    }

    public RbacDict(String id){
        this.id = id;
    }

    @Id
    @GeneratedValue(generator="uuid")
    @GenericGenerator(name="uuid",strategy="uuid.hex")
    @Column(length=32)
    public String getFdid() {
        return this.fdid;
    }

    public void setFdid(String fdid) {
        this.fdid = fdid;
    }

    @Column(name = "ID", unique = false, nullable = true, insertable = true, updatable = true, length = 50)
    public String getId() {
        return this.id;
    }

    public void setId(String value) {
        this.id = value;
    }

    @Column(name = "CODE", unique = false, nullable = true, insertable = true, updatable = true, length = 50)
    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Column(name = "ENABLE", unique = false, nullable = true, insertable = true, updatable = true, length = 2)
    public String getEnable() {
        return this.enable;
    }

    public void setEnable(String enable) {
        this.enable = enable;
    }

    @Column(name = "FULLNAME", unique = false, nullable = true, insertable = true, updatable = true, length = 255)
    public String getFullname() {
        return this.fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    @Column(name = "NAME", unique = false, nullable = true, insertable = true, updatable = true, length = 255)
    public String getName() {
        return this.name;
    }

    public void setName(String value) {
        this.name = value;
    }

    @Column(name = "DISPLAY_ORDER", unique = false, nullable = true, insertable = true, updatable = true, length = 10)
    public Integer getDisplayOrder() {
        return this.displayOrder;
    }

    public void setDisplayOrder(Integer displayOrder) {
        this.displayOrder = displayOrder;
    }

    @Column(name = "PID", unique = false, nullable = true, insertable = true, updatable = true, length = 50)
    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    @Column(name = "STATE", unique = false, nullable = true, insertable = true, updatable = true, length = 20)
    public String getState() {
        return this.state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
