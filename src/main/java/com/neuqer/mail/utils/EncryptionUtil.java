package com.neuqer.mail.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by Hotown on 17/5/16.
 */
public class EncryptionUtil {
    /**
     * 加密
     * @param source 需要加密的字符串
     * @param hashType 加密类型（MD5 或 SHA）
     * @return
     */
    public static String getHash(String source, String hashType) {
        //用来将字节转换成 16 进制表示的字符
        char hexDigits[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};

        try {
            MessageDigest md = MessageDigest.getInstance(hashType);
            //通过 update 方法处理数据，使指定的byte数组更新摘要
            md.update(source.getBytes());
            //获得密文完成哈希计算，产生 128 位的长整数
            byte[] encryptStr = md.digest();
            //每个字节用16进制表示，使用两个字符
            char[] str = new char[16 * 2];
            //表示转换结果中对应的字符位置
            int k = 0;
            for (int i = 0; i < 16; i++) {
                byte byte0 = encryptStr[i];
                //去字节中高 4 位的数字转换
                str[k++] = hexDigits[byte0 >>> 4 & 0xf];
                // 去字节中低 4 位的数字转换
                str[k++] = hexDigits[byte0 & 0xf];
            }
            return new String(str);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }
}
