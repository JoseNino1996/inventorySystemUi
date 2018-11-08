package com.jbnjr.inventorysystem.api.customerinvoice;

import com.jbnjr.inventorysystem.model.customerinvoice.CustomerInvoice;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface CustomerInvoiceApi {


    @POST("create")
    Call<CustomerInvoice> create(@Body CustomerInvoice customerInvoice);

}
