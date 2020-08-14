package com.ce.nw.oauth2.service;

import com.ce.nw.common.model.RbacClient;
import com.ce.nw.common.service.IRbacClientService;
import com.ce.nw.oauth2.domain.ClientDetailsModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.ClientRegistrationException;
import org.springframework.stereotype.Component;

/**
 *
 */
@Component
public final class MyClientDetailsService implements ClientDetailsService {

    @Autowired
    private IRbacClientService rbacClientService;

    @Override
    public ClientDetails loadClientByClientId(String clientId) throws ClientRegistrationException {

        RbacClient client = this.rbacClientService.findClientByClientId(clientId);

        if(client==null){
            throw new ClientRegistrationException("客户端不存在");
        }
        ClientDetailsModel details=new ClientDetailsModel(client);

        return details;
    }

}
