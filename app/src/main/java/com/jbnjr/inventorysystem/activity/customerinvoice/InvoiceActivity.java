package com.jbnjr.inventorysystem.activity.customerinvoice;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.jbnjr.inventorysystem.R;
import com.jbnjr.inventorysystem.activity.customerinvoice.edit.EditCurrentSelectedOrderActivity;
import com.jbnjr.inventorysystem.adapters.customerinvoice.CustomerInvoiceListAdapter;
import com.jbnjr.inventorysystem.api.customerinvoice.CustomerInvoiceApi;
import com.jbnjr.inventorysystem.model.customer.Customer;
import com.jbnjr.inventorysystem.model.customerinvoice.CustomerInvoice;
import com.jbnjr.inventorysystem.retrofit.client.customerinvoice.CustomerInvoiceClient;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InvoiceActivity extends AppCompatActivity {
    private List<CustomerInvoice> customerInvoiceList;
    private CustomerInvoiceApi customerInvoiceApi;
    private ListView listView;
    private CustomerInvoiceListAdapter customerInvoiceListAdapter;
    private  CustomerInvoice selectedInvoice;
    private TextView txtCustomerId;

    Button btnSearch,btnEdit, btnDelete, btnRefresh;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invoice);
        listView = findViewById(R.id.lvCustomerInvoice);
        btnEdit = findViewById(R.id.btnEditInvoice);
        btnDelete = findViewById(R.id.btnDelete);
        btnSearch = findViewById(R.id.btnSearchInvoice);
        txtCustomerId = findViewById(R.id.txtLvId);
        btnRefresh = findViewById(R.id.btnRefresh);
        btnDelete = findViewById(R.id.btnDeleteInvoice);
    }


    @Override
    protected  void onResume() {
        super.onResume();
        loadListOfCustomerInvoice();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectedInvoice = customerInvoiceList.get(position);

            }
        });

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                findInvoiceById(Long.parseLong(txtCustomerId.getText().toString()));
            }
        });

       btnRefresh.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               loadListOfCustomerInvoice();
           }
       });

       btnDelete.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               deleteCustomerInvoice();
               loadListOfCustomerInvoice();
           }
       });

       btnEdit.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               if(selectedInvoice!=null) {
                   Intent intent = new Intent(InvoiceActivity.this, EditCurrentSelectedOrderActivity.class);
                   intent.putExtra("customerInvoice", selectedInvoice);
                   startActivity(intent);
               }
           }
       });
    }

    private void deleteCustomerInvoice() {
        if(selectedInvoice != null) {
            customerInvoiceApi = CustomerInvoiceClient.getRetrofitClient().getCustomerInvoiceApi();

            Call<CustomerInvoice> call = customerInvoiceApi.deleteById(selectedInvoice.getId());

         call.enqueue(new Callback<CustomerInvoice>() {
             @Override
             public void onResponse(Call<CustomerInvoice> call, Response<CustomerInvoice> response) {
                 Toast.makeText(InvoiceActivity.this,response.toString(), Toast.LENGTH_LONG).show();
                 Toast.makeText(InvoiceActivity.this,response.toString(), Toast.LENGTH_LONG).show();
             }

             @Override
             public void onFailure(Call<CustomerInvoice> call, Throwable t) {

             }
         });
        } else {
            Toast.makeText(InvoiceActivity.this, "Select an invoice!", Toast.LENGTH_LONG).show();
        }
    }

    private void loadListOfCustomerInvoice() {
        customerInvoiceApi = CustomerInvoiceClient.getRetrofitClient().getCustomerInvoiceApi();

        Call<List<CustomerInvoice>> call = customerInvoiceApi.findAll();

        call.enqueue(new Callback<List<CustomerInvoice>>() {
            @Override
            public void onResponse(Call<List<CustomerInvoice>> call, Response<List<CustomerInvoice>> response) {
                customerInvoiceList = response.body();
                if(customerInvoiceList != null) {
                  fillListView(getApplicationContext(), R.layout.activity_invoicelist_adapter, customerInvoiceList);
                }
            }

            @Override
            public void onFailure(Call<List<CustomerInvoice>> call, Throwable t) {

            }
        });
    }

    private void findInvoiceById(long id) {
        customerInvoiceApi = CustomerInvoiceClient.getRetrofitClient().getCustomerInvoiceApi();

        Call<CustomerInvoice> call = customerInvoiceApi.findByid(id);

        call.enqueue(new Callback<CustomerInvoice>() {
            @Override
            public void onResponse(Call<CustomerInvoice> call, Response<CustomerInvoice> response) {
                customerInvoiceList = new ArrayList<>();

                CustomerInvoice customerInvoice = response.body();

                customerInvoiceList.add(customerInvoice);

                fillListView(getApplicationContext(),R.layout.activity_invoicelist_adapter, customerInvoiceList);
            }

            @Override
            public void onFailure(Call<CustomerInvoice> call, Throwable t) {

            }
        });

    }

    private void fillListView(final Context context, int resource, List<CustomerInvoice> customerInvoices) {
        customerInvoiceListAdapter = new CustomerInvoiceListAdapter(context, resource, customerInvoices);
        listView.setAdapter(customerInvoiceListAdapter);
    }
}
