package com.ce.nw.common.model;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by ccxxu on 2019-07-31.
 */
@Entity
@Table(name = "RBAC_USER_ROLE")
public class RbacUserRole implements Serializable {

    //columns START
    /**
     * fdid       db_column: FDID 
     */
    @Length(max=32)
    private String fdid;
    /**
     * roleId       db_column: ROLE_ID 
     */
    @Length(max=32)
    private String roleId;
    /**
     * userId       db_column: USER_ID 
     */
    @Length(max=32)
    private String userId;
    /**
     * roleid       db_column: ROLEID 
     */
    @Length(max=32)
    private String roleid;
    /**
     * userid       db_column: USERID 
     */
    @Length(max=32)
    private String userid;

    //columns END


    public RbacUserRole(){
    }

    public RbacUserRole(String fdid){
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

    @Column(name = "ROLE_ID", unique = false, nullable = true, insertable = true, updatable = true, length = 32)
    public String getRoleId() {
        return this.roleId;
    }

    public void setRoleId(String value) {
        this.roleId = value;
    }

    @Column(name = "USER_ID", unique = false, nullable = true, insertable = true, updatable = true, length = 32)
    public String getUserId() {
        return this.userId;
    }

    public void setUserId(String value) {
        this.userId = value;
    }

    @Column(name = "ROLEID", unique = false, nullable = true, insertable = true, updatable = true, length = 32)
    public String getRoleid() {
        return this.roleid;
    }

    public void setRoleid(String value) {
        this.roleid = value;
    }

    @Column(name = "USERID", unique = false, nullable = true, insertable = true, updatable = true, length = 32)
    public String getUserid() {
        return this.userid;
    }

    public void setUserid(String value) {
        this.userid = value;
    }

}
