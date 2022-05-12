<?php
class OneECEncryptor {

    private static $OPENSSL_CIPHER_NAME = "aes-128-cbc"; //Name of OpenSSL Cipher 
    private static $CIPHER_KEY_LEN = 16; //128 bits

    /**
     * Encrypt data using AES Cipher (CBC) with 128 bit key
     * 
     * @param type $key - key to use should be 16 bytes long (128 bits)
     * @param type $iv - initialization vector
     * @param type $data - data to encrypt
     * @return encrypted data in base64 encoding with iv attached at end after a :
     */

    static function encrypt($key, $iv, $data) {
        // if (strlen($key) < PHP_AES_Cipher::$CIPHER_KEY_LEN) {
        //     $key = str_pad("$key", PHP_AES_Cipher::$CIPHER_KEY_LEN, "0"); //0 pad to len 16
        // } else if (strlen($key) > PHP_AES_Cipher::$CIPHER_KEY_LEN) {
        //     $key = substr($key, 0, PHP_AES_Cipher::$CIPHER_KEY_LEN); //truncate to 16 bytes
        // }

        $encodedEncryptedData = base64_encode(openssl_encrypt($data, PHP_AES_Cipher::$OPENSSL_CIPHER_NAME, $key, OPENSSL_RAW_DATA, $iv));
        $encodedIV = base64_encode($iv);
        $encryptedPayload = $encodedEncryptedData.":".$encodedIV;

        return $encryptedPayload;

    }

    /**
     * Decrypt data using AES Cipher (CBC) with 128 bit key
     * 
     * @param type $key - key to use should be 16 bytes long (128 bits)
     * @param type $data - data to be decrypted in base64 encoding with iv attached at the end after a :
     * @return decrypted data
     */
    static function decrypt2($key, $data) {
        if (strlen($key) < PHP_AES_Cipher::$CIPHER_KEY_LEN) {
            $key = str_pad("$key", PHP_AES_Cipher::$CIPHER_KEY_LEN, "0"); //0 pad to len 16
        } else if (strlen($key) > PHP_AES_Cipher::$CIPHER_KEY_LEN) {
            $key = substr($key, 0, PHP_AES_Cipher::$CIPHER_KEY_LEN); //truncate to 16 bytes
        }

        $parts = explode(':', $data); //Separate Encrypted data from iv.
        $decryptedData = openssl_decrypt(base64_decode($parts[0]), PHP_AES_Cipher::$OPENSSL_CIPHER_NAME, $key, OPENSSL_RAW_DATA, base64_decode($parts[1]));

        return $decryptedData;
    }


    static function aesGcm($key, $iv, $data)
    {

        // $key = hex2bin($key);
        $cipher = "aes-128-gcm";
        // $cipher = "AES-128-CBC";
        $fakeivlen = openssl_cipher_iv_length($cipher);
        $fakeiv = openssl_random_pseudo_bytes($fakeivlen);
        $fakeiv64 = base64_encode($fakeiv);
        echo "fakeivlen:".$fakeivlen."\n";
        echo "fakeiv:".$fakeiv."\n";
        echo "fakeiv64:".$fakeiv64."\n";
        echo "fakeiv64len:".strlen($fakeiv64)."\n";
        
        
        $real_iv_decode = base64_decode($iv);
        echo "real_iv:".$iv."\n";
        echo "real_iv_len:".strlen($iv)."\n";
        echo "real_iv_decode:".$real_iv_decode."\n";
        echo "real_iv_decodelen:".strlen($real_iv_decode)."\n";
        
        // $key = str_replace(array("\r", "\n"), '', $key);
        // $key = base64_decode($key);
        // $key = unpack('C*', $key);
        // var_dump($key);
        // echo "key:".base64_encode($key)."\n";
        // echo "key:".strlen($key)."\n";

        // $iv = unpack('C*', $iv);
        // var_dump($iv);


        // $data = str_replace(array("\r", "\n"), '', $data);
        // $data = unpack('C*', $data);
        // $encrypt1 = openssl_encrypt($data, PHP_AES_Cipher::$OPENSSL_CIPHER_NAME, $key, OPENSSL_RAW_DATA, $real_iv_decode, $tag1);
        // echo "encrypt1:".base64_encode($encrypt1)."\n";
        // echo "tag1".$tag1."\n";
        
        $encrypt2 = openssl_encrypt(base64_decode($data), $cipher, base64_decode($key), OPENSSL_NO_PADDING, $real_iv_decode, $tag2);
        echo "encrypt2:".$encrypt2."\n";
        echo "encrypt2_enc:".base64_encode( $real_iv_decode.$encrypt2 )."\n";
        echo "tag2".$tag2."\n";
        
        return base64_encode($encrypt2);
    }


