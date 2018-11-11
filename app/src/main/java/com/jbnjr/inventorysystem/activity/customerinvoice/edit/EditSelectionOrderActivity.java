package com.jbnjr.inventorysystem.activity.customerinvoice.edit;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.jbnjr.inventorysystem.R;
import com.jbnjr.inventorysystem.activity.customerinvoice.SelectionOrderActivity;
import com.jbnjr.inventorysystem.adapters.stock.ProductInventoryListAdapter;
import com.jbnjr.inventorysystem.api.stock.ProductInventoryApi;
import com.jbnjr.inventorysystem.model.customerinvoice.ProductOrder;
import com.jbnjr.inventorysystem.model.stock.ProductInventory;
import com.jbnjr.inventorysystem.retrofit.client.stock.ProductInventoryClient;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditSelectionOrderActivity extends AppCompatActivity {
    private ListView lvAvailProducts;
    private ProductInventoryListAdapter productInventoryListAdapter;
    private List<ProductInventory> productInventories;

    private ProductInventoryApi productInventoryApi;
    private Context context;

    private ProductInventory selectedProductInventory;

    private TextView txtDisplayCurrentQuantity, txtDisplayOrderQuantity, txtProductName,
            txtProductPrice;
    private Button btnAdd, btnComplete;
    private SeekBar seekBar;
    private List<ProductOrder> productOrders;
    public static Map<Long, ProductOrder> productOrderMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invoice);

        lvAvailProducts = findViewById(R.id.lvAvailProducts);
        productOrderMap = new HashMap<>();
        productInventories = new ArrayList<>();

        txtDisplayCurrentQuantity = findViewById(R.id.txtCurrentQuantity);
        txtDisplayOrderQuantity = findViewById(R.id.txtDisplayOrderQuantity);
        txtProductName = findViewById(R.id.txtOrderName);
        txtProductPrice = findViewById(R.id.txtOrderPric);
        seekBar = findViewById(R.id.seekBarOrderQty);
        btnAdd = findViewById(R.id.btnAddToSelectedProduct);
        btnComplete = findViewById(R.id.btnComplete);

        productOrders = new ArrayList<>();

    }

    @Override
    protected void onResume() {
        super.onResume();
        loadProducts();

    }


    private void loadProducts() {
        productInventoryApi = ProductInventoryClient.getRetrofitClient().getProductInventoryApi();

        Call<List<ProductInventory>> call = productInventoryApi.findAll();

        call.enqueue(new Callback<List<ProductInventory>>() {
            @Override
            public void onResponse(Call<List<ProductInventory>> call, Response<List<ProductInventory>> response) {
                productInventories = response.body();
                if (productInventories == null) {
                    return;
                }
                productInventoryListAdapter = new ProductInventoryListAdapter(EditSelectionOrderActivity.this,
                        R.layout.activity_productlist_adapter, productInventories);
                lvAvailProducts.setAdapter(productInventoryListAdapter);
            }

            @Override
            public void onFailure(Call<List<ProductInventory>> call, Throwable t) {

            }
        });

    }
}
