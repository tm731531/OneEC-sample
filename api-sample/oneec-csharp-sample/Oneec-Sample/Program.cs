
using Newtonsoft.Json;
using Oneec_Sample.models;
using Oneec_Sample.services;
using System;

namespace Oneec_Sample
{
    class Program
    {
        static void Main(string[] args)
        {
            string apiRoute = "/oapi/v1/data/merchant/orders";
            string param = "?shipStartDate=2022-03-16T16:46:05.005Z&shipEndDate=2023-03-16T16:46:05.005Z&channelId=AyNAVo&channelSettingId=partner_unit_test&start=0&limit=2";
            string body = "";

            OneECAPI oneECAPI = new OneECAPI(apiRoute + param, body);
            OrderList orderList = oneECAPI.GetOrderList();
            if (orderList != null)
            {
                Console.WriteLine(JsonConvert.SerializeObject(orderList));
            }
            else {
                Console.WriteLine(" Error ");
            }
        }
    }
}
