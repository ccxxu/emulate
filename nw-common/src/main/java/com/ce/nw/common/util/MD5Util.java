package com.ce.nw.common.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.apache.commons.codec.binary.Base64;

public class MD5Util {

	public static final String KEY_SHA = "SHA";  
	public static final String KEY_MD5 = "MD5";  
	public static final String KEY_MAC = "HmacMD5";  
	
	public static String md5(String plainText) {
		String re_md5 = new String();
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(plainText.getBytes());
            byte b[] = md.digest();
 
            int i;
 
            StringBuffer buf = new StringBuffer("");
            for (int offset = 0; offset < b.length; offset++) {
                i = b[offset];
                if (i < 0)
                    i += 256;
                if (i < 16)
                    buf.append("0");
                buf.append(Integer.toHexString(i));
            }
 
            re_md5 = buf.toString();
 
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return re_md5;
	}
	
	  
	  
	// sun不推荐使用它们自己的base64,用apache的挺好  
	/** 
	* BASE64解密 
	*/  
	public static byte[] decryptBASE64(byte[] dest) {  
		if (dest == null) {  
			return null;  
		}  
		return Base64.decodeBase64(dest);  
	}  
	  
	/** 
	* BASE64加密 
	*/  
	public static byte[] encryptBASE64(byte[] origin) {  
		if (origin == null) {  
			return null;  
		}  
		return Base64.encodeBase64(origin);  
	} 
	
	public static void main(String[] args) {
		System.out.println(MD5Util.md5("123456"));
		String msg = "123456";
		byte[] origin = msg.getBytes();
		System.out.println("加密后:"+md5("a00000000000"));
		System.out.println("加密后:"+md5("111111"));
		System.out.println("加密后:"+md5("0"));
		System.out.println("加密后:"+md5("a11111111111"));
	}
	
}
