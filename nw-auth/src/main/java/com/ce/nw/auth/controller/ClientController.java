package com.ce.nw.auth.controller;

import com.ce.nw.auth.response.BaseResponse;
import com.ce.nw.auth.response.HttpResponse;
import com.ce.nw.common.model.RbacClient;
import com.ce.nw.common.service.IRbacClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @author yuit
 * @date 2018/10/16  10:32
 *
 **/
@RestController
@RequestMapping("client")
public class ClientController {

    public static final String OAUTH_CLIENT_ID = "client";
    public static final String OAUTH_CLIENT_SECRET = "fbed1d1b4b1449daa4bc49397cbe2350";
    public static final String OAUTH_CLIENT_SCOPE = "user,order";


    public static final String MEMBER_SESSION_KEY = "MEMBER_SESSION_KEY";
    public static final String INVALID_CLIENT_GRANT = "VERIFY_CLIENTID_FAIL";
    public static final String INVALID_CLIENT_SECRET = "VERIFY_CLIENT_SECRET_FAIL";

    public static final String OAUTH_CLIENT_CALLBACK = "http://localhost:9000/client/oauth_callback";
    public static final String OAUTH_CLIENT_AUTHORIZE = "http://localhost:8093/oauth/authorize";
    public static final String OAUTH_CLIENT_ACCESS_TOKEN = "http://localhost:8080/oauth2/access_token";
    public static final String OAUTH_CLIENT_GET_RESOURCE = "http://localhost:8080/oauth2/get_resource";

    @Autowired
    private IRbacClientService rbacClientService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/register")
    public BaseResponse clientRegistered(@RequestBody @Valid RbacClient client) {
        client.setClientSecret(passwordEncoder.encode(client.getClientSecret()));
        this.rbacClientService.save(client);
        return HttpResponse.baseResponse(200);
    }



}
