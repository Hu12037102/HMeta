package com.sbnh.comm.digest;

import android.util.Base64;

import androidx.annotation.NonNull;

import java.nio.charset.StandardCharsets;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.Cipher;

/**
 * 作者: 胡庆岭
 * 创建时间: 2021/12/6 15:17
 * 更新时间: 2021/12/6 15:17
 * 描述:
 */
public class RSACompat {
    // public static final String PRIVATE_KEY = UserInfoHelp.get().getPrivateKey();
   // public static final String PUBLIC_KEY="MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCclA50pT3KI4baSQ8ZntjhCz33OvJHAJWRZT6Dm6BqDcB9QDmd8nPVEAa2cqBYx8GnThDodFTNOdYlqPWqXpA4GCvN54XhQXInzYnHwZh0HXTZKTSMKgbYTZGAV8zb2sRYxWGkZVxG9DmEF9kDY+rJU2QclRfed3e+sgr6M8rwZQIDAQAB";
    /****************************Android************************************/
    private static PrivateKey getPrivateKey(String privateKey) {
        try {
            byte[] bytes = Base64.decode(privateKey, Base64.NO_WRAP);
            PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(bytes);
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            return keyFactory.generatePrivate(keySpec);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String encryptPrivateKey(@NonNull String privateKey, String content) {
        String result = "";
        try {
            PrivateKey key = getPrivateKey(privateKey);
            Cipher cipher = Cipher.getInstance("RSA/None/PKCS1Padding");
            cipher.init(Cipher.ENCRYPT_MODE, key);
            byte[] bytes = cipher.doFinal(content.getBytes(StandardCharsets.UTF_8));
            result = Base64.encodeToString(bytes, Base64.NO_WRAP);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public static PublicKey getPublicKey(@NonNull String publicKey) {
        try {
            byte[] bytes = Base64.decode(publicKey, Base64.NO_WRAP);
            X509EncodedKeySpec keySpec = new X509EncodedKeySpec(bytes);
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            return keyFactory.generatePublic(keySpec);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 公钥解密
     *
     * @param publicKey
     * @return
     */
    public static String decodePublicKey(@NonNull String publicKey, @NonNull String content) {
        try {
            PublicKey key = getPublicKey(publicKey);
            Cipher cipher = Cipher.getInstance("RSA/None/PKCS1Padding");
            cipher.init(Cipher.DECRYPT_MODE, key);
            byte[] bytes = Base64.decode(content, Base64.NO_WRAP);
            bytes = cipher.doFinal(bytes);
            return new String(bytes, StandardCharsets.UTF_8);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }


   /* private static PrivateKey getPrivateKey() {
        try {
            byte[] keyBytes;
            if (Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                Base64.Decoder decoder = Base64.getDecoder();
                keyBytes = decoder.decode(PRIVATE_KEY.getBytes(StandardCharsets.UTF_8));
            } else {
                keyBytes = android.util.Base64.decode(PRIVATE_KEY, android.util.Base64.DEFAULT);
            }
            LogUtils.w("getPrivateKey--",PRIVATE_KEY+"-=-");
          //  keyBytes = android.util.Base64.decode(PRIVATE_KEY, android.util.Base64.DEFAULT);
            PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(keyBytes);
            KeyFactory factory = KeyFactory.getInstance("RSA");
            return factory.generatePrivate(keySpec);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String encryptPrivateKey(String content) {
        String result = "";
        try {
            PrivateKey privateKey = getPrivateKey();
            Cipher cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.ENCRYPT_MODE, privateKey);
            byte[] resultByte = cipher.doFinal(content.getBytes(StandardCharsets.UTF_8));
            if (Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                Base64.Encoder encoder = Base64.getEncoder();
                result = encoder.encodeToString(resultByte);
            } else {
                result = android.util.Base64.encodeToString(resultByte, android.util.Base64.DEFAULT);
            }
            // result = android.util.Base64.encodeToString(resultByte, android.util.Base64.DEFAULT);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }*/

   /* public static String encryptPrivateKey(String privateKey, String str) {
        String result = "";
        try {
            byte[] keyBytes = Base64.decode(privateKey, Base64.NO_WRAP);
            PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(keyBytes);
            RSAPrivateKey priKey = (RSAPrivateKey) KeyFactory.getInstance("RSA")
                    .generatePrivate(pkcs8EncodedKeySpec);
            Cipher cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.ENCRYPT_MODE, priKey);
            byte[] resultByte = cipher.doFinal(str.getBytes(StandardCharsets.UTF_8));
            result = Base64.encodeToString(resultByte,Base64.NO_WRAP);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }*/
}
