package com.jbnjr.inventorysystem.activity.product;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.jbnjr.inventorysystem.R;
import com.jbnjr.inventorysystem.activity.stock.StockInActivity;
import com.jbnjr.inventorysystem.adapters.stock.ProductInventoryListAdapter;
import com.jbnjr.inventorysystem.api.product.ProductApi;
import com.jbnjr.inventorysystem.api.stock.ProductInventoryApi;
import com.jbnjr.inventorysystem.model.product.Product;
import com.jbnjr.inventorysystem.model.stock.ProductInventory;
import com.jbnjr.inventorysystem.retrofit.client.stock.ProductInventoryClient;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductActivity  extends AppCompatActivity  {

    private ListView listViewProduct;
    private ProductInventoryListAdapter productListAdapter;
    private ProductInventory selectedProductInventory;
    private Intent intent;
    private Button  btnAddStock, btnUpdate, btnCreate;

    private ProductInventoryApi productInventoryApi;
    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);
        listViewProduct = findViewById(R.id.lvProducts);

        btnAddStock = findViewById(R.id.btnAddStock);

        btnUpdate = findViewById(R.id.btnUpdateProduct);
        btnCreate = findViewById(R.id.btnCreateProduct);
    }

    @Override
    protected  void onResume() {
        super.onResume();


        loadProducts();

        listViewProduct.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectedProductInventory = productListAdapter.getItem(position);

            }
        });


        btnCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                intent = new Intent(ProductActivity.this, ModifyProductActivity.class);
                startActivity(intent);

            }
        });

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(selectedProductInventory == null) {
                    Toast.makeText(ProductActivity.this, "Select a product first!", Toast.LENGTH_LONG).show();
                    return;
                }
                intent = new Intent (ProductActivity.this, ModifyProductActivity.class);
                intent.putExtra("selectedProduct", selectedProductInventory);
                startActivity(intent);
            }
        });

        btnAddStock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(selectedProductInventory == null) {
                    Toast.makeText(ProductActivity.this, "Select a product first!", Toast.LENGTH_LONG).show();
                    return;
                }
                intent = new Intent (ProductActivity.this, StockInActivity.class);
                intent.putExtra("selectedProduct", selectedProductInventory);
                startActivity(intent);
            }
        });
    }


    private void loadProducts() {

        productInventoryApi  = ProductInventoryClient.getRetrofitClient().getProductInventoryApi();

        Call<List<ProductInventory>> call = productInventoryApi.findAll();

        call.enqueue(new Callback<List<ProductInventory>>() {
            @Override
            public void onResponse(Call<List<ProductInventory>> call, Response<List<ProductInventory>> response) {
                List<ProductInventory> productInventories = response.body();
                if(productInventories.size() < 1) { return; }
                productListAdapter = new ProductInventoryListAdapter(ProductActivity.this, R.layout.activity_productlist_adapter,productInventories);

                listViewProduct.setAdapter(productListAdapter);
            }

            @Override
            public void onFailure(Call<List<ProductInventory>> call, Throwable t) {
                Toast.makeText(ProductActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }



}
