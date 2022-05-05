using System;
using System.Collections.Generic;
using System.Text;

namespace Oneec_Sample.models
{
    public class MerchantCombinationProduct
    {

        public String itemNumber;
        public List<MerchantCombinationInfo> combinationInfos;

        public String insertDt;
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
