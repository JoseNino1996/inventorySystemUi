package com.jbnjr.inventorysystem.api.customer;

import android.os.Parcel;
import android.os.Parcelable;

import com.jbnjr.inventorysystem.model.customer.Customer;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface CustomerApi  {

    @GET("findAll")
    Call<List<Customer>> findAll();

    @POST("create")
    Call<Customer> create(@Body Customer customer);


    @DELETE("delete")
    Call<Customer> delete(@Query("id") Long id);

    @POST("update")
    Call<Customer> update(@Body Customer customer);

    @GET("findById")
    Call<Customer> findById(@Query("id") long id);


}
