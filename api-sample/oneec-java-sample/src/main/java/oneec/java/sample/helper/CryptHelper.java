package oneec.java.sample.helper;


import java.io.IOException;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class CryptHelper {
    private final String _aesKey;
    private final String _aesIv;
    private final String _cipherMode;

    public CryptHelper(String aesKey, String aesIv, String cipherMode) {
        this._aesKey = aesKey;
        this._aesIv = aesIv;
        this._cipherMode = cipherMode;
    }

    public static SecretKeySpec getSecretKeySpec(String key) throws IOException {
        SecretKeySpec keySpec = new SecretKeySpec(key.getBytes("UTF-8"), "AES");

        return keySpec;
    }

    public static SecretKeySpec loadSecretKeySpecFromBase64(String key) throws IOException {
        byte[] decodedKey = Base64.getDecoder().decode(key);

        SecretKeySpec keySpec = new SecretKeySpec(decodedKey, 0, decodedKey.length, "AES");

        return keySpec;
    }

    public static String encrypt(SecretKeySpec keySpec, String iv, String cipherMode, String data) throws Exception {
        if (data == null) {
            return data;
        }

        String encrypted = null;
        try {
            Cipher cipher = Cipher.getInstance(cipherMode);

            GCMParameterSpec ivParamsSpec = new GCMParameterSpec(128, iv.getBytes("UTF-8"));

            cipher.init(Cipher.ENCRYPT_MODE, keySpec, ivParamsSpec);

            byte[] encrypt = cipher.doFinal(data.getBytes("UTF-8"));

            encrypted = Base64.getEncoder().encodeToString(encrypt);

        } catch (Exception e) {
            throw e;
        }
        return encrypted;
    }

    public static String decrypt(SecretKeySpec keySpec, String iv, String cipherMode, String data) throws Exception {
        if (data == null) {
            return data;
        }

        String decrypted = null;
        try {
            Cipher cipher = Cipher.getInstance(cipherMode);

            GCMParameterSpec ivParamsSpec = new GCMParameterSpec(128, iv.getBytes("UTF-8"));

            cipher.init(Cipher.DECRYPT_MODE, keySpec, ivParamsSpec);

            byte[] b = Base64.getDecoder().decode(data);

            byte[] decrypt = cipher.doFinal(b);

            decrypted = new String(decrypt, "UTF-8");

        } catch (Exception e) {
            throw e;
        }

        return decrypted;
    }

    public static String decrypt(SecretKeySpec keySpec, String cipherMode, String data) throws Exception {
        if (data == null) {
            return data;
        }

        String decrypted = null;
        try {
            Cipher cipher = Cipher.getInstance(cipherMode);

            cipher.init(Cipher.DECRYPT_MODE, keySpec);

            byte[] b = Base64.getDecoder().decode(data);

            byte[] decrypt = cipher.doFinal(b);

            decrypted = new String(decrypt, "UTF-8");

        } catch (Exception e) {
            throw e;
        }

        return decrypted;
    }

    public String encryptFromBase64(String plainText) {
        if(plainText == null) {
            return "";
        }

        try {
            byte[] decodedKey = Base64.getDecoder().decode(_aesKey);
            SecretKeySpec keySpec = new SecretKeySpec(decodedKey, 0, decodedKey.length, "AES");

            Cipher cipher = Cipher.getInstance(_cipherMode);

            GCMParameterSpec ivParamsSpec = new GCMParameterSpec(128, _aesIv.getBytes("UTF-8"));

            cipher.init(Cipher.ENCRYPT_MODE, keySpec, ivParamsSpec);

            byte[] encrypt = cipher.doFinal(plainText.getBytes("UTF-8"));
            return Base64.getEncoder().encodeToString(encrypt);
        } catch (Exception e) {
            System.out.println(e.getMessage() + ", " + e);
            return "";
        }
    }

    public String decryptFromBase64(String cipherText) {
        if(cipherText == null) {
            return "";
        }

        try {
            byte[] decodedKey = Base64.getDecoder().decode(_aesKey);
            SecretKeySpec keySpec = new SecretKeySpec(decodedKey, 0, decodedKey.length, "AES");

            GCMParameterSpec ivParamsSpec = new GCMParameterSpec(128, _aesIv.getBytes("UTF-8"));

            Cipher cipher = Cipher.getInstance(_cipherMode);
            cipher.init(Cipher.DECRYPT_MODE, keySpec, ivParamsSpec);

            byte[] b = Base64.getDecoder().decode(cipherText);
            byte[] decrypt = cipher.doFinal(b);
            return new String(decrypt, "UTF-8");
        } catch (Exception e) {
            System.out.println(e.getMessage() + ", " + e);
            return "";
        }
    }
}
