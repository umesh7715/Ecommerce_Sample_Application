package com.andromesh.ecommercesassesment.database;


import com.andromesh.ecommercesassesment.database.converter.DateConverter;
import com.andromesh.ecommercesassesment.database.dao.ECommerceDao;
import com.andromesh.ecommercesassesment.database.entity.ecommerce.Category;
import com.andromesh.ecommercesassesment.database.entity.ecommerce.Product;
import com.andromesh.ecommercesassesment.database.entity.ecommerce.Ranking;
import com.andromesh.ecommercesassesment.database.entity.ecommerce.RankingProduct;
import com.andromesh.ecommercesassesment.database.entity.ecommerce.Variant;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

@Database(entities = {Category.class, Product.class, Variant.class, Ranking.class, RankingProduct.class}, version = 1,exportSchema = false)
@TypeConverters(DateConverter.class)
public abstract class MyDatabase extends RoomDatabase {

    // --- SINGLETON ---
    public static volatile MyDatabase INSTANCE;

    // --- DAO ---
    public abstract ECommerceDao eCommerceDao();
}
