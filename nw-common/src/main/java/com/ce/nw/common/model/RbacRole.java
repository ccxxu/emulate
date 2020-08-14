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
@Table(name = "RBAC_ROLE")
public class RbacRole implements Serializable {
    
    //columns START
    /**
     * fdid       db_column: FDID 
     */
    @Length(max=32)
    private String fdid;
    /**
     * createtime       db_column: CREATETIME 
     */

    private Date createtime;
    /**
     * createtimestring       db_column: CREATETIMESTRING 
     */
    @Length(max=2)
    private String status;
    /**
     * creator       db_column: CREATOR 
     */
    @Length(max=100)
    private String creator;
    /**
     * updatetime       db_column: UPDATETIME 
     */

    private Date updatetime;
    /**
     * updatetimestring       db_column: UPDATETIMESTRING 
     */
    @Length(max=100)
    private String code;
    /**
     * updator       db_column: UPDATOR 
     */
    @Length(max=100)
    private String updator;
    /**
     * name       db_column: NAME 
     */
    @Length(max=100)
    private String name;
    /**
     * notes       db_column: NOTES 
     */
    @Length(max=255)
    private String notes;

    /*
     * roleSort     db_column: ROLE_SORT
     */
    private Integer roleSort;

    private boolean checked = false;

    //columns END

    public RbacRole(){
    }

    public RbacRole(String fdid){
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

    @Column(name = "STATUS", unique = false, nullable = true, insertable = true, updatable = true, length = 2)
    public String getStatus() {
        return this.status;
    }

    public void setStatus(String status) {
        this.status = status;
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

    @Column(name = "CODE", unique = false, nullable = true, insertable = true, updatable = true, length = 100)
    public String getCode() {
        return this.code;
    }

    public void setCode(String value) {
        this.code = value;
    }

    @Column(name = "UPDATOR", unique = false, nullable = true, insertable = true, updatable = true, length = 100)
    public String getUpdator() {
        return this.updator;
    }

    public void setUpdator(String value) {
        this.updator = value;
    }

    @Column(name = "NAME", unique = false, nullable = true, insertable = true, updatable = true, length = 100)
    public String getName() {
        return this.name;
    }

    public void setName(String value) {
        this.name = value;
    }

    @Column(name = "NOTES", unique = false, nullable = true, insertable = true, updatable = true, length = 255)
    public String getNotes() {
        return this.notes;
    }

    public void setNotes(String value) {
        this.notes = value;
    }

    @Column(name = "ROLE_SORT", unique = false, nullable = true, insertable = true, updatable = true, length = 10)
    public Integer getRoleSort() {
        return roleSort;
    }

    public void setRoleSort(Integer roleSort) {
        this.roleSort = roleSort;
    }

    @Transient
    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

}
