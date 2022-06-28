package com.sbnh.comm.digest;



import com.sbnh.comm.utils.LogUtils;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;


/**
 * 作者: 胡庆岭
 * 创建时间: 2021/12/6 16:17
 * 更新时间: 2021/12/6 16:17
 * 描述:
 */
public class MD5Compat {

    public static String stringToString(String text) {
        String result = "";
       /* try {
            MessageDigest digest = MessageDigest.getInstance("md5");
            byte[] bytes = digest.digest(text.getBytes(StandardCharsets.UTF_8));
            //     return new String(bytes);
            //16是表示转换为16进制数
            result = new BigInteger(1, bytes).toString(16);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }*/

        try {
            MessageDigest digest = MessageDigest.getInstance("MD5");
            digest.update(text.getBytes(StandardCharsets.UTF_8));
            return Hex.encodeHexString(digest.digest());
        } catch (Exception e) {
            e.printStackTrace();
        }
        LogUtils.w("MD5", result);
        return result;
    }
}
