package website.project.website.utils;

import cn.hutool.core.codec.Base64;
import lombok.extern.slf4j.Slf4j;

import javax.crypto.Cipher;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;

/**
 * 非对称加密工具类
 * @author mayang
 */
@Slf4j
public class RSAUtil {

    // 密钥长度（2048位是最低安全要求）
    private static final int KEY_SIZE = 2048;

    // 最大加密字节数
    private static final int MAX_ENCRYPT_BLOCK = KEY_SIZE / 8 - 11;

    // 最大解密字节数
    private static final int MAX_DECRYPT_BLOCK = KEY_SIZE / 8;

    /**
     * 生成 RSA 密钥对
     */
    public static Map<String, String> generateKeyPair() throws Exception {
        KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance("RSA");
        keyPairGen.initialize(KEY_SIZE, new SecureRandom());
        KeyPair keyPair = keyPairGen.generateKeyPair();

        RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
        RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();

        Map<String, String> keyMap = new HashMap<>();
        keyMap.put("publicKey", Base64.encode(publicKey.getEncoded()));
        keyMap.put("privateKey", Base64.encode(privateKey.getEncoded()));

        return keyMap;
    }

    /**
     * PSA公钥加密
     */
    public static String encryptRSA(String data, String publicKey) throws Exception {
        byte[] keyBytes = Base64.decode(publicKey);
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PublicKey pubKey = keyFactory.generatePublic(keySpec);
        Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding", "BC");
        cipher.init(Cipher.ENCRYPT_MODE, pubKey);

        return Base64.encode(rsaSplitCodec(cipher, data.getBytes(StandardCharsets.UTF_8), MAX_ENCRYPT_BLOCK));
    }

    /**
     * 私钥解密
     */
    public static String decrypt(String data, String privateKey) throws Exception {
        byte[] keyBytes = Base64.decode(privateKey);
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PrivateKey priKey = keyFactory.generatePrivate(keySpec);

        Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding", "BC");
        cipher.init(Cipher.DECRYPT_MODE, priKey);

        byte[] decryptedBytes = rsaSplitCodec(cipher, Base64.decode(data), MAX_DECRYPT_BLOCK);
        return new String(decryptedBytes, StandardCharsets.UTF_8);
    }

    /**
     * 分段加密
     */
    private static byte[] rsaSplitCodec(Cipher cipher, byte[] data, int maxBlock) throws Exception {
        int inputLen = data.length;
        int offSet = 0;
        byte[] resultBytes = {};
        byte[] cache;

        while (inputLen - offSet > 0) {
            if (inputLen - offSet > maxBlock) {
                cache = cipher.doFinal(data, offSet, maxBlock);
                offSet += maxBlock;
            } else {
                cache = cipher.doFinal(data, offSet, inputLen - offSet);
                offSet = inputLen;
            }
            resultBytes = concatArrays(resultBytes, cache);
        }
        return resultBytes;
    }

    /**
     * 组装字符串
     */
    private static byte[] concatArrays(byte[] a, byte[] b) {
        byte[] result = new byte[a.length + b.length];
        System.arraycopy(a, 0, result, 0, a.length);
        System.arraycopy(b, 0, result, a.length, b.length);
        return result;
    }

}
