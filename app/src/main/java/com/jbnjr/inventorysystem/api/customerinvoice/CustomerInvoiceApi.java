package com.jbnjr.inventorysystem.api.customerinvoice;

import com.jbnjr.inventorysystem.model.customerinvoice.CustomerInvoice;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface CustomerInvoiceApi {


    @POST("create")
    Call<CustomerInvoice> create(@Body CustomerInvoice customerInvoice);

    @GET("findAll")
    Call<List<CustomerInvoice>> findAll();

    @GET("findById")
    Call<CustomerInvoice> findByid(@Query("id") long id);

    @DELETE("deleteById")
    Call<CustomerInvoice> deleteById(@Query("id") long id);

    @POST("update")
    Call<CustomerInvoice> update(@Body CustomerInvoice customerInvoice);


}
