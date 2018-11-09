package com.jbnjr.inventorysystem;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.jbnjr.inventorysystem.activity.customer.CustomerActivity;
import com.jbnjr.inventorysystem.activity.customerinvoice.SelectionOrderActivity;
import com.jbnjr.inventorysystem.activity.product.ProductActivity;

public class MainActivity extends AppCompatActivity {
    Button btnCustomerActivity, btnProductActivity, btnCustomerInvoice;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnCustomerInvoice = findViewById(R.id.btnOpenCustomerInvoice);
        btnCustomerActivity = findViewById(R.id.btnCustomerActivity);
        btnProductActivity = findViewById(R.id.btnProductActivity);

        btnCustomerActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, CustomerActivity.class);
                startActivity(intent);
            }
        });

        btnProductActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ProductActivity.class);
                startActivity(intent);
            }
        });

        btnCustomerInvoice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SelectionOrderActivity.class);
                startActivity(intent);
            }
        });
    }
}
