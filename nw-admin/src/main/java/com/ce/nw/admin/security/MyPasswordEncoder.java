package com.ce.nw.admin.security;

import com.ce.nw.common.util.MD5Util;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Created by ccxxu on 2019-09-17.
 */
public class MyPasswordEncoder implements PasswordEncoder {

    @Override
    public String encode(CharSequence rawPassword) {
        //return charSequence.toString();
        return MD5Util.md5(rawPassword.toString());
    }

    @Override
    public boolean matches(CharSequence rawPassword, String encodePassword) {
        //return s.equals(charSequence.toString());
        return encodePassword.equals(MD5Util.md5(rawPassword.toString()));
    }
}
