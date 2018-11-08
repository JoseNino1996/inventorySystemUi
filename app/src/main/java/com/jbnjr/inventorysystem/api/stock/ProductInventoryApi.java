package com.jbnjr.inventorysystem.api.stock;

import com.jbnjr.inventorysystem.model.stock.ProductInventory;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ProductInventoryApi {

    @POST("addStock")
    Call<ProductInventory> addStock(@Body ProductInventory productInventory);

    @GET("findById")
    Call<ProductInventory> findById(@Query("id") long id);


    @POST("create")
    Call<ProductInventory> create(@Body ProductInventory productInventory);

    @GET("findAll")
    Call<List<ProductInventory>> findAll();

    @POST("update")
    Call<ProductInventory> update(@Body ProductInventory productInventory);

}
