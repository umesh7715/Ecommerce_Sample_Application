package com.andromesh.ecommercesassesment.repositories;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;

import com.andromesh.ecommercesassesment.api.ECommerceServiceAPI;
import com.andromesh.ecommercesassesment.bindingUtils.interfaceCallback.ExecutorCallbackForProduct;
import com.andromesh.ecommercesassesment.database.dao.ECommerceDao;
import com.andromesh.ecommercesassesment.database.entity.ecommerce.Category;
import com.andromesh.ecommercesassesment.database.entity.ecommerce.ECommerceResponse;
import com.andromesh.ecommercesassesment.database.entity.ecommerce.Product;
import com.andromesh.ecommercesassesment.database.entity.ecommerce.Ranking;
import com.andromesh.ecommercesassesment.database.entity.ecommerce.RankingProduct;
import com.andromesh.ecommercesassesment.di.module.ExampleIntercepter;
import com.andromesh.ecommercesassesment.executors.AppExecutors;
import com.andromesh.ecommercesassesment.networkBoundResource.ApiResponse;
import com.andromesh.ecommercesassesment.networkBoundResource.NetworkBoundResource;
import com.andromesh.ecommercesassesment.networkBoundResource.Resource;
import com.andromesh.ecommercesassesment.utils.Constants.SortTypes;
import com.andromesh.ecommercesassesment.utils.RateLimiter;

import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

public class ECommerceServiceRepository {

    private static final int FRESH_TIMEOUT_IN_MINUTES = 2;
    private static String BASE_URL = "https://stark-spire-93433.herokuapp.com/";
    private final ECommerceServiceAPI webservice;
    private final AppExecutors executor;
    private final ExampleIntercepter exampleIntercepter;
    private RateLimiter<String> repoListRateLimit = new RateLimiter<>(FRESH_TIMEOUT_IN_MINUTES, TimeUnit.MINUTES);
    private ECommerceDao eCommerceDao;


    @Inject
    public ECommerceServiceRepository(ECommerceServiceAPI webservice, ECommerceDao userDao, AppExecutors executor, ExampleIntercepter exampleIntercepter) {
        this.webservice = webservice;
        this.executor = executor;
        this.exampleIntercepter = exampleIntercepter;
        this.eCommerceDao = userDao;
        setBaseURL();
    }

    private void setBaseURL() {
        exampleIntercepter.setInterceptor(BASE_URL);
    }

    public LiveData<ApiResponse<ECommerceResponse>> getCategoriesAndProducts() {
        return webservice.getProductsAndCategories();
    }

    public LiveData<Resource<List<Category>>> getPostsWithDatabaseResource(String owner) {
        return new NetworkBoundResource<List<Category>, ECommerceResponse>(executor, true) {
            @Override
            protected void saveCallResult(@NonNull ECommerceResponse item) {
                eCommerceDao.insertCategories(item.getCategories());

                for (Ranking ranking : item.getRankings()) {
                    for (RankingProduct rankingProduct : ranking.getRankingProducts()) {

                        RankingProduct tempRankingProduct = eCommerceDao.getProductRanking(rankingProduct.getId());

                        if (null == tempRankingProduct) {
                            eCommerceDao.insertRankingProducts(rankingProduct);
                        } else {

                            if (null != rankingProduct.getViewCount()) {
                                tempRankingProduct.setViewCount(rankingProduct.getViewCount());
                            } else if (null != rankingProduct.getOrderCount()) {
                                tempRankingProduct.setOrderCount(rankingProduct.getOrderCount());
                            } else if (null != rankingProduct.getShares()) {
                                tempRankingProduct.setShares(rankingProduct.getShares());
                            }

                            eCommerceDao.updateProductRanking(tempRankingProduct);

                        }

                    }
                }

                Product product;

                for (Category category : item.getCategories()) {
                    for (Product product1 : category.getProducts()) {
                        product = product1;
                        product.setCategoryId(category.getId());
                        eCommerceDao.inertProduct(product);
                    }

                }

            }

            @Override
            protected boolean shouldFetch(@Nullable List<Category> data) {
                return data == null || data.size() == 0 || repoListRateLimit.shouldFetch(owner);
            }

            @Override
            protected LiveData<List<Category>> performDatabaseOperation() {
                return eCommerceDao.getAllCategories();
            }

            @NonNull
            @Override
            protected LiveData<ApiResponse<ECommerceResponse>> createCall() {
                return webservice.getProductsAndCategories();
            }

            @Override
            protected void onFetchFailed() {
                repoListRateLimit.reset(owner);
            }
        }.asLiveData();
    }

    public LiveData<Resource<List<Product>>> getProducts(String owner, int categoryId, String sortType) {
        return new NetworkBoundResource<List<Product>, ECommerceResponse>(executor, true) {
            @Override
            protected void saveCallResult(@NonNull ECommerceResponse item) {

            }

            @Override
            protected boolean shouldFetch(@Nullable List<Product> data) {
                return false;
            }

            @Override
            protected LiveData<List<Product>> performDatabaseOperation() {

                switch (sortType) {
                    case SortTypes.MOST_ORDERED:
                        return eCommerceDao.getProductsByOrderCountParameter(categoryId);

                    case SortTypes.MOST_VIEWED:
                        return eCommerceDao.getProductsByViewsParameter(categoryId);

                    case SortTypes.MOST_SHARED:
                        return eCommerceDao.getProductsByShareParameter(categoryId);
                }

                return null;


            }

            @NonNull
            @Override
            protected LiveData<ApiResponse<ECommerceResponse>> createCall() {
                return null;
            }

            @Override
            protected void onFetchFailed() {
                repoListRateLimit.reset(owner);
            }
        }.asLiveData();
    }


    public void getProduct(Integer id, ExecutorCallbackForProduct executorCallback) {


        executor.diskIO().execute(() -> {
            executorCallback.fromDiskIO(eCommerceDao.getProductRanking(id));
        });

    }
}
