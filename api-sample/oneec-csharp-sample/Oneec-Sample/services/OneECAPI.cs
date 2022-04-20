using Newtonsoft.Json;
using NPOI.POIFS.Crypt;
using Oneec_Sample.models;
using System;
using System.IO;
using System.Net;
using System.Net.Http;
using System.Net.Http.Headers;
using System.Security.Cryptography;
using System.Text;

namespace Oneec_Sample.services

{
    class OneECAPI
    {
        private readonly HttpClient httpClient = new HttpClient();
        // dev url
        string _endpoint = "https://dev-api.oneec.ai";  
        string _authorization = string.Empty;
        string _xsign = string.Empty;

        /* Please contact the official counterpart */
        // partner key id
        string _partnerKeyId = ""; 
        // aes key 
        string _AESKey = "";
        // aes iv
        string _AESIV = "";
        // hash key
        string _hashKey = "";  
        // merchant account token
        string _merchantAccountToken = "";  
       
        public string AESKey { get; set; }
        public string AESIV { get; set; }

        public OneECAPI(String apiRoute, String body)
        {
            AESKey = _AESKey;
            AESIV = _AESIV;
            _endpoint = $"{_endpoint}{apiRoute}";
            _authorization = $"{_partnerKeyId}.{_merchantAccountToken}";
            _xsign = GetXSign(apiRoute, body);
            httpClient.DefaultRequestHeaders.Add("Authorization", $"Bearer {_authorization}");
            httpClient.DefaultRequestHeaders.Add("X-sign", _xsign);
            httpClient.DefaultRequestHeaders.Accept.Add(new MediaTypeWithQualityHeaderValue("application/json"));

        }
        private string GetXSign(string url, string body)
        {
            var target = url + body + _hashKey;
            var xSign = CryptService.SHA256Hash(target);
            return xSign;
        }

        public OrderList GetOrderList()
        {
            OrderList result = null;
            try
            {
                var message = httpClient.GetAsync(_endpoint).Result;
                string resultStr = message.Content.ReadAsStringAsync().Result;
                var objectModel = JsonConvert.DeserializeObject<Response>(resultStr);
                if (objectModel.status == 200)
                {
                    var data = CryptService.AesGcmDecryptTByBase64(AESKey, AESIV, objectModel.data);
                    result = JsonConvert.DeserializeObject<OrderList>(data);
                }
            }
            catch (WebException ex)
            {
                StreamReader sr = new StreamReader(ex.Response.GetResponseStream());
                throw new Exception($"Error :{sr.ReadToEnd()}");
            }

            return result;
        }
    }
}
