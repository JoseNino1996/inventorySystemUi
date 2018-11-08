package com.jbnjr.inventorysystem.retrofit.client.customerinvoice;

import com.jbnjr.inventorysystem.api.customerinvoice.CustomerInvoiceApi;
import com.jbnjr.inventorysystem.retrofit.client.customer.CustomerClient;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CustomerInvoiceClient {


    private static final String BASE_URL = "http://192.168.137.1:8085/api/customer-invoice/";
    private static CustomerInvoiceClient retrofitClient;
    private Retrofit retrofit;

    private CustomerInvoiceClient() {
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }



    public static synchronized CustomerInvoiceClient getRetrofitClient() {
        if(retrofitClient == null) {
            retrofitClient = new CustomerInvoiceClient();
        }
        return  retrofitClient;
    }

    public CustomerInvoiceApi getCustomerInvoiceApi() {
        return retrofit.create(CustomerInvoiceApi.class);
    }
}
