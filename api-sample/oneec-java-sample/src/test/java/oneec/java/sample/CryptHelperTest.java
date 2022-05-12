package oneec.java.sample;

import static org.junit.Assert.assertTrue;

import java.net.URLEncoder;
import java.util.Base64;

import javax.crypto.spec.SecretKeySpec;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import oneec.java.sample.helper.CryptHelper;

public class CryptHelperTest {
    private static Logger log = LoggerFactory.getLogger(CryptHelperTest.class);

    @Test
    public void testAesGcm() throws Exception {
        try{
            String key = "A123456789012345A123456789012345";
            String iv = "B123456789012345";

            String cipherMode = "AES/GCM/NoPadding";
            String data = "{\"Name\":\"Test\",\"ID\":\"A123456789\"}";

            data = URLEncoder.encode(data, "UTF-8");

            System.out.println("data: "+data);

            var keySpec = CryptHelper.getSecretKeySpec(key);

            var encryptString = CryptHelper.encrypt(keySpec, iv, cipherMode, data);

            System.out.println("encryptString: "+encryptString);

            var decryptString = CryptHelper.decrypt(keySpec, iv, cipherMode, encryptString);

            System.out.println("decryptString: "+decryptString);
            System.out.println("data: "+data);

            System.out.println(data.equals(decryptString));

            assertTrue("decrypt fail", data.equals(decryptString));

        }catch (Exception e){
            log.error(e.getMessage(), e);
            throw e;
        }
    }

    @Test
    public void testLoadAesKey() throws Exception {

        try{
            String secretKey = "A123456789012345A123456789012345";
            String iv = "B123456789012345";

            String cipherMode = "AES/GCM/NoPadding";
            String data = "{\"Name\":\"Test\",\"ID\":\"A123456789\"}";

            SecretKeySpec secretKeySpec = CryptHelper.getSecretKeySpec(secretKey);
            String secretKeySpecString = Base64.getEncoder().encodeToString(secretKeySpec.getEncoded());

            System.out.println(secretKeySpecString);

            var keySpec = CryptHelper.loadSecretKeySpecFromBase64(secretKeySpecString);

            data = URLEncoder.encode(data, "UTF-8");

            System.out.println("data: "+data);

            var encryptString = CryptHelper.encrypt(keySpec, iv, cipherMode, data);

            System.out.println("encryptString: "+encryptString);

            var decryptString = CryptHelper.decrypt(keySpec, iv, cipherMode, encryptString);

            System.out.println("decryptString: "+decryptString);
            System.out.println("data: "+data);

            System.out.println(data.equals(decryptString));

            assertTrue("decrypt fail", data.equals(decryptString));

        }catch (Exception e){
            log.error(e.getMessage(), e);
            throw e;
        }
    }
}
