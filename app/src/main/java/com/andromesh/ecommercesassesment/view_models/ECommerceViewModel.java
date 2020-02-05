package com.andromesh.ecommercesassesment.view_models;


import com.andromesh.ecommercesassesment.database.entity.ecommerce.Category;
import com.andromesh.ecommercesassesment.database.entity.ecommerce.Product;
import com.andromesh.ecommercesassesment.networkBoundResource.Resource;
import com.andromesh.ecommercesassesment.repositories.ECommerceServiceRepository;

import java.util.List;

import javax.inject.Inject;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

public class ECommerceViewModel extends ViewModel {

    private ECommerceServiceRepository eCommerceServiceRepository;

    @Inject
    public ECommerceViewModel(ECommerceServiceRepository eCommerceServiceRepository) {
        this.eCommerceServiceRepository = eCommerceServiceRepository;
    }

    public LiveData<Resource<List<Category>>> getCategoriesAndProductWithoutDatabaseResource() {
        return eCommerceServiceRepository.getPostsWithDatabaseResource("Category");
    }

    public LiveData<Resource<List<Product>>> getProducts(Integer id, String sortType) {
        return eCommerceServiceRepository.getProducts("Category", id,sortType);
    }
}

