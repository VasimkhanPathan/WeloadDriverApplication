package com.weload.driverapplication.JobDescription.model;

import java.util.ArrayList;

public class WalletResponse {
    public class Datum{
        public String job_id;
        public String order_id;
        public String job_date;
        public String transactionid;
        public String final_total_amount;
        public String total_order_amount;
        public String driver_amount;
        public String weload_job_amount;
        public String weload_diff_amount;
        public String driver_diff_amount;
        public String transacation_type;
        public String diff_note;

    }
    public String dues;
    public String totaltrans;
        public String totalamount;
        public String message;
        public String drivertotal;
        public int status;
        public ArrayList<Datum> data;

}
