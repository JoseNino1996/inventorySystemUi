package com.jbnjr.inventorysystem.activity.stock;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.jbnjr.inventorysystem.R;
import com.jbnjr.inventorysystem.api.product.ProductApi;
import com.jbnjr.inventorysystem.api.stock.ProductInventoryApi;
import com.jbnjr.inventorysystem.model.stock.ProductInventory;
import com.jbnjr.inventorysystem.retrofit.client.stock.ProductInventoryClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StockInActivity extends AppCompatActivity {

    private ProductInventory productInventory;

    private TextView txtName, txtQuantity, txtViewStockQuantity;

    private Button btnAddStock;

    private ProductInventoryApi productInventoryApi;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stock_in);


        txtName = findViewById(R.id.txtStockProductName);
        txtQuantity = findViewById(R.id.txtQuantity);

        txtViewStockQuantity = findViewById(R.id.txtStockQuantity);
        btnAddStock = findViewById(R.id.btnAddQuantity);

    }

    @Override
    protected  void onResume() {
        super.onResume();
        productInventoryApi = ProductInventoryClient.getRetrofitClient().getProductInventoryApi();
        Intent intent = getIntent();

        productInventory = intent.getParcelableExtra("selectedProduct");

        txtName.setText("Product Name: "+ productInventory.getProduct().getName());
        txtViewStockQuantity.setText("Quantity: "+ Long.toString(productInventory.getQuantity()));


        btnAddStock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(txtQuantity.getText().toString() == "") {
                    txtQuantity.setError("Enter a quantity!");
                    return;
                }
                productInventory.setQuantity(Long.parseLong(txtQuantity.getText().toString()));

                Call<ProductInventory> call = productInventoryApi.addStock(productInventory);

                call.enqueue(new Callback<ProductInventory>() {
                    @Override
                    public void onResponse(Call<ProductInventory> call, Response<ProductInventory> response) {
                        Toast.makeText(StockInActivity.this,"Stock added!", Toast.LENGTH_LONG).show();

                        reLoadProductInventory(productInventory.getId());
                    }

                    @Override
                    public void onFailure(Call<ProductInventory> call, Throwable t) {

                    }
                });
            }
        });
    }


    public void reLoadProductInventory(long id) {
        Call<ProductInventory> call = productInventoryApi.findById(id);

        call.enqueue(new Callback<ProductInventory>() {
            @Override
            public void onResponse(Call<ProductInventory> call, Response<ProductInventory> response) {
                ProductInventory productInventory = response.body();

                txtName.setText("Product Name: " +productInventory.getProduct().getName());
                txtQuantity.clearFocus();
                txtViewStockQuantity.setText("Quantity: "+ Long.toString(productInventory.getQuantity()));

            }

            @Override
            public void onFailure(Call<ProductInventory> call, Throwable t) {
                Toast.makeText(StockInActivity.this,t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

    }
}
