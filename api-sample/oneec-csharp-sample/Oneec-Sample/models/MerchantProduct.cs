using System;
using System.Collections.Generic;
using System.Text;

namespace Oneec_Sample.models
{

    public class MerchantProduct
    {
        public string productName { get; set; }
        /// <summary>
        /// 分類的ID
        /// </summary>
        public string categoryId { get; set; }
        public string[] categoryIds { get; set; }
        public string[] categoryNames { get; set; }

        /// <summary>
        /// 品牌
        /// </summary>
        public string brand { get; set; }

        /// <summary>
        /// 供應商
        /// </summary>
        public string supplier { get; set; }

        /// <summary>
        /// 0:一般商品 1:即期品 2:贈品
        /// </summary>
        public int condition { get; set; }
        /// <summary>
        /// 1:原廠 2:經銷商  3:平行輸入
        /// </summary>
        public int goodType { get; set; }
        /// <summary>
        /// 0:常溫 1:冷藏 2:冷凍
        /// </summary>
        public int distributionTemperature { get; set; }
        /// <summary>
        /// 是否為易碎品
        /// </summary>
        public bool isFragile { get; set; }
        /// <summary>
        /// 是否為需要備貨日
        /// </summary>
        public bool isPrepareLongStocking { get; set; }
        public int prepareStocking { get; set; }
        /// <summary>
        /// 是否為組合商品
        /// </summary>
        public object isCombinationItem { get; set; }
        /// <summary>
        /// 貨號
        /// </summary>
        public string itemNumber { get; set; }
        /// <summary>
        /// 售價
        /// </summary>
        public float suggestPrice { get; set; }
        /// <summary>
        /// 市價
        /// </summary>
        public float normalPrice { get; set; }
        /// <summary>
        /// 成本
        /// </summary>
        public float cost { get; set; }
        /// <summary>
        /// 幣值  台幣為TWD
        /// </summary>
        public string currency { get; set; }
        /// <summary>
        /// 安全庫存
        /// </summary>
        public int safetyQty { get; set; }
        /// <summary>
        /// 總量
        /// </summary>
        public int qty { get; set; }
        /// <summary>
        /// 是否為可以超接
        /// </summary>
        public bool isCanOvertakeOrder { get; set; }
        /// <summary>
        /// 可超接數量
        /// </summary>
        public int overtakeQty { get; set; }
        /// <summary>
        /// 國際條碼
        /// </summary>
        public string eanCode { get; set; }
        /// <summary>
        /// 原產地
        /// </summary>
        public object originCountry { get; set; }
        /// <summary>
        /// 副標
        /// </summary>
        public string subTitle { get; set; }
        /// <summary>
        /// 短敘述
        /// </summary>
        public string shortDescription1 { get; set; }
        public string shortDescription2 { get; set; }
        public string shortDescription3 { get; set; }
        /// <summary>
        /// 敘述
        /// </summary>
        public string description { get; set; }
        /// <summary>
        /// 使用方法
        /// </summary>
        public string instructions { get; set; }
        /// <summary>
        /// 規格說明
        /// </summary>
        public string specDescription { get; set; }
        /// <summary>
        /// 注意事項
        /// </summary>
        public string notice { get; set; }
        /// <summary>
        /// 銷售狀態 上架(on_shelf)、下架(off_shelf)、未設定(unset)
        /// </summary>
        public string saleStatus { get; set; }

        /// <summary>
        /// 請使用格式 "yyyy-MM-ddTHH:mm:ss.SSSZ" ,UTC時間
        /// </summary>
        public String insertDt { get; set; }

        /// <summary>
        /// 請使用格式 "yyyy-MM-ddTHH:mm:ss.SSSZ" ,UTC時間
        /// </summary>
        public String modifiedDt { get; set; }
        /// <summary>
        /// 商品規格
        /// </summary>
        public MerchantProductSpec[] specs { get; set; }
        /// <summary>
        /// 尺寸
        /// </summary>
        public MerchantProductSize size { get; set; }
    }

    public class MerchantProductSize
    {
        /// <summary>
        ///  (the unit is cm.) 
        /// </summary>
        public int length { get; set; }
        /// <summary>
        ///  (the unit is cm.) 
        /// </summary>
        public int width { get; set; }
        /// <summary>
        ///  (the unit is cm.) 
        /// </summary>
        public int height { get; set; }
        /// <summary>
        ///  (the unit is KG.) 
        /// </summary>
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
