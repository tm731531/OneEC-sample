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


$aesKey = "A123456789012345A123456789012345";
$aesIv = "B123456789012345";
$data = "test";
echo base64_encode($aesKey)."\n";
echo base64_encode($aesIv)."\n";
echo OneECEncryptor::encrypt($data, $aesKey, $aesIv);



?>