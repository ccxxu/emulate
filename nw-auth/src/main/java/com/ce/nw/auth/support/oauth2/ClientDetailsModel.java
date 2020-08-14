package com.ce.nw.auth.support.oauth2;

import com.ce.nw.auth.utils.CommonUtils;
import com.ce.nw.common.model.RbacClient;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.oauth2.provider.ClientDetails;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

/**
 * @author  ccx
 * @date    2019-09-25
 *
 **/
public final class ClientDetailsModel implements ClientDetails {

    private RbacClient client;
    private Set<String> scope;

    public ClientDetailsModel(RbacClient client) {
        this.client = client;
    }

    public ClientDetailsModel() {
    }

    @Override
    public String getClientId() {
        return client.getClientId();
    }

    @Override
    public Set<String> getResourceIds() {
        return client.getResourceIds()!=null?
                CommonUtils.transformStringToSet(client.getResourceIds(),String.class):null;
    }

    @Override
    public boolean isSecretRequired() {
        return true;
    }

    @Override
    public String getClientSecret() {
        return client.getClientSecret();
    }

    @Override
    public boolean isScoped() {
        return true;
    }

    @Override
    public Set<String> getScope() {

        this.scope = client.getScope()!=null?
                CommonUtils.transformStringToSet(client.getScope(),String.class):null;

        return client.getScope()!=null?
                CommonUtils.transformStringToSet(client.getScope(),String.class):null;
    }

    @Override
    public Set<String> getAuthorizedGrantTypes() {
        return client.getGrantTypes()!=null?
                CommonUtils.transformStringToSet(client.getGrantTypes(),String.class):null;
    }

    @Override
    public Set<String> getRegisteredRedirectUri() {
        return client.getRedirectUri()!=null?
                CommonUtils.transformStringToSet(client.getRedirectUri(),String.class):null;
    }

    @Override
    public Collection<GrantedAuthority> getAuthorities() {
        return (client.getAuthorities()!=null&&client.getAuthorities().trim().length()>0)?
                AuthorityUtils.commaSeparatedStringToAuthorityList(client.getAuthorities()):null;
    }

    @Override
    public Integer getAccessTokenValiditySeconds() {
        return client.getAccessTokenValidity();
    }

    @Override
    public Integer getRefreshTokenValiditySeconds() {
        return client.getRefreshTokenValidity();
    }

    @Override
    public boolean isAutoApprove(String scope) {
       return false;
    }

    @Override
    public Map<String, Object> getAdditionalInformation() {
        return null;
    }
}
