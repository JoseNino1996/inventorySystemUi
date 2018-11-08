package com.jbnjr.inventorysystem.adapters.customer;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.jbnjr.inventorysystem.R;
import com.jbnjr.inventorysystem.model.customer.Customer;

import java.util.List;

public class CustomerListAdapter extends ArrayAdapter<Customer> {
    private Context context;
    private  int resource;


    public CustomerListAdapter( Context context, int resource, List<Customer> objects) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
    }


    @Override
    public View getView(int position,  View convertView,  ViewGroup parent) {
        String name = getItem(position).getName();
        String address = getItem(position).getAddress();
        long contactNumber = getItem(position).getContactNumber();


        LayoutInflater inflater = LayoutInflater.from(context);

        convertView = inflater.inflate(resource,parent,false);

        TextView txtName =  convertView.findViewById(R.id.txtCustName);
        TextView txtAddress = convertView.findViewById(R.id.txtAddress);
        TextView txtPhoneNumber = convertView.findViewById(R.id.txtPhoneNum);

        txtAddress.setText("Address: " + address);
        txtName.setText(name);
        txtPhoneNumber.setText("Contact Number: "+Long.toString(contactNumber ));


        return  convertView;

    }
}
