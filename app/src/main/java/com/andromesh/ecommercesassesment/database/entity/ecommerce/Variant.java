package com.andromesh.ecommercesassesment.database.entity.ecommerce;


import android.os.Parcel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Variant implements android.os.Parcelable {

    @PrimaryKey
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("color")
    @Expose
    private String color;
    @SerializedName("size")
    @Expose
    private int size;
    @SerializedName("price")
    @Expose
    private Integer price;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.id);
        dest.writeString(this.color);
        dest.writeInt(this.size);
        dest.writeValue(this.price);
    }

    public Variant() {
    }

    protected Variant(Parcel in) {
        this.id = (Integer) in.readValue(Integer.class.getClassLoader());
        this.color = in.readString();
        this.size = in.readInt();
        this.price = (Integer) in.readValue(Integer.class.getClassLoader());
    }

    public static final Creator<Variant> CREATOR = new Creator<Variant>() {
        @Override
        public Variant createFromParcel(Parcel source) {
            return new Variant(source);
        }

        @Override
        public Variant[] newArray(int size) {
            return new Variant[size];
        }
    };
}
