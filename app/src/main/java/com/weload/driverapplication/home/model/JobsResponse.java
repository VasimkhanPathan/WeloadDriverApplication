package com.weload.driverapplication.home.model;

import java.util.ArrayList;
import java.util.List;
 public class JobsResponse{
    public int status;
    public String message;
    public ArrayList<Datum> data;

    public class Datum{
        public String jobid;
        public String customer_name;
        public String orderid;
        public String pickup_date;
        public String pickup_unitno;
        public String pickup_address;
        public String pickup_phone;
        public String drop_unitno;
        public String drop_address;
        public String drop_phone;
        public List<AdditionalLocation> additional_location;
        public String pickup_time;
        public String comment;
        public String item_comment;
        public List<Product> products;
        public String total_amount;
        public String paid_amount;
        public String tobe_paid_amount;
        public String job_token;
        public int job_status;
        public List<List<Object>> geo_encodes;


        public class Product{
            public String product_name;
            public List<Option> options;
            public class Option{
                public String name;
                public String value;
                public String price;
            }
        }
        public class AdditionalLocation{
            public String address;
            public String unit_no;
            public String phone;
        }

    }

}