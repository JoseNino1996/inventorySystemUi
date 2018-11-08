package com.jbnjr.inventorysystem.activity.customer;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.jbnjr.inventorysystem.R;
import com.jbnjr.inventorysystem.api.customer.CustomerApi;
import com.jbnjr.inventorysystem.model.customer.Customer;
import com.jbnjr.inventorysystem.retrofit.client.customer.CustomerClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ModifyCustomerActivity extends AppCompatActivity {
   private EditText txtCustomerName, txtAddress, txtPhoneNumber;
    private CustomerApi customerApi;
   private Button btnCancel,btnSave;
    private boolean isUpdate;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_customer);

        txtCustomerName = findViewById(R.id.txtCustomerName);
        txtAddress = findViewById(R.id.txtAddress);
        txtPhoneNumber = findViewById(R.id.txtPhoneNumber);
        btnCancel = findViewById(R.id.btnCancel);
        btnSave = findViewById(R.id.btnModifySaveCustomer);
    }

    @Override
    protected  void onResume() {
        super.onResume();
        customerApi = CustomerClient.getRetrofitClient().getCustomerApi();
        Intent intent = getIntent();

        final Customer customer = intent.getParcelableExtra("selectedCustomer");
        if(customer != null) {
            txtCustomerName.setText(customer.getName());
            txtAddress.setText(customer.getAddress());
            txtPhoneNumber.setText(Long.toString(customer.getContactNumber()));
            isUpdate = true;


        }

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Call<Customer> call;
                if(!isUpdate) {
                    call  = customerApi.create(createNew());
                }else {
                    customer.setAddress(txtAddress.getText().toString());
                    customer.setName(txtCustomerName.getText().toString());
                    customer.setContactNumber(Long.parseLong(txtPhoneNumber.getText().toString()));
                    call = customerApi.update(customer);
                }


              call.enqueue(new Callback<Customer>() {
                  @Override
                  public void onResponse(Call<Customer> call, Response<Customer> response) {
                      Toast.makeText(ModifyCustomerActivity.this,"Customer info saved!!", Toast.LENGTH_LONG).show();
                  }

                  @Override
                  public void onFailure(Call<Customer> call, Throwable t) {
                      Toast.makeText(ModifyCustomerActivity.this,t.getMessage(), Toast.LENGTH_LONG).show();
                  }
              });

            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    public Customer createNew(){
        final Customer  newCustomer = new Customer();
        long contactNo = Long.parseLong(txtPhoneNumber.getText().toString());
        newCustomer.setName(txtCustomerName.getText().toString());
        newCustomer.setContactNumber(contactNo);
        newCustomer.setAddress(txtAddress.getText().toString());

        return  newCustomer;
    }
}
