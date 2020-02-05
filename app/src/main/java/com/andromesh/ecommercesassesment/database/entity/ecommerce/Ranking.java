package com.andromesh.ecommercesassesment.database.entity.ecommerce;


import com.andromesh.ecommercesassesment.database.typeConverter.RankingProductListTypeConverter;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

@Entity
public class Ranking {

    @PrimaryKey
    @NotNull
    @SerializedName("ranking")
    @Expose
    private String ranking;
    @SerializedName("products")
    @Expose
    @TypeConverters(RankingProductListTypeConverter.class)
    private List<RankingProduct> rankingProducts = null;

    public String getRanking() {
        return ranking;
    }

    public void setRanking(String ranking) {
        this.ranking = ranking;
    }

    public List<RankingProduct> getRankingProducts() {
        return rankingProducts;
    }

    public void setRankingProducts(List<RankingProduct> rankingProducts) {
        this.rankingProducts = rankingProducts;
    }
}
