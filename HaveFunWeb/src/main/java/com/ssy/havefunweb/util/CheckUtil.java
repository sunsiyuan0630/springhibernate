/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ssy.havefunweb.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

/**
 *
 * @author jsun
 */
public class CheckUtil {
    private static final String token = "huangxiaofeng";
    public static boolean checkSignature(String signature,String timestamp,String nonce){
        String [] arr = new String[]{token,timestamp,nonce};
        
        Arrays.sort(arr);
        
        StringBuilder content = new StringBuilder();
        for (String arr1 : arr) {
            content.append(arr1);
        }
        
        String temp = getSha1(content.toString());
        return temp.equals(signature);
    }
    public static String getSha1(String decript) {
        try {
                MessageDigest digest = java.security.MessageDigest
                                .getInstance("SHA-1");
                digest.update(decript.getBytes());
                byte messageDigest[] = digest.digest();
                // Create Hex String
                StringBuffer hexString = new StringBuffer();
                // 字节数组转换为 十六进制 数
                for (int i = 0; i < messageDigest.length; i++) {
                        String shaHex = Integer.toHexString(messageDigest[i] & 0xFF);
                        if (shaHex.length() < 2) {
                                hexString.append(0);
                        }
                        hexString.append(shaHex);
                }
                return hexString.toString();

        } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
        }
        return "";
    }
}
