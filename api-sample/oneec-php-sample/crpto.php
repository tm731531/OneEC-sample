<?php
class OneECEncryptor {
    /**
    * Encrypts given data with given key, iv and aad, returns a base64 encoded string.
    *
    * @param string $plaintext - Text to encode.
    * @param string $key - The secret key, 32 bytes string.
    * @param string $iv - The initialization vector, 16 bytes string.
    * @param string $aad - The additional authenticated data, maybe empty string.
    *
    * @return string - The base64-encoded ciphertext.
    */
    static function encrypt(string $plaintext, string $key, string $iv = '', string $aad = ''): string
    {
        $ciphertext = openssl_encrypt($plaintext, 'aes-256-gcm', $key, OPENSSL_RAW_DATA, $iv, $tag, $aad, 16);

        if (false === $ciphertext) {
            throw new UnexpectedValueException('Encrypting the input $plaintext failed, please checking your $key and $iv whether or nor correct.');
        }

        return base64_encode($ciphertext . $tag);
    }

    /**
    * Takes a base64 encoded string and decrypts it using a given key, iv and aad.
    *
    * @param string $ciphertext - The base64-encoded ciphertext.
    * @param string $key - The secret key, 32 bytes string.
    * @param string $iv - The initialization vector, 16 bytes string.
    * @param string $aad - The additional authenticated data, maybe empty string.
    *
    * @return string - The utf-8 plaintext.
    */
    static function decrypt(string $ciphertext, string $key, string $iv = '', string $aad = ''): string
    {
        $ciphertext = base64_decode($ciphertext);
        $authTag = substr($ciphertext, -16);
        $tagLength = strlen($authTag);

        /* Manually checking the length of the tag, because the `openssl_decrypt` was mentioned there, it's the caller's responsibility. */
        if ($tagLength > 16 || ($tagLength < 12 && $tagLength !== 8 && $tagLength !== 4)) {
            throw new RuntimeException('The inputs `$ciphertext` incomplete, the bytes length must be one of 16, 15, 14, 13, 12, 8 or 4.');
        }

        $plaintext = openssl_decrypt(substr($ciphertext, 0, -16), 'aes-256-gcm', $key, OPENSSL_RAW_DATA, $iv, $authTag, $aad);

        if (false === $plaintext) {
            throw new UnexpectedValueException('Decrypting the input $ciphertext failed, please checking your $key and $iv whether or nor correct.');
        }

        return $plaintext;
    }
}






// https://drive.google.com/drive/folders/1u4lh7NleX08ardH7jhI17EfYhIGeysHQ
$domain = "https://dev-api.oneec.ai";
$url = "/oapi/v1/data/merchant/orders";
$partnerKeyId = "l011cx";
$hashKey = "SkeF22b3OhvSkG8kMRxzlSUExV2AUwTd";
$merchantAccountToken = "QkNk+7SnB7CPbgX84Oi7awB2rUyC4QFTkW4PX/hL2dvhwG1Ll+81fEs3jLsOtWSf5AUDooKYn7jfTfsjhVPYEnYFEl7uiqbSIvSChAtoTbE9j9c4HQeoj+XODTqTRnTaXLyF7N6/HRbz7aYhFqCDLoSmOSf/bnSyyg/KxBFQ1vfULnfaD+9HYqlN";
$aesKey = "HLWX8IZir9wZIlhruFzLLd2oG8jy1NTZ";
$aesIv = "2vIaD0Lqi6mo0sEQ";



$jsonBody = <<<DOC
[
    {
        "orderSn": "EC-123456789",
        "orderStatus": 1,
        "orderCreateDt": "2022-02-21T01:05:13.980Z",
        "lastShipDate": "2022-02-24T01:05:13.980Z",
        "totalPrice": 1000,
        "currency": "TWD",
        "orderNote": "宅配測試用訂單,請勿出貨",
        "buyerName": "buyer name",
        "buyerPhone": "0912345678",
        "recipientName": "buyer name",
        "recipientMobile": "0922345678",
        "recipientPhone": "0932345678",
        "recipientAddressLine1": " (114)台北市內湖區瑞光路 318 號",
        "distributionTemperature": 0,
        "deliveryWay": "0.0",
        "products": [
            {
                "supplierPartNumber": "128117204",
                "productNumber": "16892447",
                "productName": "測試用商品",
                "qty": 2,
                "cost": 350,
                "price": 500
            }
        ]
    },
    {
        "orderSn": "EC-123456790",
        "orderStatus": 1,
        "orderCreateDt": "2022-02-21T01:05:13.980Z",
        "lastShipDate": "2022-02-24T01:05:13.980Z",
        "totalAmount": 250,
        "currency": "TWD",
        "orderNote": "超取測試用訂單,請勿出貨",
        "buyerName": "buyer name",
        "buyerPhone": "0912345678",
        "recipientName": "recipient name",
        "recipientMobile": "0922345678",
        "recipientPhone": "0932345678",
        "distributionTemperature": 0,
        "deliveryWay": "0.0",
        "products": [
                {
                    "supplierPartNumber": "128117204",
                    "productNumber": "16892447",
                    "productName": "測試用商品",
                    "qty": 1,
                    "cost": 175,
                    "price": 250
                }
            ]
        }
    ]
DOC;

$encryptedBody = OneECEncryptor::encrypt($jsonBody, $aesKey, $aesIv);
$xSign =  $url.$encryptedBody.$hashKey;
$sha256_xSign =  hash('sha256', $xSign);

$options = array(
    'http' => array(
        'method' => 'POST',
        'header' => array(
            "Content-type: application/json; charset=utf-8 \r\n".
            "Authorization: Bearer $partnerKeyId.$merchantAccountToken \r\n".
            "X-sign: $sha256_xSign"
        ),
        'content' => $encryptedBody,
    )
);
$context = stream_context_create($options);
$result = file_get_contents($domain.$url, false, $context);
echo $result;

?>