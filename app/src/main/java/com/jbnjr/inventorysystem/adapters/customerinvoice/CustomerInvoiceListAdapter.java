package com.jbnjr.inventorysystem.adapters.customerinvoice;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.jbnjr.inventorysystem.R;
import com.jbnjr.inventorysystem.model.customerinvoice.CustomerInvoice;
import com.jbnjr.inventorysystem.model.customerinvoice.ProductOrder;

import java.util.List;

public class CustomerInvoiceListAdapter extends ArrayAdapter<CustomerInvoice> {

    private Context context;
    private  int resource;

    public CustomerInvoiceListAdapter(Context context, int resource,List<CustomerInvoice> objects) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        CustomerInvoice customerInvoice = new CustomerInvoice();
        customerInvoice.setProductOrderList(getItem(position).getProductOrderList());
        customerInvoice.setCustomer(getItem(position).getCustomer());
        customerInvoice.setAmountTendered(getItem(position).getAmountTendered());
        customerInvoice.setAmountDue(getItem(position).getAmountDue());


        LayoutInflater inflater = LayoutInflater.from(context);

        convertView = inflater.inflate(resource,parent,false);

        TextView txtCustomerName, txtAmountTendered, txtAmountDue, txtOrderQuantities;
        txtCustomerName  = convertView.findViewById(R.id.txtLvCustomerName);
        txtAmountTendered = convertView.findViewById(R.id.txtLvAmountTendered);
        txtAmountDue = convertView.findViewById(R.id.txtLvAmountDue);
        txtOrderQuantities=convertView.findViewById(R.id.txtLvOrderQuantity);

       long totalOrderQuanity =  calculateQuantityOrder(customerInvoice.getProductOrderList());
        txtCustomerName.setText(customerInvoice.getCustomer().getName());
        txtAmountDue.setText(Double.toString(customerInvoice.getAmountDue()));
        txtAmountTendered.setText(Double.toString(customerInvoice.getAmountTendered()));
        txtOrderQuantities.setText(Long.toString(totalOrderQuanity));

        return  convertView;
    }

    private long calculateQuantityOrder(List<ProductOrder> productOrders) {
        long totalOrderQuantity = 0;
        for(ProductOrder productOrder : productOrders) {
            totalOrderQuantity += productOrder.getOrderedQty();
        }

        return totalOrderQuantity;
    }
}

