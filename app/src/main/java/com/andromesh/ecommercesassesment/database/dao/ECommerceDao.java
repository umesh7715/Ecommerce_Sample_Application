package com.andromesh.ecommercesassesment.database.dao;


import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.RoomWarnings;
import androidx.room.Update;

import com.andromesh.ecommercesassesment.database.entity.ecommerce.Category;
import com.andromesh.ecommercesassesment.database.entity.ecommerce.Product;
import com.andromesh.ecommercesassesment.database.entity.ecommerce.RankingProduct;

import java.util.List;

import static androidx.room.OnConflictStrategy.REPLACE;

@Dao
public interface ECommerceDao {

    @Insert(onConflict = REPLACE)
    void insertCategories(Category post);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public abstract void insertPosts(List<Category> posts);

    @Insert(onConflict = REPLACE)
    void insertCategories(List<Category> countryModel);

    @SuppressWarnings(RoomWarnings.CURSOR_MISMATCH)
    @Query("SELECT category.name,category.id FROM category")
    LiveData<List<Category>> getAllCategories();

    @SuppressWarnings(RoomWarnings.CURSOR_MISMATCH)
    @Query("SELECT Product.id,Product.dateAdded,Product.name,Product.categoryId,Product.variants,RankingProduct.viewCount FROM Product " +
            "LEFT JOIN RankingProduct ON Product.id = RankingProduct.id\n" +
            "WHERE product.categoryId = :categoryid\n" +
            "ORDER BY RankingProduct.viewCount DESC")
    LiveData<List<Product>> getProductsByViewsParameter(int categoryid);

    @SuppressWarnings(RoomWarnings.CURSOR_MISMATCH)
    @Query("SELECT Product.id,Product.dateAdded,Product.name,Product.categoryId,Product.variants,RankingProduct.shares FROM Product " +
            "LEFT JOIN RankingProduct ON Product.id = RankingProduct.id\n" +
            "WHERE product.categoryId = :categoryid\n" +
            "ORDER BY RankingProduct.shares DESC")
    LiveData<List<Product>> getProductsByShareParameter(int categoryid);

    @SuppressWarnings(RoomWarnings.CURSOR_MISMATCH)
    @Query("SELECT Product.id,Product.dateAdded,Product.name,Product.categoryId,Product.variants,RankingProduct.orderCount FROM Product " +
            "LEFT JOIN RankingProduct ON Product.id = RankingProduct.id\n" +
            "WHERE product.categoryId = :categoryid\n" +
            "ORDER BY RankingProduct.orderCount DESC")
    LiveData<List<Product>> getProductsByOrderCountParameter(int categoryid);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertRankingProducts(RankingProduct rankingProduct);

    @Query("SELECT * FROM RankingProduct where id = :productId")
    RankingProduct getProductRanking(int productId);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void inertProduct(Product product);

    @Update
    void updateProductRanking(RankingProduct tempRankingProduct);
}