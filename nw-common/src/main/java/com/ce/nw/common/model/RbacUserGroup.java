package com.ce.nw.common.model;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by ccxxu on 2019-07-31.
 */
@Entity
@Table(name = "RBAC_USER_GROUP")
public class RbacUserGroup implements Serializable {

    //columns START
    /**
     * fdid       db_column: FDID
     */
    @Length(max=32)
    private String fdid;
    /**
     * groupId       db_column: GROUP_ID
     */
    @Length(max=32)
    private String groupId;
    /**
     * userId       db_column: USER_ID
     */
    @Length(max=32)
    private String userId;
    /**
     * groupid       db_column: GROUPID
     */
    @Length(max=32)
    private String groupid;
    /**
     * userid       db_column: USERID
     */
    @Length(max=32)
    private String userid;
    //columns END


    public RbacUserGroup(){
    }

    public RbacUserGroup(String fdid){
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

    @Column(name = "GROUP_ID", unique = false, nullable = true, insertable = true, updatable = true, length = 32)
    public String getGroupId() {
        return this.groupId;
    }

    public void setGroupId(String value) {
        this.groupId = value;
    }

    @Column(name = "USER_ID", unique = false, nullable = true, insertable = true, updatable = true, length = 32)
    public String getUserId() {
        return this.userId;
    }

    public void setUserId(String value) {
        this.userId = value;
    }

    @Column(name = "GROUPID", unique = false, nullable = true, insertable = true, updatable = true, length = 32)
    public String getGroupid() {
        return this.groupid;
    }

    public void setGroupid(String value) {
        this.groupid = value;
    }

    @Column(name = "USERID", unique = false, nullable = true, insertable = true, updatable = true, length = 32)
    public String getUserid() {
        return this.userid;
    }

    public void setUserid(String value) {
        this.userid = value;
    }
}
