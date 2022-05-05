using System;
using System.Collections.Generic;
using System.Text;

namespace Oneec_Sample.models
{

    public class MerchantProduct
    {
        public string productName { get; set; }
        public string categoryId { get; set; }
        public string[] categoryIds { get; set; }
        public string[] categoryNames { get; set; }
        public string brand { get; set; }
        public string supplier { get; set; }
        public int condition { get; set; }
        public int goodType { get; set; }
        public int distributionTemperature { get; set; }
        public bool isFragile { get; set; }
        public bool isPrepareLongStocking { get; set; }
        public int prepareStocking { get; set; }
        public object isCombinationItem { get; set; }
        public string itemNumber { get; set; }
        public float suggestPrice { get; set; }
        public float normalPrice { get; set; }
        public float cost { get; set; }
        public string currency { get; set; }
        public int safetyQty { get; set; }
        public int qty { get; set; }
        public bool isCanOvertakeOrder { get; set; }
        public int overtakeQty { get; set; }
        public string eanCode { get; set; }
        public object originCountry { get; set; }
        public string subTitle { get; set; }
        public string shortDescription1 { get; set; }
        public string shortDescription2 { get; set; }
        public string shortDescription3 { get; set; }
        public string description { get; set; }
        public string instructions { get; set; }
        public string specDescription { get; set; }
        public string notice { get; set; }
        public string saleStatus { get; set; }
        public String insertDt { get; set; }
        public String modifiedDt { get; set; }
        public MerchantProductSpec[] specs { get; set; }
        public MerchantProductSize size { get; set; }
    }

    public class MerchantProductSize
    {
        public int length { get; set; }
        public int width { get; set; }
        public int height { get; set; }
        public float weight { get; set; }
    }

    public class MerchantProductSpec
    {
        public string name { get; set; }
        public string value { get; set; }
    }

    public class MerchantProductData
    {
        public int total { get; set; }
        public int totalPage { get; set; }
        public MerchantProduct[] data { get; set; }
    }

}
