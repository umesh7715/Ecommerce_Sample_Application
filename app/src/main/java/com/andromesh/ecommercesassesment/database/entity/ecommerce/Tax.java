package com.andromesh.ecommercesassesment.database.entity.ecommerce;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import androidx.room.PrimaryKey;

public class Tax {

    @PrimaryKey
    @SerializedName("name")
    @Expose
    private String taxName;
    @SerializedName("value")
    @Expose
    private double value;


    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public String getTaxName() {
        return taxName;
    }

    public void setTaxName(String taxName) {
        this.taxName = taxName;
    }
}
