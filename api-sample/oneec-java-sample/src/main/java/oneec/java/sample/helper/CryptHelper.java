package oneec.java.sample.helper;

import javax.crypto.Cipher;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

public class CryptHelper {
    private static final String _cipherMode = "AES/GCM/NoPadding";

    /**
     * AES GCM Encrypt
     *
     * @param plainText Data that needs to be encrypted
     * @return Encrypted Base64 data
     */
    public static String aesGcmEncryptToBase64(String aesKey, String aesIv, String plainText) throws Exception {
        if (plainText == null) {
            return "";
        } else {
            byte[] decodedKey = Base64.getDecoder().decode(aesKey);
            SecretKeySpec keySpec = new SecretKeySpec(decodedKey, 0, decodedKey.length, "AES");

            Cipher cipher = Cipher.getInstance(_cipherMode);

            GCMParameterSpec ivParamsSpec = new GCMParameterSpec(128, aesIv.getBytes("UTF-8"));

            cipher.init(Cipher.ENCRYPT_MODE, keySpec, ivParamsSpec);

            byte[] encrypt = cipher.doFinal(plainText.getBytes("UTF-8"));
            return Base64.getEncoder().encodeToString(encrypt);
        }
    }

    /**
     * AES GCM Decrypt
     *
     * @param cipherText Data that needs to be decrypted
     * @return decrypted String
     */
    public static String aesGcmDecryptByBase64(String aesKey, String aesIv, String cipherText) throws Exception {
        if (cipherText == null) {
            return "";
        } else {
            byte[] decodedKey = Base64.getDecoder().decode(aesKey);
            SecretKeySpec keySpec = new SecretKeySpec(decodedKey, 0, decodedKey.length, "AES");

            Cipher cipher = Cipher.getInstance(_cipherMode);

            GCMParameterSpec ivParamsSpec = new GCMParameterSpec(128, aesIv.getBytes("UTF-8"));

            cipher.init(Cipher.DECRYPT_MODE, keySpec, ivParamsSpec);

            byte[] b = Base64.getDecoder().decode(cipherText);
            byte[] decrypt = cipher.doFinal(b);
            return new String(decrypt, "UTF-8");
        }
    }
}
