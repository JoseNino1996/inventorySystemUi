package com.jbnjr.inventorysystem.activity.customerinvoice;

import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.jbnjr.inventorysystem.R;
import com.jbnjr.inventorysystem.adapters.stock.ProductInventoryListAdapter;
import com.jbnjr.inventorysystem.api.stock.ProductInventoryApi;
import com.jbnjr.inventorysystem.api.stock.utilities.ProductInventoryApiUtil;
import com.jbnjr.inventorysystem.model.customerinvoice.ProductOrder;
import com.jbnjr.inventorysystem.model.product.Product;
import com.jbnjr.inventorysystem.model.stock.ProductInventory;
import com.jbnjr.inventorysystem.retrofit.client.stock.ProductInventoryClient;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SelectionOrderActivity extends AppCompatActivity {
    private ListView lvAvailProducts;
    private ProductInventoryListAdapter productInventoryListAdapter;
    private List<ProductInventory> productInventories;

    private ProductInventoryApi productInventoryApi;
    private Context context;

    private ProductInventory selectedProductInventory;

    private TextView txtDisplayCurrentQuantity, txtDisplayOrderQuantity, txtProductName,
            txtProductPrice;
    private Button btnAdd,btnComplete;
    private SeekBar seekBar;
    private List<ProductOrder> productOrders;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


            setContentView(R.layout.activity_selection_order);
            lvAvailProducts = findViewById(R.id.lvAvailProducts);

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

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(selectedProductInventory != null ) {
                    if(Integer.parseInt(txtDisplayCurrentQuantity.getText().toString()) > 1) {
                        ProductOrder productOrder = new ProductOrder();
                        productOrder.setOrderedQty(Long.parseLong(txtDisplayOrderQuantity.getText().toString()));
                        productOrder.setPrice(Double.parseDouble(txtProductPrice.getText().toString()));
                        productOrder.setProduct(selectedProductInventory.getProduct());

                        productOrders.add(productOrder);
                    } else {
                        Toast.makeText(SelectionOrderActivity.this, "Insufficient stock!", Toast.LENGTH_LONG).show();
                    }

                }else {
                    Toast.makeText(SelectionOrderActivity.this, "Please select a product", Toast.LENGTH_LONG).show();
                }
            }
        });


        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if(selectedProductInventory == null) { return; }
                txtDisplayOrderQuantity.setText(Long.toString(progress));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });


        btnComplete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(productOrders.size() >= 1) {
                    Intent intent = new Intent(SelectionOrderActivity.this, SelectedOrderActivity.class);
                    intent.putParcelableArrayListExtra("selectedListOfOrders", (ArrayList<? extends Parcelable>) productOrders);

                    startActivity(intent);
                }else {
                    Toast.makeText(SelectionOrderActivity.this, "Empty order/s", Toast.LENGTH_LONG).show();
                }
            }
        });


        lvAvailProducts.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectedProductInventory = productInventories.get(position);
                txtDisplayCurrentQuantity.setText(Long.toString(selectedProductInventory.getQuantity()));
                txtProductName.setText(selectedProductInventory.getProduct().getName());
                txtProductPrice.setText(Double.toString(selectedProductInventory.getPrice()));
                seekBar.setMax( (int) (selectedProductInventory.getQuantity()) );

            }
        });
    }



    private void loadProducts() {
        productInventoryApi = ProductInventoryClient.getRetrofitClient().getProductInventoryApi();

        Call<List<ProductInventory>> call = productInventoryApi.findAll();

        call.enqueue(new Callback<List<ProductInventory>>() {
            @Override
            public void onResponse(Call<List<ProductInventory>> call, Response<List<ProductInventory>> response) {
                productInventories = response.body();
                if(productInventories == null)  {return; }
                productInventoryListAdapter = new ProductInventoryListAdapter(SelectionOrderActivity.this,
                        R.layout.activity_productlist_adapter,productInventories);
                lvAvailProducts.setAdapter(productInventoryListAdapter);
            }

            @Override
            public void onFailure(Call<List<ProductInventory>> call, Throwable t) {

            }
        });


    }




}
