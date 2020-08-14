package com.ce.nw.oauth2.utils;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * @author ccx
 * @description
 * @date
 */
public class PwdEncoderUtil {

    public static void main(String[] args) {
        BCryptPasswordEncoder bcryptPasswordEncoder = new BCryptPasswordEncoder();
        String hashPass = bcryptPasswordEncoder.encode("2");
        System.out.println(hashPass);
        System.out.println(bcryptPasswordEncoder.matches("2",hashPass));//true
        System.out.println(bcryptPasswordEncoder.matches("2","$2a$10$eFMK1ATC3dgQ47p2aoF/h.2VB7uICizEyrqnv4vFjXDpmJ01tRavO"));//true
        System.out.println(bcryptPasswordEncoder.matches("2","$2a$10$JNWcYPCf0IHL4g/bzLtwluHOQz2JTdKDr4FwP6Lgf.jmX69pKvxlm"));//true
        System.out.println(bcryptPasswordEncoder.matches("2","$2a$10$9etIPtquQ3f..ACQkDHAVuBfjBoDXXWHHCOBl/RaJADxuXdSQB6I2"));//true
    }

}
