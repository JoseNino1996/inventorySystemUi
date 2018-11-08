package com.jbnjr.inventorysystem.activity.customerinvoice.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.jbnjr.inventorysystem.model.product.Product;
import com.jbnjr.inventorysystem.retrofit.client.customer.CustomerClient;
import com.jbnjr.inventorysystem.retrofit.client.customerinvoice.CustomerInvoiceClient;

import java.io.DataOutputStream;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public  class CustomerInvoiceFragment  extends Fragment {
    private TextView txtDisplayAmountDue,txtCustomerName;
    private EditText txtInputAmountTendered, txtInputCustomerId;
    private Customer customer;
    private CustomerApi customerApi;
    private  Bundle bundle;
    private List<ProductOrder> productOrderList;
    private CustomerInvoice customerInvoice;
    private CustomerInvoiceApi customerInvoiceApi;
    private Button btnSearchCustomer,btnCompleteTransaction;

    private  View rootView;
    private double amountTendered, amountDue;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        if(rootView == null) {
            rootView = inflater.inflate(R.layout.fragment_invoice, container, false);

            txtDisplayAmountDue = rootView.findViewById(R.id.txtDisplayAmountDue);
            txtInputAmountTendered = rootView.findViewById(R.id.txtInputAmountTender);
            txtCustomerName = rootView.findViewById(R.id.txtFoundName);
            btnSearchCustomer = rootView.findViewById(R.id.btnFindCustomerById);
            txtInputCustomerId = rootView.findViewById(R.id.txtInputCustomerId);
            btnCompleteTransaction = rootView.findViewById(R.id.btnCompleteTransaction);
        }
        return  rootView;
    }

    @Override
    public  void onCreate(Bundle savedInstance) {
        super.onCreate(savedInstance);
    }

    @Override
    public void onResume() {
        super.onResume();
        bundle = getArguments();

        if(bundle!= null)
        {
            displayInvoice();
            for(ProductOrder productOrder : productOrderList) {
                Toast.makeText(getContext(), productOrder.toString(), Toast.LENGTH_LONG).show();
            }
        }

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
                        Toast.makeText(getContext(), "null productOrderlist", Toast.LENGTH_LONG).show();
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
                        //show errror message
                    }
            }
        });
    }


    private void saveInvoice(CustomerInvoice customerInvoice)   {
        customerInvoiceApi = CustomerInvoiceClient.getRetrofitClient().getCustomerInvoiceApi();

        Call<CustomerInvoice> call = customerInvoiceApi.create(customerInvoice);

        call.enqueue(new Callback<CustomerInvoice>() {
            @Override
            public void onResponse(Call<CustomerInvoice> call, Response<CustomerInvoice> response) {
                Toast.makeText(getContext(), response.toString(), Toast.LENGTH_LONG).show();
                Toast.makeText(getContext(), response.message(), Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(Call<CustomerInvoice> call, Throwable t) {
                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

    }



    private void displayInvoice() {
        productOrderList = bundle.getParcelableArrayList("selectedListOfOrders");
        String amountDue =bundle.getString("amountDue");

        txtDisplayAmountDue.setText(amountDue);

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
                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });


    }
}
