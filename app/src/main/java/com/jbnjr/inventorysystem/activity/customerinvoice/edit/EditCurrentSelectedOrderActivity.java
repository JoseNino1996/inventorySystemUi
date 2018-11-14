package com.jbnjr.inventorysystem.activity.customerinvoice.edit;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.jbnjr.inventorysystem.R;
import com.jbnjr.inventorysystem.activity.customerinvoice.SelectionOrderActivity;
import com.jbnjr.inventorysystem.adapters.customerinvoice.ProductOrderListAdapter;
import com.jbnjr.inventorysystem.model.customerinvoice.CustomerInvoice;
import com.jbnjr.inventorysystem.model.customerinvoice.ProductOrder;

import java.util.List;

public class EditCurrentSelectedOrderActivity extends AppCompatActivity {
    private ProductOrder selectedOrder;
    private Button btnModifyOrders;
    private ListView lvSelectedOrders;
    private TextView txtAmountDue;
    private List<ProductOrder> productOrderList ;
    private ProductOrderListAdapter productOrderListAdapter;
    private CustomerInvoice customerInvoice;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_invoice);

        txtAmountDue = findViewById(R.id.txtAmountDue);
        btnModifyOrders = findViewById(R.id.btnCIModfy);
        lvSelectedOrders = findViewById(R.id.lvSelectedProducts);
        Intent intent = getIntent();

        customerInvoice = intent.getParcelableExtra("customerInvoice");
    }

    @Override
    protected void onResume() {

        super.onResume();
            productOrderList = customerInvoice.getProductOrderList();
            loadSelectedOrders();

            btnModifyOrders.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(EditCurrentSelectedOrderActivity.this, SelectionOrderActivity.class);
                    intent.putExtra("customerInvoice", customerInvoice);

                    startActivity(intent);
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
