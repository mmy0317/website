package website.project.website.utils;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import cn.hutool.core.codec.Base64;
import io.micrometer.common.util.StringUtils;
import org.apache.logging.log4j.util.Strings;

import java.security.KeyPairGenerator;
import java.security.SecureRandom;

/**
 * 对称加密工具类
 * @author mayang
 */
public class AESUtil {

    // 密钥长度（2048位是最低安全要求）
    private static final int KEY_SIZE = 128;

    /**
     * 通过字符串生成AES密钥
     */
    public static String getSecretKey() throws Exception {
        KeyGenerator keyGen = KeyGenerator.getInstance("AES");
        keyGen.init(KEY_SIZE);
        return Base64.encode(keyGen.generateKey().getEncoded());
    }

    /**
     * AES对称加密
     */
    public static String encrypt(String data, String key) throws Exception {
        SecretKeySpec keySpec = new SecretKeySpec(key.getBytes(), "AES");
        //加密类型为AES/CBC/PKCS5Padding
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, keySpec);
        //数据加密
        byte[] encrypted = cipher.doFinal(data.getBytes());
        return Base64.encode(encrypted);
    }

    /**
     * AES对称解密
     */
    public static String decrypt(String data, String key) throws Exception {
        if (StringUtils.isBlank(data) || StringUtils.isBlank(key)){
            return Strings.EMPTY;
        }
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        SecretKeySpec keySpec = new SecretKeySpec(key.getBytes(), "AES");
        cipher.init(Cipher.DECRYPT_MODE, keySpec);
        byte[] decoded = Base64.decode(data);
        byte[] decrypted = cipher.doFinal(decoded);
        return new String(decrypted);
    }

}
