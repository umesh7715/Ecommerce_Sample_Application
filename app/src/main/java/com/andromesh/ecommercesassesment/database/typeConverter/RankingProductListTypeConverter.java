package com.andromesh.ecommercesassesment.database.typeConverter;


import com.andromesh.ecommercesassesment.database.entity.ecommerce.RankingProduct;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.Collections;
import java.util.List;

import androidx.room.TypeConverter;

public class RankingProductListTypeConverter {

    static Gson gson = new Gson();

    @TypeConverter
    public static List<RankingProduct> stringToSomeObjectList(String data) {
        if (data == null) {
            return Collections.emptyList();
        }

        Type listType = new TypeToken<List<RankingProduct>>() {
        }.getType();

        return gson.fromJson(data, listType);
    }

    @TypeConverter
    public static String someObjectListToString(List<RankingProduct> someObjects) {
        return gson.toJson(someObjects);
    }
}