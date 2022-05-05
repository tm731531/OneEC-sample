using System;
using System.Collections.Generic;
using System.Text;

namespace Oneec_Sample.models
{
    public class MerchantCombinationProduct
    {
        /// <summary>
        /// 料號
        /// </summary>
        public String itemNumber;

        /// <summary>
        /// 組合的內容資訊
        /// </summary>
        public List<MerchantCombinationInfo> combinationInfos;

        /// <summary>
        /// 格式 "yyyy-MM-ddTHH:mm:ss.SSSZ" ,UTC時間
        /// </summary>
        public String insertDt;
        /// <summary>
        /// 格式 "yyyy-MM-ddTHH:mm:ss.SSSZ" ,UTC時間
        /// </summary>
        public String modifiedDt;

        public void addCombinationInfo(MerchantCombinationInfo combinationInfo)
        {
            if (combinationInfos == null)
            {
                combinationInfos = new List<MerchantCombinationInfo>();
            }

            combinationInfos.Add(combinationInfo);
        }
    }
}
