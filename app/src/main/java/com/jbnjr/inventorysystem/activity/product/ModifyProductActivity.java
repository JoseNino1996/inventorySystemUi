package com.jbnjr.inventorysystem.activity.product;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.jbnjr.inventorysystem.R;
import com.jbnjr.inventorysystem.api.product.ProductApi;
import com.jbnjr.inventorysystem.api.stock.ProductInventoryApi;
import com.jbnjr.inventorysystem.model.product.Product;
import com.jbnjr.inventorysystem.model.stock.ProductInventory;
import com.jbnjr.inventorysystem.retrofit.client.product.ProductClient;
import com.jbnjr.inventorysystem.retrofit.client.stock.ProductInventoryClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ModifyProductActivity extends AppCompatActivity {
    private ProductInventory selecteProductInventory;
    private EditText txtProductName, txtPrice, txxtModifyProductQuantity;
    private Button btnSave;
    private boolean isUpdate;
    private ProductApi productApi;

   private ProductInventory productInventory ;

    private ProductInventoryApi productInventoryApi;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_product);

        txtProductName = findViewById(R.id.txtModifyProductName);
        txtPrice = findViewById(R.id.txtModifyPrice);
        txxtModifyProductQuantity = findViewById(R.id.txttxtModifyProductQuantity);
        btnSave = findViewById(R.id.btnModifySaveProduct);


        productInventoryApi = ProductInventoryClient.getRetrofitClient().getProductInventoryApi();
        productApi = ProductClient.getRetrofitClient().getProductApi();
    }

    @Override
    protected  void onResume() {
        super.onResume();

        Intent intent = getIntent();

        selecteProductInventory = intent.getParcelableExtra("selectedProduct");

        if(selecteProductInventory != null) {
            txtProductName.setText(selecteProductInventory.getProduct().getName());
            txxtModifyProductQuantity.setText(Long.toString(selecteProductInventory.getQuantity()));
            txtPrice.setText(Double.toString(selecteProductInventory.getPrice()));
            isUpdate = true;
        }

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              if(!isUpdate) {
                    createNewProduct();
                }else {

                  updateProduct(selecteProductInventory.getProduct());
                  updateProductInventory();
                }
            }
        });

    }
    public  void updateProductInventory() {

        selecteProductInventory.setPrice(Double.parseDouble(txtPrice.getText().toString()));
        selecteProductInventory.setQuantity(selecteProductInventory.getQuantity());
        Call<ProductInventory> call = productInventoryApi.update(selecteProductInventory);

        call.enqueue(new Callback<ProductInventory>() {
            @Override
            public void onResponse(Call<ProductInventory> call, Response<ProductInventory> response) {

                Toast.makeText(ModifyProductActivity.this, "Product updated!", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(Call<ProductInventory> call, Throwable t) {
                Toast.makeText(ModifyProductActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

    }

    public void createNewProduct()  {

        Product product = new Product();
        product.setName(txtProductName.getText().toString());

        Call<Product> call = productApi.create(product);

        call.enqueue(new Callback<Product>() {
            @Override
            public void onResponse(Call<Product> call, Response<Product> response) {

                  final Product responseProduct = response.body();
                  setProductInventorysProduct(responseProduct);

            }

            @Override
            public void onFailure(Call<Product> call, Throwable t) {
                Toast.makeText(ModifyProductActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void updateProduct(Product product) {
        if(txtProductName.getText().toString() == "") {
            txtProductName.setError("Product Name cannot be empty!");
            return;
        }
        product.setName(txtProductName.getText().toString());
        productApi = ProductClient.getRetrofitClient().getProductApi();

        Call<Product> productCall = productApi.update(product);

        productCall.enqueue(new Callback<Product>() {
            @Override
            public void onResponse(Call<Product> call, Response<Product> response) {
                Toast.makeText(ModifyProductActivity.this, response.toString(), Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(Call<Product> call, Throwable t) {

            }
        });


    }

    private void setProductInventorysProduct(Product product) {
        productInventory = new ProductInventory();
        product.setDateCreated(null);
        productInventory.setPrice(Double.parseDouble(txtPrice.getText().toString()));
        productInventory.setQuantity(Long.parseLong(txxtModifyProductQuantity.getText().toString()));
        productInventory.setProduct(product);
        createProductInventory(productInventory);
    }


    private void createProductInventory(ProductInventory productInventory) {

        productInventoryApi = ProductInventoryClient.getRetrofitClient().getProductInventoryApi();
        Call<ProductInventory>  productInventoryCall =  productInventoryApi.create(productInventory);

        productInventoryCall.enqueue(new Callback<ProductInventory>() {
            @Override
            public void onResponse(Call<ProductInventory> call, Response<ProductInventory> response) {

                Toast.makeText(ModifyProductActivity.this,"Product saved!", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(Call<ProductInventory> call, Throwable t) {
                Toast.makeText(ModifyProductActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

    }



}
