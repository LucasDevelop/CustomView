package com.cj.customwidget;

import android.graphics.Paint;
import android.util.Base64;

import java.security.AlgorithmParameters;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.Security;
import java.security.spec.InvalidParameterSpecException;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * @author luan
 * @package com.cj.customwidget
 * @date 2020/7/15
 * @des
 */
class Aes {
    public static int measureHeight(Paint paint) {
        Paint.FontMetricsInt fm = paint.getFontMetricsInt();
        int textHeight = ~fm.top - (~fm.top - ~fm.ascent) - (fm.bottom - fm.descent);
        return textHeight;
    }

    private static boolean initialized = false;
    public static final String AES_TYPE = "AES/CBC/PKCS7Padding";
    public static final String AES_KEY = "AES";
    private Aes() {
    }

    /**
     * 加密
     *
     * @param key
     * @param data
     * @param iv
     * @return
     */
    public static String encrypt(String key, String data, String iv) throws Exception{
        byte[] dataByte = data.getBytes();
        byte[] result = encryptOrDecrypt(key, dataByte, iv, Cipher.ENCRYPT_MODE);
        return new String(Base64.encode(result,Base64.DEFAULT));
    }

    /**
     * AES解密
     *
     * @param data
     * @param key
     * @param iv
     * @return
     */
    public static String decrypt(String key, String data, String iv) throws Exception {
        byte[] dataByte = Base64.decode(data,Base64.DEFAULT);
        return new String(encryptOrDecrypt(key, dataByte, iv, Cipher.DECRYPT_MODE), "UTF-8");
    }

    /**
     * 加密或者解密
     *
     * @param key
     * @param iv
     * @param data
     * @param cipherMode
     * @return
     * @throws Exception
     */
    public static byte[] encryptOrDecrypt(String key, byte[] data, String iv, int cipherMode) throws Exception{
        initialize();
        byte[] keyByte = Base64.decode(key,Base64.DEFAULT);
        byte[] ivByte = Base64.decode(iv,Base64.DEFAULT);
        Cipher cipher = Cipher.getInstance(AES_TYPE);
        Key sKeySpec = new SecretKeySpec(keyByte, AES_KEY);
        // 初始化
        cipher.init(cipherMode, sKeySpec, generateIV(ivByte));
        return cipher.doFinal(data);
    }

    /**
     * 生成iv
     *
     * @param iv
     * @return
     * @throws NoSuchAlgorithmException
     * @throws InvalidParameterSpecException
     */
    public static AlgorithmParameters generateIV(byte[] iv) throws NoSuchAlgorithmException, InvalidParameterSpecException {
        AlgorithmParameters params = AlgorithmParameters.getInstance(AES_KEY);
        params.init(new IvParameterSpec(iv));
        return params;
    }

    public static void initialize() {
        if (initialized) {
            return;
        }
        initialized = true;
    }
}
