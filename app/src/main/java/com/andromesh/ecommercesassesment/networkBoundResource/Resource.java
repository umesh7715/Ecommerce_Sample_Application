package com.andromesh.ecommercesassesment.networkBoundResource;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import static com.andromesh.ecommercesassesment.networkBoundResource.Resource.Status.ERROR;
import static com.andromesh.ecommercesassesment.networkBoundResource.Resource.Status.LOADING;
import static com.andromesh.ecommercesassesment.networkBoundResource.Resource.Status.SUCCESS;

public class Resource<T> {
    @NonNull
    public final int status;
    @Nullable
    public final T data;
    @Nullable
    public final String message;

    public Resource(@NonNull int status, @Nullable T data, @Nullable String message) {
        this.status = status;
        this.data = data;
        this.message = message;
    }

    public static <T> Resource<T> success(@NonNull T data) {
        return new Resource<>(SUCCESS, data, null);
    }

    public static <T> Resource<T> error(String msg, @Nullable T data) {
        return new Resource<>(ERROR, data, msg);
    }

    public static <T> Resource<T> loading(@Nullable T data) {
        return new Resource<>(LOADING, data, null);
    }

    public interface Status {

        int SUCCESS = 100;
        int ERROR = 101;
        int LOADING = 102;
    }


}
