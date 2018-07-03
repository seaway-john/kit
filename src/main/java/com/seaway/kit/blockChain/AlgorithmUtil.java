package com.seaway.kit.blockChain;

import java.security.*;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

public class AlgorithmUtil {

    public static String applySha256(String plainText) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] byteHash = digest.digest(plainText.getBytes("UTF-8"));

            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < byteHash.length; i++) {
                String hex = Integer.toHexString(0xff & byteHash[i]);
                if (hex.length() == 1) {
                    sb.append("0");
                }
                sb.append(hex);
            }

            return sb.toString();
        } catch (Exception e) {
        }

        return null;
    }

    public static String getFromKey(Key key) {
        return Base64.getEncoder().encodeToString(key.getEncoded());
    }

    public static byte[] applyECDSASignature(PrivateKey privateKey, String plainText) {
        try {
            Signature ecdsa = Signature.getInstance("ECDSA", "BC");
            ecdsa.initSign(privateKey);
            ecdsa.update(plainText.getBytes());

            return ecdsa.sign();
        } catch (Exception e) {
        }

        return null;
    }

    public static boolean verifyECDSASignature(PublicKey publicKey, String plainText, byte[] signature) {
        try {
            Signature ecdsa = Signature.getInstance("ECDSA", "BC");
            ecdsa.initVerify(publicKey);
            ecdsa.update(plainText.getBytes());

            return ecdsa.verify(signature);
        } catch (Exception e) {
        }

        return false;
    }

    public static String getMerkleRoot(List<Transaction> transactions) {
        List<String> previousTreeLayer = new ArrayList<>();
        for (Transaction transaction : transactions) {
            previousTreeLayer.add(transaction.getId());
        }

        List<String> treeLayer = new ArrayList<>();
        int count = previousTreeLayer.size();
        while (count > 1) {
            treeLayer = new ArrayList<>();
            for (int i = 1; i < previousTreeLayer.size(); i++) {
                String plainText = previousTreeLayer.get(i - 1) + previousTreeLayer.get(i);
                treeLayer.add(applySha256(plainText));
            }

            count = previousTreeLayer.size();
            previousTreeLayer = treeLayer;
        }

        return treeLayer.size() == 1 ? treeLayer.get(0) : "";
    }

    public static String getDificultyString(int difficulty) {
        return new String(new char[difficulty]).replace('\0', '0');
    }

}
