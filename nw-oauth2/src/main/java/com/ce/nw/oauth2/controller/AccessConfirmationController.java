package com.ce.nw.oauth2.controller;

import org.springframework.security.oauth2.provider.AuthorizationRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;
import java.util.Map;

/**
 * 重新定义授权页面
 * @author ccx
 * 说明: 如果让生效, 需要添加@SessionAttributes("authorizationRequest")注解
 */
@Controller
@SessionAttributes("authorizationRequest")
public class AccessConfirmationController {


	@RequestMapping("/oauth/confirm_access")
	public ModelAndView getAccessConfirmation(Map<String, Object> model, Principal principal) throws Exception {
		AuthorizationRequest authorizationRequest = (AuthorizationRequest) model.remove("authorizationRequest");

		model.put("clientId", authorizationRequest.getClientId());
		model.put("scopes",authorizationRequest.getScope());

		return new ModelAndView("access_confirmation", model);
	}

	@RequestMapping("/oauth/error")
	public String handleError(Map<String, Object> model) throws Exception {
		// We can add more stuff to the model here for JSP rendering. If the client was a machine then
		// the JSON will already have been rendered.
		model.put("message", "There was a problem with the OAuth2 protocol");
		return "oauth_error";
	}

}
