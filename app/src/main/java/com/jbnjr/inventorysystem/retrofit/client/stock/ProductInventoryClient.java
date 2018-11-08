package com.jbnjr.inventorysystem.retrofit.client.stock;

import com.jbnjr.inventorysystem.api.stock.ProductInventoryApi;
import com.jbnjr.inventorysystem.model.stock.ProductInventory;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ProductInventoryClient {
    private static final String BASE_URL = "http://192.168.137.1:8085/api/product-inventory/";
    private static ProductInventoryClient retrofitClient;
    private Retrofit retrofit;


    private ProductInventoryClient() {
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public  static  synchronized ProductInventoryClient getRetrofitClient () {
        if(retrofitClient == null) {
            retrofitClient = new ProductInventoryClient();
        }
        return retrofitClient;
    }

    public ProductInventoryApi getProductInventoryApi() {
        return retrofit.create(ProductInventoryApi.class);
    }
}
