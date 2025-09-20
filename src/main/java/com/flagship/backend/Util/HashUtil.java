package com.flagship.backend.Util;

import java.util.Base64;
import java.security.SecureRandom;

public class HashUtil {
    public static String generateApiKey() {
        SecureRandom secureRandom = new SecureRandom();
        byte[] bytes = new byte[32];
        secureRandom.nextBytes(bytes);
        return Base64.getUrlEncoder().withoutPadding().encodeToString(bytes);
    }
}
