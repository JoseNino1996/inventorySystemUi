package com.jbnjr.inventorysystem.activity.customerinvoice.fragments;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.support.v4.app.Fragment;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;


import com.jbnjr.inventorysystem.R;
import com.jbnjr.inventorysystem.adapters.stock.ProductInventoryListAdapter;
import com.jbnjr.inventorysystem.api.stock.ProductInventoryApi;
import com.jbnjr.inventorysystem.model.customerinvoice.ProductOrder;
import com.jbnjr.inventorysystem.model.stock.ProductInventory;
import com.jbnjr.inventorysystem.retrofit.client.stock.ProductInventoryClient;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ProductOrderFragment extends  Fragment {

    private ProductInventoryApi productInventoryApi;
    private ProductInventoryListAdapter productListAdapter;
    private List<ProductInventory> productInventories;
    private ListView listView;
    private TextView txtDisplayCurrentQuantity, txtDisplayOrderQuantity, txtProductName,
            txtProductPrice;
    private   View rootView;

    private ProductInventory selectedProductInventory;

    private SeekBar seekBar;

    private Context context;

    private   Button btnAdd,btnComplete;

    private List<ProductOrder> productOrders;
    private SelectedOrderFragment selectedOrderFragment;

    public  ProductOrderFragment() {}
    @Override
    public View onCreateView(LayoutInflater inflater,  ViewGroup container,  Bundle savedInstanceState) {

        if(rootView == null) {
            productOrders = new ArrayList<>();
            rootView = inflater.inflate(R.layout.fragment_order, container, false);
            context = rootView.getContext();
            listView = rootView.findViewById(R.id.lvAvailProducts);
            txtDisplayCurrentQuantity = rootView.findViewById(R.id.txtCurrentQuantity);
            txtDisplayOrderQuantity = rootView.findViewById(R.id.txtDisplayOrderQuantity);
            txtProductName = rootView.findViewById(R.id.txtOrderName);
            txtProductPrice = rootView.findViewById(R.id.txtOrderPric);
            seekBar = rootView.findViewById(R.id.seekBarOrderQty);
            btnAdd = rootView.findViewById(R.id.btnAddToSelectedProduct);
            btnComplete = rootView.findViewById(R.id.btnComplete);

            selectedOrderFragment = new SelectedOrderFragment();
        }
        return  rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        seekBar.setMax(0);

        loadProducts();


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectedProductInventory = productInventories.get(position);
                txtDisplayCurrentQuantity.setText(Long.toString(selectedProductInventory.getQuantity()));
                txtProductName.setText(selectedProductInventory.getProduct().getName());
                txtProductPrice.setText(Double.toString(selectedProductInventory.getPrice()));
                seekBar.setMax( (int) (selectedProductInventory.getQuantity()) );

            }
        });

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            if(selectedProductInventory != null) {
//
                    ProductOrder productOrder = new ProductOrder();
                    productOrder.setOrderedQty(Long.parseLong(txtDisplayOrderQuantity.getText().toString()));
                    productOrder.setPrice(Double.parseDouble(txtProductPrice.getText().toString()));
                    productOrder.setProduct(selectedProductInventory.getProduct());

                      productOrders.add(productOrder);

              }
            }
        });

        btnComplete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(productOrders.size() >= 1) {
                    Bundle bundle = new Bundle();
                    bundle.putParcelableArrayList("selectedListOfOrders", (ArrayList<? extends Parcelable>) productOrders);
                    selectedOrderFragment.setArguments(bundle);
                    FragmentManager fragmentManager = getFragmentManager();
                   fragmentManager.beginTransaction().replace(R.id.containerSelectedOrders1, selectedOrderFragment).commit();

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

    }

    private void loadProducts() {
        productInventoryApi = ProductInventoryClient.getRetrofitClient().getProductInventoryApi();

        Call<List<ProductInventory>>  call = productInventoryApi.findAll();

        call.enqueue(new Callback<List<ProductInventory>>() {
            @Override
            public void onResponse(Call<List<ProductInventory>> call, Response<List<ProductInventory>> response) {

                productInventories = response.body();
                productListAdapter = new ProductInventoryListAdapter(context, R.layout.activity_productlist_adapter,productInventories);

                listView.setAdapter(productListAdapter);
            }

            @Override
            public void onFailure(Call<List<ProductInventory>> call, Throwable t) {

            }
        });

    }
}
