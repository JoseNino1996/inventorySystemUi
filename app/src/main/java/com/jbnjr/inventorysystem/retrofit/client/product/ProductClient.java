package com.jbnjr.inventorysystem.retrofit.client.product;

import com.jbnjr.inventorysystem.api.product.ProductApi;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ProductClient {


    private static final String BASE_URL = "http://192.168.137.1:8085/api/product/";
    private static ProductClient productClient;
    private Retrofit retrofit;

    private ProductClient() {
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }


    public static synchronized ProductClient getRetrofitClient() {
        if(productClient == null) {
            productClient = new ProductClient();
        }
        return  productClient;
    }

    public ProductApi getProductApi() {
        return retrofit.create(ProductApi.class);
    }

}
