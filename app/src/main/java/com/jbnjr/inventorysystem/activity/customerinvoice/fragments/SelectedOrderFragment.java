package com.jbnjr.inventorysystem.activity.customerinvoice.fragments;

import android.content.Context;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.jbnjr.inventorysystem.R;
import com.jbnjr.inventorysystem.adapters.customerinvoice.ProductOrderListAdapter;
import com.jbnjr.inventorysystem.model.customerinvoice.ProductOrder;

import java.util.ArrayList;
import java.util.List;

public class SelectedOrderFragment extends Fragment {

    private  List<ProductOrder> productOrderList ;
    private ProductOrderListAdapter productOrderListAdapter;
    private  Bundle bundle, savedInstanceState;
    private Button btnRemove,btnConfirm;
    private ListView lvSelectedOrders;
    private Context context;
    private TextView txtAmountDue;
    private  CustomerInvoiceFragment customerInvoiceFragment;
    private ProductOrder selectedOrder;
    private View rootView;

    public SelectedOrderFragment() { }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        if(rootView == null) {
            rootView = inflater.inflate(R.layout.fragment_selected_orders, container, false);

            this.savedInstanceState = savedInstanceState;
            lvSelectedOrders = rootView.findViewById(R.id.lvSelectedProducts);
            btnRemove = rootView.findViewById(R.id.removeOrder);
            txtAmountDue = rootView.findViewById(R.id.txtAmountDue);
            btnConfirm = rootView.findViewById(R.id.btnConfirm);
            context = rootView.getContext();

            customerInvoiceFragment = new CustomerInvoiceFragment();
        }

        return  rootView;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bundle = savedInstanceState;
    }


    @Override
    public void onResume() {
        super.onResume();


        bundle = getArguments();

        if(bundle!= null)
        {
            productOrderList = bundle.getParcelableArrayList("selectedListOfOrders");

            loadSelectedOrders();
        }
        btnRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

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
                   Bundle  bundle = new Bundle();
                    bundle.putParcelableArrayList("selectedListOfOrders", (ArrayList<? extends Parcelable>) productOrderList);
                    bundle.putString("amountDue", txtAmountDue.getText().toString());

                    if(savedInstanceState == null) {
                        customerInvoiceFragment.setArguments(bundle);

                        FragmentManager fragmentManager = getFragmentManager();

                        fragmentManager.beginTransaction().replace(R.id.invoice_fragment_container1, customerInvoiceFragment).commit();
                    }

            }
        });
    }

    private void loadSelectedOrders() {
        productOrderListAdapter = new ProductOrderListAdapter(context, R.layout.activity_productlist_adapter,productOrderList);
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
