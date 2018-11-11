package com.jbnjr.inventorysystem.activity.customerinvoice.edit;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.jbnjr.inventorysystem.R;
import com.jbnjr.inventorysystem.adapters.customerinvoice.ProductOrderListAdapter;
import com.jbnjr.inventorysystem.model.customerinvoice.CustomerInvoice;
import com.jbnjr.inventorysystem.model.customerinvoice.ProductOrder;

import java.util.List;

public class EditCurrentSelectedOrderActivity extends AppCompatActivity {
    private ProductOrder selectedOrder;
    private Button btnRemove,btnConfirm;
    private ListView lvSelectedOrders;
    private TextView txtAmountDue;
    private List<ProductOrder> productOrderList ;
    private ProductOrderListAdapter productOrderListAdapter;
    private CustomerInvoice customerInvoice;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selected_orders);

        btnRemove = findViewById(R.id.removeOrder);
        txtAmountDue = findViewById(R.id.txtAmountDue);
        btnConfirm = findViewById(R.id.btnConfirm);
        lvSelectedOrders = findViewById(R.id.lvSelectedProducts);
        Intent intent = getIntent();

        customerInvoice = intent.getParcelableExtra("customerInvoice");
    }

    @Override
    protected void onResume() {

        super.onResume();
        if(customerInvoice!=null) {
            productOrderList = customerInvoice.getProductOrderList();
            loadSelectedOrders();
        }
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
