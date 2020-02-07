package com.andromesh.ecommercesassesment.database.entity.ecommerce;


import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Entity
public class RankingProduct implements Parcelable {

    public static final Creator<RankingProduct> CREATOR = new Creator<RankingProduct>() {
        @Override
        public RankingProduct createFromParcel(Parcel in) {
            return new RankingProduct(in);
        }

        @Override
        public RankingProduct[] newArray(int size) {
            return new RankingProduct[size];
        }
    };
    @PrimaryKey
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("view_count")
    @Expose
    private Integer viewCount;
    @SerializedName("order_count")
    @Expose
    private Integer orderCount;
    @SerializedName("shares")
    @Expose
    private Integer shares;


    public RankingProduct() {

    }

    protected RankingProduct(Parcel in) {
        if (in.readByte() == 0) {
            id = null;
        } else {
            id = in.readInt();
        }
        if (in.readByte() == 0) {
            viewCount = null;
        } else {
            viewCount = in.readInt();
        }
        if (in.readByte() == 0) {
            orderCount = null;
        } else {
            orderCount = in.readInt();
        }
        if (in.readByte() == 0) {
            shares = null;
        } else {
            shares = in.readInt();
        }
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        if (id == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(id);
        }
        if (viewCount == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(viewCount);
        }
        if (orderCount == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(orderCount);
        }
        if (shares == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(shares);
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getViewCount() {
        return viewCount;
    }

    public void setViewCount(Integer viewCount) {
        this.viewCount = viewCount;
    }

    public Integer getOrderCount() {
        return orderCount;
    }

    public void setOrderCount(Integer orderCount) {
        this.orderCount = orderCount;
    }

    public Integer getShares() {
        return shares;
    }

    public void setShares(Integer shares) {
        this.shares = shares;
    }

}
