using System;
using System.Collections.Generic;
using System.Text;

namespace Oneec_Sample.models
{

    public class OrderList
    {
        public int total { get; set; }
        public int totalPage { get; set; }
        public Datum[] data { get; set; }
    }

    public class Datum
    {
        public string channelId { get; set; }
        public string channelSettingId { get; set; }
        public string recipientName { get; set; }
        public string recipientPhone { get; set; }
        public string recipientMobile { get; set; }
        public string recipientAddressCity { get; set; }
        public string recipientAddressRegion { get; set; }
        public string recipientAddressCountry { get; set; }
        public string recipientAddressZip { get; set; }
        public string recipientAddressLine1 { get; set; }
        public string recipientAddressLine2 { get; set; }
        public string buyerName { get; set; }
        public string buyerPhone { get; set; }
        public object senderName { get; set; }
        public object senderPhone { get; set; }
        public object senderAddressCity { get; set; }
        public object senderAddressRegion { get; set; }
        public object senderAddressCountry { get; set; }
        public object senderAddressZip { get; set; }
        public object senderAddressLine1 { get; set; }
        public object senderAddressLine2 { get; set; }
        public string orderSn { get; set; }
        public float totalAmount { get; set; }
        public float totalPrice { get; set; }
        public string currency { get; set; }
        public DateTime orderCreateDt { get; set; }
        public DateTime shipDate { get; set; }
        public DateTime lastShipDate { get; set; }
        public object note { get; set; }
        public int? orderStatus { get; set; }
        public int? shipStatus { get; set; }
        public object refundStatus { get; set; }
        public object canceledStatus { get; set; }
        public string deliveryWay { get; set; }
        public string orderShipcodeCompany { get; set; }
        public string orderShipcode { get; set; }
        public int? distributionTemperature { get; set; }
        public object paymentMethod { get; set; }
        public int? orderDataSoure { get; set; }
        public DateTime insertDt { get; set; }
        public DateTime modifiedDt { get; set; }
        public object storeDeliveryInfo { get; set; }
        public object otherData { get; set; }
        public object orderStatusNote { get; set; }
        public Product[] products { get; set; }
        public object[] refunds { get; set; }
        public object[] canceled { get; set; }
    }

    public class Product
    {
        public string itemSn { get; set; }
        public string productName { get; set; }
        public int qty { get; set; }
        public float cost { get; set; }
        public float price { get; set; }
        public string supplierPartNumber { get; set; }
        public string productNumber { get; set; }
    }

}
