package com.jbnjr.inventorysystem.api.product;

import com.jbnjr.inventorysystem.model.product.Product;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ProductApi {

    @POST("create")
    Call<Product> create(@Body Product product);

    @GET("findById")
    Call<Product> findById(@Query("id") Long id);

    @GET("findAll")
    Call<List<Product>> findAll();

    @POST("update")
    Call<Product> update(@Body Product product);

}
