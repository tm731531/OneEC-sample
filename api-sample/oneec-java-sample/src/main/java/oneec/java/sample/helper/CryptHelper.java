package oneec.java.sample.helper;



import java.io.IOException;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class CryptHelper {

    public static SecretKeySpec getSecretKeySpec(String key) throws IOException {
        SecretKeySpec keySpec = new SecretKeySpec(key.getBytes("UTF-8"), "AES");

        return keySpec;
    }

    public static SecretKeySpec loadSecretKeySpecFromBase64(String key) throws IOException {
        byte[] decodedKey = Base64.getDecoder().decode(key);

        SecretKeySpec keySpec = new SecretKeySpec(decodedKey, 0, decodedKey.length, "AES");

        return keySpec;
    }

    public static String encrypt(SecretKeySpec keySpec, String iv, String cipherMode, String data) throws Exception{
        if(data == null){
            return data;
        }

        String encrypted = null;
        try{
            Cipher cipher = Cipher.getInstance(cipherMode);

            GCMParameterSpec ivParamsSpec = new GCMParameterSpec(128, iv.getBytes("UTF-8"));

            cipher.init(Cipher.ENCRYPT_MODE, keySpec, ivParamsSpec);

            byte[] encrypt = cipher.doFinal(data.getBytes("UTF-8"));

            encrypted = Base64.getEncoder().encodeToString(encrypt);

        } catch(Exception e){
            throw e;
        }
        return encrypted;
    }

    public static String decrypt(SecretKeySpec keySpec, String iv, String cipherMode, String data) throws Exception{
        if(data == null){
            return data;
        }

        String decrypted = null;
        try{
            Cipher cipher = Cipher.getInstance(cipherMode);

            GCMParameterSpec ivParamsSpec = new GCMParameterSpec(128, iv.getBytes("UTF-8"));

            cipher.init(Cipher.DECRYPT_MODE, keySpec, ivParamsSpec);

            byte[] b = Base64.getDecoder().decode(data);

            byte[] decrypt = cipher.doFinal(b);

            decrypted = new String(decrypt, "UTF-8");

        } catch(Exception e) {
            throw e;
        }

        return decrypted;
    }
    public static String decrypt(SecretKeySpec keySpec, String cipherMode, String data) throws Exception{
        if(data == null){
            return data;
        }

        String decrypted = null;
        try{
            Cipher cipher = Cipher.getInstance(cipherMode);

            cipher.init(Cipher.DECRYPT_MODE, keySpec);

            byte[] b = Base64.getDecoder().decode(data);

            byte[] decrypt = cipher.doFinal(b);

            decrypted = new String(decrypt, "UTF-8");

        } catch(Exception e) {
            throw e;
        }

        return decrypted;
    }
}
