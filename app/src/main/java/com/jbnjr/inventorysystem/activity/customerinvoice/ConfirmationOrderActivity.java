package com.jbnjr.inventorysystem.activity.customerinvoice;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.jbnjr.inventorysystem.R;
import com.jbnjr.inventorysystem.api.customer.CustomerApi;
import com.jbnjr.inventorysystem.api.customerinvoice.CustomerInvoiceApi;
import com.jbnjr.inventorysystem.model.customer.Customer;
import com.jbnjr.inventorysystem.model.customerinvoice.CustomerInvoice;
import com.jbnjr.inventorysystem.model.customerinvoice.ProductOrder;
import com.jbnjr.inventorysystem.retrofit.client.customer.CustomerClient;
import com.jbnjr.inventorysystem.retrofit.client.customerinvoice.CustomerInvoiceClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ConfirmationOrderActivity extends AppCompatActivity {

    private TextView txtDisplayAmountDue,txtCustomerName;
    private EditText txtInputAmountTendered, txtInputCustomerId;
    private Customer customer;
    private CustomerApi customerApi;
    private double  amountDue;

    private List<ProductOrder> productOrderList;
    private CustomerInvoice customerInvoice;
    private CustomerInvoiceApi customerInvoiceApi;
    private Button btnSearchCustomer,btnCompleteTransaction;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState == null) {
            setContentView(R.layout.activity_confirmation_invoice);


            txtDisplayAmountDue = findViewById(R.id.txtDisplayAmountDue);
            txtInputAmountTendered = findViewById(R.id.txtInputAmountTender);
            txtCustomerName = findViewById(R.id.txtFoundName);
            btnSearchCustomer = findViewById(R.id.btnFindCustomerById);
            txtInputCustomerId = findViewById(R.id.txtInputCustomerId);
            btnCompleteTransaction = findViewById(R.id.btnCompleteTransaction);
        }
    }

    @Override
    protected  void onResume() {
        super.onResume();

        displayInvoice();

        btnSearchCustomer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                findById(Integer.parseInt(txtInputCustomerId.getText().toString()));

            }
        });


        btnCompleteTransaction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                double amountDue = Double.parseDouble(txtDisplayAmountDue.getText().toString());
                double amountTendered = Double.parseDouble(txtInputAmountTendered.getText().toString());
                if(productOrderList == null) {
                    Toast.makeText(ConfirmationOrderActivity.this, "null product order list", Toast.LENGTH_LONG).show();
                    return;
                }
                if(amountTendered >= amountDue) {
                    CustomerInvoice customerInvoice = new CustomerInvoice();
                    customerInvoice.setDate(null);
                    customerInvoice.setAmountDue(amountDue);
                    customerInvoice.setAmountTendered(amountTendered);
                    customerInvoice.setCustomer(customer);
                    customerInvoice.setProductOrderList(productOrderList);
                    saveInvoice(customerInvoice);

                } else {

                }
            }
        });
    }



        private void displayInvoice() {
            Intent intent = getIntent();
            productOrderList = intent.getParcelableArrayListExtra("selectedOrders");
             amountDue =intent.getDoubleExtra("amountDue", 0);

            txtDisplayAmountDue.setText(Double.toString(amountDue));

        }




    private void findById(long id){
        customerApi =  CustomerClient.getRetrofitClient().getCustomerApi();

        Call<Customer> call = customerApi.findById(id);

        call.enqueue(new Callback<Customer>() {
            @Override
            public void onResponse(Call<Customer> call, Response<Customer> response) {
                customer = response.body();
                txtCustomerName.setText(customer.getName());

            }

            @Override
            public void onFailure(Call<Customer> call, Throwable t) {
                Toast.makeText(ConfirmationOrderActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }


    private void saveInvoice(CustomerInvoice customerInvoice)   {
        customerInvoiceApi = CustomerInvoiceClient.getRetrofitClient().getCustomerInvoiceApi();

        Call<CustomerInvoice> call = customerInvoiceApi.create(customerInvoice);

        call.enqueue(new Callback<CustomerInvoice>() {
            @Override
            public void onResponse(Call<CustomerInvoice> call, Response<CustomerInvoice> response) {
                Toast.makeText(ConfirmationOrderActivity.this, response.toString(), Toast.LENGTH_LONG).show();
                Toast.makeText(ConfirmationOrderActivity.this, response.message(), Toast.LENGTH_LONG).show();
                finish();
            }

            @Override
            public void onFailure(Call<CustomerInvoice> call, Throwable t) {
                Toast.makeText(ConfirmationOrderActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

    }
}
