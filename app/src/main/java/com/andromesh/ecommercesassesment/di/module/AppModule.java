package com.andromesh.ecommercesassesment.di.module;


import android.app.Application;

import com.andromesh.ecommercesassesment.api.ECommerceServiceAPI;
import com.andromesh.ecommercesassesment.database.MyDatabase;
import com.andromesh.ecommercesassesment.database.dao.ECommerceDao;
import com.andromesh.ecommercesassesment.executors.AppExecutors;
import com.andromesh.ecommercesassesment.repositories.ECommerceServiceRepository;
import com.andromesh.ecommercesassesment.utils.LiveDataCallAdapterFactory;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import javax.inject.Singleton;

import androidx.room.Room;
import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Module(includes = ViewModelModule.class)
public class AppModule {

    // --- DATABASE INJECTION ---


    @Provides
    @Singleton
    MyDatabase provideDatabase(Application application) {
        return Room.databaseBuilder(application,
                MyDatabase.class, "MyDatabase.db")
                .build();
    }


    // --- REPOSITORY INJECTION ---
    @Provides
    @Singleton
    ECommerceDao provideUserDao(MyDatabase database) {
        return database.eCommerceDao();
    }


    @Provides
    Executor provideExecutor() {
        return Executors.newSingleThreadExecutor();
    }

    // --- NETWORK INJECTION ---
    @Provides
    @Singleton
    ECommerceServiceRepository provideUserRepository(ECommerceServiceAPI webservice, ECommerceDao userDao, AppExecutors executor, ExampleIntercepter exampleIntercepter) {
        return new ECommerceServiceRepository(webservice, userDao, executor, exampleIntercepter);
    }


    @Provides
    @Singleton
    ExampleIntercepter provideInterceptor() {
        return ExampleIntercepter.getIntercepter().get();
    }


    @Provides
    @Singleton
    OkHttpClient provideOkHttpClient(ExampleIntercepter interceptor) {
        return new OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .build();
    }

    @Provides
    Gson provideGson() {
        return new GsonBuilder().create();
    }

    @Provides
    Retrofit provideRetrofit(Gson gson, OkHttpClient okHttpClient) {
        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(new LiveDataCallAdapterFactory())
                .client(okHttpClient)
                .baseUrl("http://localhost/")
                .build();
        return retrofit;
    }


    @Provides
    @Singleton
    ECommerceServiceAPI provideApiWebserviceForECommerceService(Retrofit restAdapter) {
        return restAdapter.create(ECommerceServiceAPI.class);
    }

}
