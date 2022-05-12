package oneec.java.sample.models;

import java.math.BigDecimal;
import java.util.List;

public class MerchantProduct {
    public String productName;

    public String categoryId; // 分類的ID
    public String[] categoryIds;
    public String[] categoryNames;

    public String brand; // 品牌
    public String supplier; // 供應商
    public int condition; // 0:一般商品 1:即期品 2:贈品
    public int goodType; // 1:原廠 2:經銷商  3:平行輸入
    public int distributionTemperature; // 0:常溫 1:冷藏 2:冷凍
    public boolean isFragile; // 是否為易碎品
    public boolean isPrepareLongStocking; // 是否為需要備貨日
    public int prepareStocking;
    public boolean isCombinationItem; // 是否為組合商品
    public String itemNumber; // 貨號
    public BigDecimal suggestPrice; // 售價
    public BigDecimal normalPrice; // 市價
    public BigDecimal cost; // 成本
    public String currency; // 幣值  台幣為TWD
    public int safetyQty; // 安全庫存
    public int qty; // 總量
    public boolean isCanOvertakeOrder; // 是否為可以超接
    public int overtakeQty; // 可超接數量
    public String eanCode; // 國際條碼
    public String originCountry; // 原產地
    public String subTitle; // 副標
    public String shortDescription1; // 短敘述1
    public String shortDescription2; // 短敘述2
    public String shortDescription3; // 短敘述3
    public String description; // 敘述
    public String instructions; // 使用方法
    public String specDescription; // 規格說明
    public String notice; // 注意事項
    public String saleStatus; // 銷售狀態 上架(on_shelf)、下架(off_shelf)、未設定(unset)
    public String insertDt; // 請使用格式 "yyyy-MM-ddTHH:mm:ss.SSSZ" ,UTC時間
    public String modifiedDt; // 請使用格式 "yyyy-MM-ddTHH:mm:ss.SSSZ" ,UTC時間
    public List<MerchantProductSpec> specs; // 商品規格
    public MerchantProductSize size; // 尺寸

    public static class MerchantProductSize {
        public float length; //  (the unit is cm.)
        public float width; //  (the unit is cm.)
        public float height; //  (the unit is cm.)
        public float weight; //  (the unit is KG.)
    }

    public static class MerchantProductSpec {
        public String name;
        public String value;
    }

    public static class MerchantProductData {
        public int total;
        public int totalPage;
        public List<MerchantProduct> data;
    }
}
