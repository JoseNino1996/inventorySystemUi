package com.jbnjr.inventorysystem.activity.customerinvoice;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.jbnjr.inventorysystem.R;
import com.jbnjr.inventorysystem.adapters.customerinvoice.ProductOrderListAdapter;
import com.jbnjr.inventorysystem.model.customerinvoice.ProductOrder;

import java.util.ArrayList;
import java.util.List;

public class SelectedOrderActivity  extends AppCompatActivity {
    private ProductOrder selectedOrder;
    private Button btnRemove,btnConfirm;
    private ListView lvSelectedOrders;
    private TextView txtAmountDue;
    private List<ProductOrder> productOrderList ;
    private ProductOrderListAdapter productOrderListAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(savedInstanceState == null) {
            setContentView(R.layout.fragment_selected_orders);

            btnRemove = findViewById(R.id.removeOrder);
            txtAmountDue = findViewById(R.id.txtAmountDue);
            btnConfirm = findViewById(R.id.btnConfirm);
            lvSelectedOrders = findViewById(R.id.lvSelectedProducts);
            Intent intent = getIntent();

            productOrderList = intent.getParcelableArrayListExtra("selectedListOfOrders");
        }
    }

    @Override
    protected void onResume(){
        super.onResume();

        if(productOrderList != null) {
            loadSelectedOrders();
        }




        lvSelectedOrders.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectedOrder =  productOrderList.get(position);

            }
        });

        btnRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(selectedOrder != null) {
                    productOrderList.remove(selectedOrder);
                    loadSelectedOrders();
                }
            }
        });

        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            Intent intent = new Intent(SelectedOrderActivity.this, ConfirmationOrderActivity.class);
            double amount = Double.parseDouble(txtAmountDue.getText().toString());
                if(productOrderList != null) {
                    intent.putParcelableArrayListExtra("selectedOrders", (ArrayList<? extends Parcelable>) productOrderList);

                    intent.putExtra("amountDue", amount);

                    startActivity(intent);
                } else {

                }
            }
        });
    }

    private void loadSelectedOrders() {
        productOrderListAdapter = new ProductOrderListAdapter(this, R.layout.activity_productlist_adapter,productOrderList);
        calculateAmountDue();
        lvSelectedOrders.setAdapter(productOrderListAdapter);

    }


    private void calculateAmountDue() {
        double amountDue = 0;
        if(productOrderList == null ) { return ; }
        for(ProductOrder productOrder : productOrderList) {
            amountDue += productOrder.getOrderedQty() * productOrder.getPrice();
        }
        txtAmountDue.setText(Double.toString(amountDue));

    }


}

