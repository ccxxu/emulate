package com.ce.nw.common.model;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import java.util.Date;

/**
 * @author ccx
 * @description 项目中, 主要操作oauth_client_details表的类是JdbcClientDetailsService.java,
 * 更多的细节请参考该类. 也可以根据实际的需要,去扩展或修改该类的实现.
 * @date 2019-09-28
 */
@Entity
@Table(name = "RBAC_CLIENT")
public class RbacClient {

    @Length(max=32)
    private String id;

    private String clientId;

    private String resourceIds;

    private String clientSecret;

    private String scope;

    private String grantTypes;

    private String redirectUri;

    private String authorities;

    private Integer accessTokenValidity;

    private Integer refreshTokenValidity;

    private String additionalInformation;

    private String autoapprove;

    private Date createTime;

    private Date modifyTime;

    private String clientName;

    private Integer clientSort;

    private String enable;

    private String clientPwd;

    @Id
    @GeneratedValue(generator="uuid")
    @GenericGenerator(name="uuid",strategy="uuid.hex")
    @Column(length=32)
    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }


    @Column(name = "CLIENT_ID", unique = false, nullable = true, insertable = true, updatable = true, length = 128)
    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }


    @Column(name = "RESOURCE_IDS", unique = false, nullable = true, insertable = true, updatable = true, length = 255)
    public String getResourceIds() {
        return resourceIds;
    }

    public void setResourceIds(String resourceIds) {
        this.resourceIds = resourceIds;
    }


    @Column(name = "CLIENT_SECRET", unique = false, nullable = true, insertable = true, updatable = true, length = 255)
    public String getClientSecret() {
        return clientSecret;
    }

    public void setClientSecret(String clientSecret) {
        this.clientSecret = clientSecret;
    }


    @Column(name = "SCOPE", unique = false, nullable = true, insertable = true, updatable = true, length = 255)
    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }


    @Column(name = "GRANT_TYPES", unique = false, nullable = true, insertable = true, updatable = true, length = 255)
    public String getGrantTypes() {
        return this.grantTypes;
    }

    public void setGrantTypes(String grantTypes) {
        this.grantTypes = grantTypes;
    }


    @Column(name = "REDIRECT_URI", unique = false, nullable = true, insertable = true, updatable = true, length = 100)
    public String getRedirectUri() {
        return redirectUri;
    }

    public void setRedirectUri(String redirectUri) {
        this.redirectUri = redirectUri;
    }


    @Column(name = "AUTHORITIES", unique = false, nullable = true, insertable = true, updatable = true, length = 100)
    public String getAuthorities() {
        return authorities;
    }

    public void setAuthorities(String authorities) {
        this.authorities = authorities;
    }


    @Column(name = "ACCESS_TOKEN_VALIDITY", unique = false, nullable = true, insertable = true, updatable = true, length = 11)
    public Integer getAccessTokenValidity() {
        return this.accessTokenValidity;
    }

    public void setAccessTokenValidity(Integer accessTokenValidity) {
        this.accessTokenValidity = accessTokenValidity;
    }


    @Column(name = "REFRESH_TOKEN_VALIDITY", unique = false, nullable = true, insertable = true, updatable = true, length = 11)
    public Integer getRefreshTokenValidity() {
        return this.refreshTokenValidity;
    }

    public void setRefreshTokenValidity(Integer refreshTokenValidity) {
        this.refreshTokenValidity = refreshTokenValidity;
    }

    @Column(name = "AUTOAPPROVE", unique = false, nullable = true, insertable = true, updatable = true, length = 255)
    public String getAutoapprove() {
        return this.autoapprove;
    }

    public void setAutoapprove(String autoapprove) {
        this.autoapprove = autoapprove;
    }


    @Column(name = "ADDITIONAL_INFORMATION", unique = false, nullable = true, insertable = true, updatable = true, length = 255)
    public String getAdditionalInformation() {
        return additionalInformation;
    }

    public void setAdditionalInformation(String additionalInformation) {
        this.additionalInformation = additionalInformation;
    }

    @Column(name = "CREATE_TIME", unique = false, nullable = true, insertable = true, updatable = true, length = 7)
    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Column(name = "MODIFY_TIME", unique = false, nullable = true, insertable = true, updatable = true, length = 7)
    public Date getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }

    @Column(name = "CLIENT_NAME", unique = false, nullable = true, insertable = true, updatable = true, length = 255)
    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    @Column(name = "CLIENT_SORT", unique = false, nullable = true, insertable = true, updatable = true, length = 11)
    public Integer getClientSort() {
        return clientSort;
    }

    public void setClientSort(Integer clientSort) {
        this.clientSort = clientSort;
    }

    @Column(name = "ENABLE", unique = false, nullable = true, insertable = true, updatable = true, length = 2)
    public String getEnable() {
        return enable;
    }

    public void setEnable(String enable) {
        this.enable = enable;
    }

    @Column(name = "CLIENT_PWD", unique = false, nullable = true, insertable = true, updatable = true, length = 255)
    public String getClientPwd() {
        return clientPwd;
    }

    public void setClientPwd(String clientPwd) {
        this.clientPwd = clientPwd;
    }

}
