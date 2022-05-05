using NPOI.POIFS.Crypt;
using System;
using System.Collections.Generic;
using System.Security.Cryptography;
using System.Text;

namespace Oneec_Sample.services
{
    public class CryptService
    {

        /// <summary>
        /// AES GCM Encrypt
        /// </summary>
        /// <param name="key">key</param>
        /// <param name="iv">iv</param>
        /// <param name="data">data</param>
        /// <returns></returns>

        public static string AesGcmEncryptToBase64(string key, string iv, string data)
        {
            var keyBytes = Encoding.UTF8.GetBytes(key);
            var ivBytes = Encoding.UTF8.GetBytes(iv);
            var dataBytes = Encoding.UTF8.GetBytes(data);

            SecretKeySpec skeySpec = new SecretKeySpec(keyBytes, "AES");
            Cipher cipher = Cipher.GetInstance("AES/GCM/NoPadding");
            cipher.Init(Cipher.ENCRYPT_MODE, skeySpec, new IvParameterSpec(ivBytes));
            byte[] encrypted = cipher.DoFinal(dataBytes);
            return Convert.ToBase64String(encrypted);
        }

        /// <summary>
        /// AES GCM Decrypt
        /// </summary>
        /// <param name="key">key</param>
        /// <param name="iv">iv</param>
        /// <param name="data">data</param>
        public static string AesGcmDecryptTByBase64(string key, string iv, string data)
        {
           
            var keyBytes = Convert.FromBase64String(key);
            var ivBytes = Encoding.UTF8.GetBytes(iv);
            var dataBytes = Convert.FromBase64String(data);

            SecretKeySpec skeySpec = new SecretKeySpec(keyBytes, "AES");
            Cipher cipher = Cipher.GetInstance("AES/GCM/NoPadding");
            cipher.Init(Cipher.DECRYPT_MODE, skeySpec, new IvParameterSpec(ivBytes));
            byte[] decrypted = cipher.DoFinal(dataBytes);
            return Encoding.UTF8.GetString(decrypted);
        }
        public static string SHA256Hash(string value)
        {
            StringBuilder Sb = new StringBuilder();

            using (SHA256 hash = SHA256Managed.Create())
            {
                Encoding enc = Encoding.UTF8;
                Byte[] result = hash.ComputeHash(enc.GetBytes(value));

                foreach (Byte b in result)
                    Sb.Append(b.ToString("x2"));
            }

            return Sb.ToString();
        }
    }
}
