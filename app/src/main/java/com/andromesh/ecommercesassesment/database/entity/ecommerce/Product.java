package com.andromesh.ecommercesassesment.database.entity.ecommerce;

import android.os.Parcel;
import android.os.Parcelable;

import com.andromesh.ecommercesassesment.database.typeConverter.VariantListTypeConverter;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

@Entity
public class Product implements Parcelable {

    @PrimaryKey
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("date_added")
    @Expose
    private String dateAdded;
    @SerializedName("variants")
    @Expose
    @TypeConverters(VariantListTypeConverter.class)
    private List<Variant> variants = null;
    @SerializedName("view_count")
    @Expose
    private Integer viewCount;
    @SerializedName("order_count")
    @Expose
    private Integer orderCount;
    @SerializedName("shares")
    @Expose
    private Integer shares;

    private int categoryId;

    public Product() {
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

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDateAdded() {
        return dateAdded;
    }

    public void setDateAdded(String dateAdded) {
        this.dateAdded = dateAdded;
    }

    public List<Variant> getVariants() {
        return variants;
    }

    public void setVariants(List<Variant> variants) {
        this.variants = variants;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.id);
        dest.writeString(this.name);
        dest.writeString(this.dateAdded);
        dest.writeTypedList(this.variants);
        dest.writeValue(this.viewCount);
        dest.writeValue(this.orderCount);
        dest.writeValue(this.shares);
        dest.writeInt(this.categoryId);
    }

    protected Product(Parcel in) {
        this.id = (Integer) in.readValue(Integer.class.getClassLoader());
        this.name = in.readString();
        this.dateAdded = in.readString();
        this.variants = in.createTypedArrayList(Variant.CREATOR);
        this.viewCount = (Integer) in.readValue(Integer.class.getClassLoader());
        this.orderCount = (Integer) in.readValue(Integer.class.getClassLoader());
        this.shares = (Integer) in.readValue(Integer.class.getClassLoader());
        this.categoryId = in.readInt();
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
