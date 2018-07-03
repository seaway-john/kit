package com.seaway.kit;

import org.junit.Test;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

public class Dyl {
    @Test
    public void test() {
        try {
            String secret = "secret";
            byte[] secretBytes = secret.getBytes("UTF-8");

            Mac hmacSHA256 = Mac.getInstance("HmacSHA256");
            hmacSHA256.init(new SecretKeySpec(secretBytes, 0, secretBytes.length, "HmacSHA256"));

            String signed = bytesToHexString(hmacSHA256.doFinal(secret.getBytes("UTF-8")));
            System.out.println("signed: " + signed);
        } catch (Exception e) {
            System.err.println("Exception in main, reason " + e.getMessage());
        }
    }

    private static String bytesToHexString(byte[] bytes) {
        if (bytes == null || bytes.length <= 0) {
            return null;
        }

        StringBuilder sb = new StringBuilder("");
        for (byte b : bytes) {
            int v = b & 0xFF;
            String hv = Integer.toHexString(v);
            if (hv.length() < 2) {
                sb.append(0);
            }
            sb.append(hv);
        }

        return sb.toString();
    }
}
