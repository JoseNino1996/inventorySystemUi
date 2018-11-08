package com.jbnjr.inventorysystem.activity.customer;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.jbnjr.inventorysystem.R;
import com.jbnjr.inventorysystem.api.customer.CustomerApi;
import com.jbnjr.inventorysystem.adapters.customer.CustomerListAdapter;
import com.jbnjr.inventorysystem.model.customer.Customer;
import com.jbnjr.inventorysystem.retrofit.client.customer.CustomerClient;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CustomerActivity extends AppCompatActivity {
    private ListView lvCustomer;
    private List<Customer> customerList;
    private CustomerApi customerApi;
    CustomerListAdapter customerListAdapter;

    private Customer selectedCustomer;

    Button btnSave,btnUpdate,btnDelete;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer);
        lvCustomer = findViewById(R.id.lvCustomer);
        btnSave = findViewById(R.id.btnSave);
        btnUpdate = findViewById(R.id.btnUpdate);
        btnDelete = findViewById(R.id.btnDelete);



    }



    @Override
    protected void onResume() {
        super.onResume();
        customerList = new ArrayList<>();


        loadListOfCustomer();

        lvCustomer.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectedCustomer = customerList.get(position);

                Toast.makeText(CustomerActivity.this,selectedCustomer.toString(),Toast.LENGTH_LONG ).show();
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CustomerActivity.this, ModifyCustomerActivity.class);
                startActivity(intent);
            }
        });

        btnUpdate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if(selectedCustomer == null) {
                    Toast.makeText(CustomerActivity.this, "Select a Customer first", Toast.LENGTH_LONG).show();
                    return;
                }
                Intent intent = new Intent(CustomerActivity.this, ModifyCustomerActivity.class);

                intent.putExtra("selectedCustomer", selectedCustomer);

                startActivity(intent);
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(selectedCustomer == null) {
                    Toast.makeText(CustomerActivity.this, "Select a Customer first", Toast.LENGTH_LONG).show();
                    return;
                }
                 delete(selectedCustomer);
               loadListOfCustomer();


            }
        });

    }
    public void loadListOfCustomer() {
        customerApi = CustomerClient.getRetrofitClient().getCustomerApi();
        Call<List<Customer>> call = customerApi.findAll();

        call.enqueue(new Callback<List<Customer>>() {
            @Override
            public void onResponse(Call<List<Customer>> call, Response<List<Customer>> response) {
                List<Customer> customers = response.body();
                if( customerList != null) { customerList = null; }
                customerList = customers;

                customerListAdapter = new CustomerListAdapter(CustomerActivity.this,R.layout.activity_customerlist_adapter,customers);
                lvCustomer.setAdapter(customerListAdapter);

            }

            @Override
            public void onFailure(Call<List<Customer>> call, Throwable t) {
                Toast.makeText(CustomerActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    public  void delete(Customer customer) {
        Call<Customer> customerCall = customerApi.delete(customer.getId());

        customerCall.enqueue(new Callback<Customer>() {
            @Override
            public void onResponse(Call<Customer> call, Response<Customer> response) {
                Toast.makeText(CustomerActivity.this, "Customer has been deleted!", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(Call<Customer> call, Throwable t) {
                Toast.makeText(CustomerActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

    }
}
