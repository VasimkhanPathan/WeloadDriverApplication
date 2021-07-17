package com.weload.driverapplication.JobDescription.model;

import android.os.Parcel;
import android.os.Parcelable;

public class CompletJobResponse implements Parcelable {
    public static class Data implements Parcelable {
        public String jobid;
        public String job_date;
        public String total_amount;
        public String deposite;
        public String cod;
        public String tobe_collected_amount;
        public String eaxtra_amount;

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.jobid);
            dest.writeString(this.job_date);
            dest.writeString(this.total_amount);
            dest.writeString(this.deposite);
            dest.writeString(this.cod);
            dest.writeString(this.tobe_collected_amount);
            dest.writeString(this.eaxtra_amount);
        }

        public Data() {
        }

        protected Data(Parcel in) {
            this.jobid = in.readString();
            this.job_date = in.readString();
            this.total_amount = in.readString();
            this.deposite = in.readString();
            this.cod = in.readString();
            this.tobe_collected_amount = in.readString();
            this.eaxtra_amount = in.readString();
        }

        public static final Creator<Data> CREATOR = new Creator<Data>() {
            @Override
            public Data createFromParcel(Parcel source) {
                return new Data(source);
            }

            @Override
            public Data[] newArray(int size) {
                return new Data[size];
            }
        };
    }
        public int status;
        public Data data;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.status);
        dest.writeParcelable(this.data, flags);
    }

    public CompletJobResponse() {
    }

    protected CompletJobResponse(Parcel in) {
        this.status = in.readInt();
        this.data = in.readParcelable(Data.class.getClassLoader());
    }

    public static final Parcelable.Creator<CompletJobResponse> CREATOR = new Parcelable.Creator<CompletJobResponse>() {
        @Override
        public CompletJobResponse createFromParcel(Parcel source) {
            return new CompletJobResponse(source);
        }

        @Override
        public CompletJobResponse[] newArray(int size) {
            return new CompletJobResponse[size];
        }
    };
}
