using System;
using System.Collections.Generic;
using System.Text;

namespace Oneec_Sample.models
{
    public class Response
    {
        public int status { get; set; }
        public object message { get; set; }
        public string data { get; set; }
    }

}
