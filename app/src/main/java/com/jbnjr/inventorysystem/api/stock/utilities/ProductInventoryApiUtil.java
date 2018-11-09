package com.jbnjr.inventorysystem.api.stock.utilities;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;
import android.widget.Toast;

import com.jbnjr.inventorysystem.adapters.stock.ProductInventoryListAdapter;
import com.jbnjr.inventorysystem.api.stock.ProductInventoryApi;
import com.jbnjr.inventorysystem.model.stock.ProductInventory;
import com.jbnjr.inventorysystem.retrofit.client.stock.ProductInventoryClient;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductInventoryApiUtil extends AppCompatActivity {

    private ProductInventoryApi productInventoryApi;
     private  List<ProductInventory> productInventoriess ;
    public  ProductInventoryApiUtil() {
        productInventoriess = new ArrayList<>();
    }
    public List<ProductInventory> loadProducts()  {

        ProductInventoryApi api = ProductInventoryClient.getRetrofitClient().getProductInventoryApi();

        retrofit2.Call<List<ProductInventory>> call = api.findAll();

        call.enqueue(new Callback<List<ProductInventory>>() {
            @Override
            public void onResponse(Call<List<ProductInventory>> call, Response<List<ProductInventory>> response) {
                 List<ProductInventory>   productInventories = response.body();
               for(ProductInventory productInventory : productInventories) {
                     productInventoriess.add(productInventory);
               }
            }

            @Override
            public void onFailure(Call<List<ProductInventory>> call, Throwable t) {

            }
        });

        return  productInventoriess;
    }
//
//    public ProductInventoryListAdapter getProductInventoryListAdapter(Context context, int resource, List<ProductInventory> productInventories) {
//             productListAdapter = new ProductInventoryListAdapter(context, resource,productInventories);
//
//           return  productListAdapter ;
//    }

}
