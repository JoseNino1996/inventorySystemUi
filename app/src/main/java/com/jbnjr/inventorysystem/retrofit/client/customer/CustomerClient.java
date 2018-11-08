package com.jbnjr.inventorysystem.retrofit.client.customer;

import com.jbnjr.inventorysystem.api.customer.CustomerApi;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CustomerClient {

    private static final String BASE_URL = "http://192.168.137.1:8085/api/customer/";
    private static CustomerClient retrofitClient;
    private Retrofit retrofit;

    private CustomerClient() {
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }



    public static synchronized CustomerClient getRetrofitClient() {
        if(retrofitClient == null) {
            retrofitClient = new CustomerClient();
        }
        return  retrofitClient;
    }

    public  CustomerApi getCustomerApi() {
        return retrofit.create(CustomerApi.class);
    }

}
