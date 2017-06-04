package com.i2nexted.mvpframe.util;



import android.util.Base64;

import java.io.InputStream;
import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.cert.Certificate;
import java.security.cert.X509Certificate;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.PBEParameterSpec;

/**
 * Created by Alex on 2015/12/3.
 */
public class EncryptUtil {

    public static String generateSecretKey(){
        try {
            KeyGenerator keyGenerator = KeyGenerator.getInstance("DES");
            SecretKey secretKey = keyGenerator.generateKey();
            return Base64.encodeToString(secretKey.getEncoded(),Base64.NO_WRAP);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null ;
    }

    public static KeyPair getKeyPair(InputStream inputStream,String password,String alias){
        try {
            //默认是BKS
            String type = KeyStore.getDefaultType();
            //Android不支持JKS格式，只支持BKS和PKCS12
            KeyStore keyStore = KeyStore.getInstance(type);
            keyStore.load(inputStream, password.toCharArray());
            if(keyStore.containsAlias(alias)){
                //获取的是公钥
                X509Certificate x509Certificate = (X509Certificate) keyStore.getCertificate(alias);
                PublicKey publicKey = x509Certificate.getPublicKey() ;
                //获取私钥需要密码
                PrivateKey privateKey = (PrivateKey) keyStore.getKey(alias, password.toCharArray());
                return new KeyPair(publicKey,privateKey);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null ;
    }

    public static Certificate getCertificate(InputStream inputStream,String password,String alias){
        try {
            //默认是BKS
            String type = KeyStore.getDefaultType();
            //Android不支持JKS格式，只支持BKS和PKCS12
            KeyStore keyStore = KeyStore.getInstance(type);
            keyStore.load(inputStream, password.toCharArray());
            if(keyStore.containsAlias(alias)){
                //获取的是公钥
                X509Certificate x509Certificate = (X509Certificate) keyStore.getCertificate(alias);
                return x509Certificate;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null ;
    }

    public static String encryptByDES(String content,String encodedKey){
        SecretKey secretKey = getSecretKeyDES(encodedKey);
        return encrypt(content,secretKey);
    }

    public static String[] encryptByDES(String[] array,String encodedKey){
        SecretKey secretKey = getSecretKeyDES(encodedKey);
        return encrypt(array, secretKey);
    }

    public static String decryptByDES(String content,String encodedKey) throws Exception {
        SecretKey secretKey = getSecretKeyDES(encodedKey);
        return decrypt(content, secretKey);
    }

    public static String[] decryptByDES(String[] array,String encodedKey) throws Exception {
        SecretKey secretKey = getSecretKeyDES(encodedKey);
        return decrypt(array, secretKey);
    }

    public static String encryptByRSAPrivate(String content,String encodedKey){
        PrivateKey privateKey = getPrivateKey(encodedKey);
        return encrypt(content, privateKey) ;
    }

    public static String encryptByRSAPublic(String content,String encodedKey){
        PublicKey publicKey = getPublicKey(encodedKey);
        return encrypt(content, publicKey) ;
    }

    public static String decryptByRSAPrivate(String content,String encodedKey) throws Exception {
        PrivateKey privateKey = getPrivateKey(encodedKey);
        return decrypt(content, privateKey) ;
    }

    public static String decryptByRSAPublic(String content,String encodedKey) throws Exception {
        PublicKey publicKey = getPublicKey(encodedKey);
        return decrypt(content, publicKey) ;
    }

    public static PrivateKey getPrivateKey(String encodedKey){
        byte[] keyByte = Base64.decode(encodedKey, Base64.NO_WRAP);
        PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(keyByte);
        try {
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            return keyFactory.generatePrivate(pkcs8EncodedKeySpec);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null ;
    }

    public static PublicKey getPublicKey(String encodedKey){
        byte[] keyByte = Base64.decode(encodedKey, Base64.NO_WRAP);
        X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(keyByte);
        try {
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            return keyFactory.generatePublic(x509EncodedKeySpec);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null ;
    }

    public static SecretKey getSecretKeyDES(String encodedKey){
        byte[] keyByte = Base64.decode(encodedKey, Base64.NO_WRAP);
        SecretKey secretKey = null;
        try {
            DESKeySpec desKeySpec = new DESKeySpec(keyByte);
            SecretKeyFactory secretKeyFactory = SecretKeyFactory.getInstance("DES");
            secretKey = secretKeyFactory.generateSecret(desKeySpec);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return secretKey ;
    }

    public static SecretKey getSecretKeyPBE(String password) throws Exception {
        PBEKeySpec pbeKeySpec = new PBEKeySpec(password.toCharArray());
        SecretKeyFactory secretKeyFactory = SecretKeyFactory.getInstance("PBEWITHMD5ANDDES");
        return secretKeyFactory.generateSecret(pbeKeySpec);
    }

    public static String encrypt(String content,Key key){
        try {
            Cipher cipher = Cipher.getInstance(key.getAlgorithm());
            cipher.init(Cipher.ENCRYPT_MODE, key);
            byte[] encryptByte = cipher.doFinal(content.getBytes());
            return Base64.encodeToString(encryptByte, Base64.NO_WRAP);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null ;
    }

    public static String encryptByPBE(String content,Key key,byte[] salt,int iterationCount){
        try {
            Cipher cipher = Cipher.getInstance(key.getAlgorithm());
            PBEParameterSpec pbeParameterSpec = new PBEParameterSpec(salt,iterationCount);
            cipher.init(Cipher.ENCRYPT_MODE, key,pbeParameterSpec);
            byte[] encryptByte = cipher.doFinal(content.getBytes());
            return Base64.encodeToString(encryptByte, Base64.NO_WRAP);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null ;
    }

    public static String[] encrypt(String[] array,Key key){
        String[] encodedArray = new String[array.length];
        try {
            Cipher cipher = Cipher.getInstance(key.getAlgorithm());
            cipher.init(Cipher.ENCRYPT_MODE, key);
            for (int i=0;i<array.length;i++){
                byte[] encryptByte = cipher.doFinal(array[i].getBytes());
                encodedArray[i] = Base64.encodeToString(encryptByte, Base64.NO_WRAP);
            }
            return encodedArray ;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return encodedArray ;
    }

    public static String decrypt(String content,Key key) throws Exception {
        byte[] encryptByte = Base64.decode(content, Base64.NO_WRAP);
        Cipher cipher = Cipher.getInstance(key.getAlgorithm());
        cipher.init(Cipher.DECRYPT_MODE, key);
        byte[] decryptByte = cipher.doFinal(encryptByte);
        return new String(decryptByte);
    }

    public static String decryptByPBE(String content,Key key,byte[] salt,int iterationCount) throws Exception {
	    byte[] encryptByte = Base64.decode(content, Base64.NO_WRAP);
	    Cipher cipher = Cipher.getInstance(key.getAlgorithm());
	    PBEParameterSpec pbeParameterSpec = new PBEParameterSpec(salt, iterationCount);
	    cipher.init(Cipher.DECRYPT_MODE, key, pbeParameterSpec);
	    byte[] decryptByte = cipher.doFinal(encryptByte);
	    return new String(decryptByte);
    }

    public static String[] decrypt(String[] array,Key key) throws Exception {
	    String[] encryptArray = new String[array.length];
	    Cipher cipher = Cipher.getInstance(key.getAlgorithm());
	    cipher.init(Cipher.DECRYPT_MODE, key);
	    for (int i = 0; i < array.length; i++) {
		    byte[] encryptByte = Base64.decode(array[i], Base64.NO_WRAP);
		    byte[] decryptByte = cipher.doFinal(encryptByte);
		    encryptArray[i] = new String(decryptByte);
	    }
	    return encryptArray;
    }

}
