package com.ce.nw.common.model;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by ccxxu on 2019-07-31.
 */
@Entity
@Table(name = "RBAC_ROLE_RESOURCE")
public class RbacRoleResource implements Serializable {
    
    //columns START
    /**
     * id       db_column: ID 
     */
    @Length(max=32)
    private String id;
    /**
     * roleId       db_column: ROLE_ID 
     */
    @Length(max=32)
    private String roleId;
    /**
     * resourceId       db_column: RESOURCE_ID 
     */
    @Length(max=32)
    private String resourceId;
    //columns END


    public RbacRoleResource(){
    }

    public RbacRoleResource(String id){
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

    @Column(name = "ROLE_ID", unique = false, nullable = true, insertable = true, updatable = true, length = 32)
    public String getRoleId() {
        return this.roleId;
    }

    public void setRoleId(String value) {
        this.roleId = value;
    }

    @Column(name = "RESOURCE_ID", unique = false, nullable = true, insertable = true, updatable = true, length = 32)
    public String getResourceId() {
        return this.resourceId;
    }

    public void setResourceId(String value) {
        this.resourceId = value;
    }
}
