package com.andromesh.ecommercesassesment.api;



import com.andromesh.ecommercesassesment.database.entity.ecommerce.ECommerceResponse;
import com.andromesh.ecommercesassesment.networkBoundResource.ApiResponse;

import androidx.lifecycle.LiveData;
import retrofit2.http.GET;


public interface ECommerceServiceAPI {

    @GET("/json")
    LiveData<ApiResponse<ECommerceResponse>> getProductsAndCategories();
}
