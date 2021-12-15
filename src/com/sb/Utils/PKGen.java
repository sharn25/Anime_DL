package com.sb.Utils;

import java.security.SecureRandom;
import java.util.Base64;

/**
 * 
 * @author sharn25
 * @since 31-12-20
 */
public class PKGen {
	private final static String TAG = "PKGen";
	public static String generateCodeVerifier() {
        byte[] bArr = new byte[32];
        new SecureRandom().nextBytes(bArr);
        return getBase64String(bArr);
    }
	
	public static String getBase64String(byte[] bArr) {
        return Base64.getEncoder().encodeToString(bArr);
    }
}
