package com.jbnjr.inventorysystem.activity.customerinvoice;

import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
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
import com.jbnjr.inventorysystem.model.customerinvoice.CustomerInvoice;
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
    public static Map<String,ProductOrder> productOrderMap;

    private CustomerInvoice customerInvoice;

    private Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


            setContentView(R.layout.activity_selection_order);
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

            intent = getIntent();


    }

    // refactor

    private  boolean hasCustomerInvoice() {
        if(intent.hasExtra("customerInvoice")) {
            customerInvoice = intent.getParcelableExtra("customerInvoice");
            return  true;
        }

        return false;
    }

    private void setCustomerInvoiceOrdersToThisProductOrders() {
        productOrders = customerInvoice.getProductOrderList();
        mapProductOrders();
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadProducts();


        if(hasCustomerInvoice()) {
            setCustomerInvoiceOrdersToThisProductOrders();
        }

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(selectedProductInventory != null ) {
                    if(productOrderMap.get(selectedProductInventory.getProduct().getName()) != null) {
                        Toast.makeText(SelectionOrderActivity.this, "Product is already added!", Toast.LENGTH_LONG).show();
                        return;
                    }
                    if(Integer.parseInt(txtDisplayCurrentQuantity.getText().toString()) < 1 ||
                            Integer.parseInt(txtDisplayOrderQuantity.getText().toString()) < 1) {

                        Toast.makeText(SelectionOrderActivity.this, "Invalid Order!", Toast.LENGTH_LONG).show();
                    } else {

                        ProductOrder productOrder = getProductOrder();
                        productOrders.add(productOrder);
                        productOrderMap.put(selectedProductInventory.getProduct().getName(), productOrder);
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
                    if(hasCustomerInvoice()) {
                        intent.putExtra("customerInvoice", customerInvoice);
                        startActivity(intent);

                    } else {
                        intent.putParcelableArrayListExtra("selectedListOfOrders", (ArrayList<? extends Parcelable>) productOrders);
                        startActivityForResult(intent, 1000);
                    }
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


    private void mapProductOrders() {

       for(ProductOrder productOrder : productOrders) {
            productOrderMap.put(productOrder.getProduct().getName(), productOrder);

       }
    }

    private ProductOrder getProductOrder() {
        ProductOrder productOrder = new ProductOrder();
        productOrder.setOrderedQty(Long.parseLong(txtDisplayOrderQuantity.getText().toString()));
        productOrder.setPrice(Double.parseDouble(txtProductPrice.getText().toString()));
        productOrder.setProduct(selectedProductInventory.getProduct());

        return  productOrder;
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


    @Override
    protected  void onActivityResult(int requestCode, int resultCode, Intent intent) {
        productOrderMap = new HashMap<>();
        if(resultCode == RESULT_OK && requestCode == 1000) {
            if(intent.hasExtra("backSelectedListOfOrders")) {
                productOrders = intent.getParcelableArrayListExtra("backSelectedListOfOrders");
                mapProductOrders();

            }
        }
    }




}
