package com.ce.nw.auth.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.common.util.OAuth2Utils;
import org.springframework.security.oauth2.provider.AuthorizationRequest;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.approval.Approval;
import org.springframework.security.oauth2.provider.approval.ApprovalStore;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author yuit
 * @date 2018/11/1 11:44
 *
 */
@Controller
@SessionAttributes("authorizationRequest")
public class BootGrantController {

    @Autowired
    private ClientDetailsService clientDetailsService;

    @Autowired
    private ApprovalStore approvalStore;

//    @RequestMapping("/custom/confirm_access")
    @RequestMapping("/oauth/confirm_access")
    public ModelAndView getAccessConfirmation(Map<String, Object> model, HttpServletRequest request) throws Exception {

        AuthorizationRequest authorizationRequest = (AuthorizationRequest) model.get("authorizationRequest");

//        OAuthAuthzResponse oAuthAuthzResponse = OAuthAuthzResponse.oAuthCodeAndTokenAuthzResponse(request);
//        String code = oAuthAuthzResponse.getCode();

        ModelAndView view = new ModelAndView();
        view.setViewName("base-grant");

        view.addObject("clientId", authorizationRequest.getClientId());
        view.addObject("scopes",authorizationRequest.getScope());

        return view;
    }

    @RequestMapping("/custom/confirm_access_bak")
    public ModelAndView getAccessConfirmation(Map<String, Object> model, Principal principal) throws Exception {
        AuthorizationRequest clientAuth = (AuthorizationRequest) model.remove("authorizationRequest");
        ClientDetails client = clientDetailsService.loadClientByClientId(clientAuth.getClientId());
        model.put("auth_request", clientAuth);
        model.put("clientId", client);
        Map<String, String> scopes = new LinkedHashMap<String, String>();
        for (String scope : clientAuth.getScope()) {
            scopes.put(OAuth2Utils.SCOPE_PREFIX + scope, "false");
        }
        for (Approval approval : approvalStore.getApprovals(principal.getName(), client.getClientId())) {
            if (clientAuth.getScope().contains(approval.getScope())) {
                scopes.put(OAuth2Utils.SCOPE_PREFIX + approval.getScope(),
                        approval.getStatus() == Approval.ApprovalStatus.APPROVED ? "true" : "false");
            }
        }
        model.put("scopes", scopes);
        return new ModelAndView("base-grant", model);
    }

}