    static function decrypt($key, $iv, $data){
        $cipher = "aes-128-gcm";
        $encrypt2 = openssl_decrypt(base64_decode($data), $cipher, base64_decode($key), OPENSSL_RAW_DATA, base64_decode($iv));
        return $encrypt2;
    }

    function aes_gcm_decrypt_orig($content, $secret) {
        $cipher = 'aes-128-gcm';
        $ciphertextwithiv = bin2hex(base64_decode($content));
        $iv = substr($ciphertextwithiv, 0, 24);
        $tag = substr($ciphertextwithiv , -32, 32);
        $ciphertext = substr($ciphertextwithiv, 24, strlen($ciphertextwithiv) - 24 - 32);
        $skey = bin2hex(base64_decode($secret));
        echo "iv:".$iv."\n";
        echo "tag:".$tag."\n";
        echo "ciphertext:".$ciphertext."\n";
        echo "ciphertextwithiv:".$ciphertextwithiv."\n";
    
        return openssl_decrypt(hex2bin($ciphertext), $cipher, $skey, OPENSSL_RAW_DATA, hex2bin($iv), hex2bin($tag));
    }


    function aes_gcm_decrypt($content, $secret) {
        $cipher = 'aes-128-gcm';
        $ciphertextwithiv = bin2hex(base64_decode($content));
        

        $ivlen = 4;
        $iv = substr($ciphertextwithiv, 0, $ivlen);
        $ciphertext = substr($ciphertextwithiv, $ivlen, strlen($ciphertextwithiv) - $ivlen);
        $skey = bin2hex(base64_decode($secret));

        echo "iv:".$iv."\n";
        echo "ciphertext:".$ciphertext."\n";
        echo "ciphertextwithiv:".$ciphertextwithiv."\n";
    
        return openssl_decrypt(hex2bin($ciphertext), $cipher, $skey, OPENSSL_RAW_DATA, hex2bin($iv));
    }


    function encrypt2($key, $textToEncrypt){
        $cipher = 'aes-256-gcm';
        $iv_len = 128;
        // $tag_length = l6;
        $version_length = 3;
        $iv = openssl_random_pseudo_bytes($iv_len);
        echo "iv:".base64_encode($iv)."\n";
        $tag = ""; // will be filled by openssl_encrypt
        $ciphertext = openssl_encrypt($textToEncrypt, $cipher, $key, OPENSSL_ZERO_PADDING, $iv, $tag, "", $tag_length=16);
        echo "tag:".base64_encode($tag)."\n";
        $encrypted = base64_encode($iv.$ciphertext.$tag);
        return $encrypted;
    }
}


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
function encrypt(string $plaintext, string $key, string $iv = '', string $aad = ''): string
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
function decrypt(string $ciphertext, string $key, string $iv = '', string $aad = ''): string
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

$aeskey = "SExXWDhJWmlyOXdaSWxocnVGekxMZDJvRzhqeTFOVFo=";
$aesiv = "2vIaD0Lqi6mo0sEQ";
$data = "test";
echo encrypt($data, $aeskey, $aesiv);



?>