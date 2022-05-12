package oneec.java.sample.orders;

import java.math.BigInteger;
import java.security.MessageDigest;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.json.JsonMapper;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import oneec.java.sample.helper.CryptHelper;
import oneec.java.sample.models.Response;

public class GetOrderList {
    private static final String _domain = "https://dev-api.oneec.ai"; // Dev url
    private static final String _apiUrl = "/oapi/v1/data/merchant/orders";
    private static final String _partnerKeyID = ""; // Please contact the official counterpart
    private static final String _aesKey = ""; // Please contact the official counterpart
    private static final String _aesIv = ""; // Please contact the official counterpart
    private static final String _hashKey = ""; // Please contact the official counterpart
    private static final String _merchantAccessToken = ""; // Please contact the official counterpart
    private static final String _token = _partnerKeyID + "." + _merchantAccessToken;

    public static void trymain(String[] args) throws Exception{

        
        var aesKey = "A123456789012345A123456789012345";
        var aesIv = "B123456789012345";
        var cipherText = "s/zbKa8wB1HeTuODWwNj9kacDX0=";
        var keySpec = CryptHelper.getSecretKeySpec(aesKey);
        var rc =  CryptHelper.decrypt(keySpec, aesIv, "AES/GCM/NoPadding", cipherText);
        System.out.println(rc);



    }

    public static void main(String[] args) {

        // GetOrderList getOrderList = new GetOrderList();
        try {
            // getOrderList.getData();
            trymain(args);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void getData() throws Exception {
        String param = "";
        //param = param+"?";
        //param = param + "&shipStartDate=2022-03-16T16:46:05.005Z";      // ship start date  default ""
        //param = param + "&shipEndDate=2023-03-16T16:46:05.005Z";        // ship end date  default ""
        //param = param + "&orderCreateDate=2022-03-16T16:46:05.005Z";    // order create date  default ""
        //param = param + "&orderStatus=";                                //
        //param = param + "&channelId=AyNAVo";                            // channel id
        //param = param + "&channelSettingId=partner_unit_test";          // platform
        //param = param + "&start=0";                                     // start default 0
        //param = param + "&limit=20";                                    // page size default 10
        String endpoint = _domain + _apiUrl + param;
        String encryptedData = CryptHelper.aesGcmEncryptToBase64(_aesKey, _aesIv, null);
        System.out.println("encryptedData: " + encryptedData);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(_token);
        headers.add("X-sign", getXSign(param, encryptedData));

        HttpEntity<String> request = new HttpEntity<>(encryptedData, headers);

        ResponseEntity<String> entity = new RestTemplate().exchange(endpoint, HttpMethod.GET, request, String.class);

        TypeReference<Response> typeReference = new TypeReference<>() {
        };

        Response response = new JsonMapper().readValue(entity.getBody(), typeReference);
        if (response.status == HttpStatus.OK.value()) {
            String decryptedData = CryptHelper.aesGcmDecryptByBase64(_aesKey, _aesIv, response.data);
            System.out.println("decryptedData: " + decryptedData);
        }
    }

    private String getXSign(String param, String body) throws Exception {
        String sign = _apiUrl + param + body + _hashKey;

        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        digest.update(sign.getBytes("utf8"));
        return String.format("%064x", new BigInteger(1, digest.digest()));
    }
}
