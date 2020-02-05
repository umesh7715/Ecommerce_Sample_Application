package com.andromesh.ecommercesassesment.database.typeConverter;



import com.andromesh.ecommercesassesment.database.entity.ecommerce.Product;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.Collections;
import java.util.List;

import androidx.room.TypeConverter;

public class ProductListTypeConverter {

    static Gson gson = new Gson();

    @TypeConverter
    public static List<Product> stringToSomeObjectList(String data) {
        if (data == null) {
            return Collections.emptyList();
        }

        Type listType = new TypeToken<List<Product>>() {
        }.getType();

        return gson.fromJson(data, listType);
    }

    @TypeConverter
    public static String someObjectListToString(List<Product> someObjects) {
        return gson.toJson(someObjects);
    }
}
