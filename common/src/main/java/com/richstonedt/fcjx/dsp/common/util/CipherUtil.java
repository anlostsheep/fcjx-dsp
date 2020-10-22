/*
 * 广州丰石科技有限公司拥有本软件版权2020并保留所有权利。
 * Copyright 2020, Guangzhou Rich Stone Data Technologies Company Limited,
 * All rights reserved.
 */

package com.richstonedt.fcjx.dsp.common.util;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

/**
 * <b><code>CipherUtil</code></b>
 * <p/>
 * Description
 * <p/>
 * <b>Creation Time:</b> 2020/4/3 11:53 下午.
 *
 * @author LIANG QING LONG
 * @since dsp-blackwhitelist
 */
public class CipherUtil {

    public static String aesEncrypt(String password, String strKey) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
        byte[] keyBytes = Arrays.copyOf(strKey.getBytes(StandardCharsets.US_ASCII), 16);
        SecretKey key = new SecretKeySpec(keyBytes, "AES");
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.ENCRYPT_MODE, key);
        byte[] cleartext = password.getBytes(StandardCharsets.UTF_8);
        byte[] cipherBytes = cipher.doFinal(cleartext);
        return new String(Hex.encodeHex(cipherBytes)).toUpperCase();
    }

    public static String aesDecrypt(String passwordHex, String strKey) throws NoSuchPaddingException, NoSuchAlgorithmException, DecoderException, BadPaddingException, IllegalBlockSizeException, InvalidKeyException {
        byte[] keyBytes = Arrays.copyOf(strKey.getBytes(StandardCharsets.US_ASCII), 16);
        SecretKey key = new SecretKeySpec(keyBytes, "AES");
        Cipher decipher = Cipher.getInstance("AES");
        decipher.init(Cipher.DECRYPT_MODE, key);
        char[] cleartext = passwordHex.toCharArray();
        byte[] decodeHex = Hex.decodeHex(cleartext);
        byte[] cipherBytes = decipher.doFinal(decodeHex);
        return new String(cipherBytes);
    }

    public static void main(String[] args) throws IllegalBlockSizeException, InvalidKeyException, BadPaddingException, NoSuchAlgorithmException, NoSuchPaddingException, DecoderException {
        String key = "smartpush-cmgddr";
        String s = aesEncrypt("13430235818", key);
        String dc = aesDecrypt("1F0993F73DF090774053F90FC6B66FFB", key);
        System.out.println(s);
        System.out.println(dc);
    }
}
