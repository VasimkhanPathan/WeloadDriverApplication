package com.weload.driverapplication.JobDescription.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

public class JobDescriptionRespons implements Parcelable {
    public int status;
    public Data data;

    public static class Data implements Parcelable {
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
        public ArrayList<AdditionalLocation> additional_location;
        public String pickup_time;
        public String comment;
        public String item_comment;
        public List<Product> products;
        public String total_amount;
        public String paid_amount;
        public String tobe_paid_amount;
        public int job_status;
        public String image;
        public String job_token;
        public ArrayList<GeoEncodes> geo_encodes;
        public static class Product implements Parcelable {
            public String product_name;
            public List<Option> options;

            public static class Option implements Parcelable {
                public String name;
                public String value;
                public String price;

                @Override
                public int describeContents() {
                    return 0;
                }

                @Override
                public void writeToParcel(Parcel dest, int flags) {
                    dest.writeString(this.name);
                    dest.writeString(this.value);
                    dest.writeString(this.price);
                }

                public Option() {
                }

                protected Option(Parcel in) {
                    this.name = in.readString();
                    this.value = in.readString();
                    this.price = in.readString();
                }

                public static final Creator<Option> CREATOR = new Creator<Option>() {
                    @Override
                    public Option createFromParcel(Parcel source) {
                        return new Option(source);
                    }

                    @Override
                    public Option[] newArray(int size) {
                        return new Option[size];
                    }
                };
            }

            @Override
            public int describeContents() {
                return 0;
            }

            @Override
            public void writeToParcel(Parcel dest, int flags) {
                dest.writeString(this.product_name);
                dest.writeList(this.options);
            }

            public Product() {
            }

            protected Product(Parcel in) {
                this.product_name = in.readString();
                this.options = new ArrayList<Option>();
                in.readList(this.options, Option.class.getClassLoader());
            }

            public static final Creator<Product> CREATOR = new Creator<Product>() {
                @Override
                public Product createFromParcel(Parcel source) {
                    return new Product(source);
                }

                @Override
                public Product[] newArray(int size) {
                    return new Product[size];
                }
            };
        }
        public class AdditionalLocation{
            public String address;
            public String unit_no;
            public String phone;
        }
        public static class GeoEncodes implements Parcelable {
            public String address;
            public double latitude;
            public double longitude;
            public int index;

            @Override
            public int describeContents() {
                return 0;
            }

            @Override
            public void writeToParcel(Parcel dest, int flags) {
                dest.writeString(this.address);
                dest.writeDouble(this.latitude);
                dest.writeDouble(this.longitude);
                dest.writeInt(this.index);
            }

            public GeoEncodes() {
            }

            protected GeoEncodes(Parcel in) {
                this.address = in.readString();
                this.latitude = in.readDouble();
                this.longitude = in.readDouble();
                this.index = in.readInt();
            }

            public static final Creator<GeoEncodes> CREATOR = new Creator<GeoEncodes>() {
                @Override
                public GeoEncodes createFromParcel(Parcel source) {
                    return new GeoEncodes(source);
                }

                @Override
                public GeoEncodes[] newArray(int size) {
                    return new GeoEncodes[size];
                }
            };
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.jobid);
            dest.writeString(this.customer_name);
            dest.writeString(this.orderid);
            dest.writeString(this.pickup_date);
            dest.writeString(this.pickup_unitno);
            dest.writeString(this.pickup_address);
            dest.writeString(this.pickup_phone);
            dest.writeString(this.drop_unitno);
            dest.writeString(this.drop_address);
            dest.writeString(this.drop_phone);
            dest.writeList(this.additional_location);
            dest.writeString(this.pickup_time);
            dest.writeString(this.comment);
            dest.writeString(this.item_comment);
            dest.writeList(this.products);
            dest.writeString(this.total_amount);
            dest.writeString(this.paid_amount);
            dest.writeString(this.tobe_paid_amount);
            dest.writeInt(this.job_status);
            dest.writeString(this.image);
            dest.writeString(this.job_token);
            dest.writeList(this.geo_encodes);
        }

        public Data() {
        }

        protected Data(Parcel in) {
            this.jobid = in.readString();
            this.customer_name = in.readString();
            this.orderid = in.readString();
            this.pickup_date = in.readString();
            this.pickup_unitno = in.readString();
            this.pickup_address = in.readString();
            this.pickup_phone = in.readString();
            this.drop_unitno = in.readString();
            this.drop_address = in.readString();
            this.drop_phone = in.readString();
            this.additional_location = new ArrayList<AdditionalLocation>();
            in.readList(this.additional_location, AdditionalLocation.class.getClassLoader());
            this.pickup_time = in.readString();
            this.comment = in.readString();
            this.item_comment = in.readString();
            this.products = new ArrayList<Product>();
            in.readList(this.products, Product.class.getClassLoader());
            this.total_amount = in.readString();
            this.paid_amount = in.readString();
            this.tobe_paid_amount = in.readString();
            this.job_status = in.readInt();
            this.image = in.readString();
            this.job_token = in.readString();
            this.geo_encodes = new ArrayList<GeoEncodes>();
            in.readList(this.geo_encodes, GeoEncodes.class.getClassLoader());
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.status);
        dest.writeParcelable(this.data, flags);
    }

    public JobDescriptionRespons() {
    }

    protected JobDescriptionRespons(Parcel in) {
        this.status = in.readInt();
        this.data = in.readParcelable(Data.class.getClassLoader());
    }

    public static final Parcelable.Creator<JobDescriptionRespons> CREATOR = new Parcelable.Creator<JobDescriptionRespons>() {
        @Override
        public JobDescriptionRespons createFromParcel(Parcel source) {
            return new JobDescriptionRespons(source);
        }

        @Override
        public JobDescriptionRespons[] newArray(int size) {
            return new JobDescriptionRespons[size];
        }
    };
}