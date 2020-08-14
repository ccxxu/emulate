package com.ce.nw.auth.support.code;

import com.ce.nw.auth.support.BootSecurityProperties;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.ServletWebRequest;

/**
 * @author yuit
 * @date 2018/10/19 17:47
 *
 */
@Component
@ConditionalOnMissingBean(BootDefaultVerificationCodeGenerator.class)
public class BootDefaultVerificationCodeGenerator implements VerificationCodeGenerator {

    @Autowired
    private BootSecurityProperties properties;


    @Override
    public VerificationCode generator(ServletWebRequest request) {
        String smsCode=RandomStringUtils.randomNumeric(properties.getSms().getLength());
        return new VerificationCode(smsCode,properties.getSms().getExpirationTime());
    }


}
