package com.ce.nw.common.model;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by ccxxu on 2019-07-31.
 */
@Entity
@Table(name = "RBAC_USER_RESOURCE")
public class RbacUserResource implements Serializable {
    
    //columns START
    /**
     * 该表主键       db_column: ID
     */
    @Length(max=128)
    private String id;
    /**
     * 人员编号       db_column: USER_ID
     */

    private Long userId;
    /**
     * 人员名称       db_column: USER_NAME
     */
    @Length(max=255)
    private String userName;
    /**
     * 用户角色       db_column: USER_ROLE
     */
    @Length(max=255)
    private String userRole;
    /**
     * 用户分配资源       db_column: USER_RESOURCE
     */
    @Length(max=255)
    private String userResource;
    //columns END


    public RbacUserResource(){
    }

    public RbacUserResource(String id){
        this.id = id;
    }

    public void setId(String value) {
        this.id = value;
    }

    @Id
    @GeneratedValue(generator="uuid")
    @GenericGenerator(name="uuid",strategy="uuid.hex")
    @Column(length=32)
    public String getId() {
        return this.id;
    }

    @Column(name = "USER_ID", unique = false, nullable = true, insertable = true, updatable = true, length = 10)
    public Long getUserId() {
        return this.userId;
    }

    public void setUserId(Long value) {
        this.userId = value;
    }

    @Column(name = "USER_NAME", unique = false, nullable = true, insertable = true, updatable = true, length = 255)
    public String getUserName() {
        return this.userName;
    }

    public void setUserName(String value) {
        this.userName = value;
    }

    @Column(name = "USER_ROLE", unique = false, nullable = true, insertable = true, updatable = true, length = 255)
    public String getUserRole() {
        return this.userRole;
    }

    public void setUserRole(String value) {
        this.userRole = value;
    }

    @Column(name = "USER_RESOURCE", unique = false, nullable = true, insertable = true, updatable = true, length = 255)
    public String getUserResource() {
        return this.userResource;
    }

    public void setUserResource(String value) {
        this.userResource = value;
    }
}
