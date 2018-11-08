package com.jbnjr.inventorysystem.adapters.stock;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.jbnjr.inventorysystem.R;
import com.jbnjr.inventorysystem.model.product.Product;
import com.jbnjr.inventorysystem.model.stock.ProductInventory;

import java.util.List;

public class ProductInventoryListAdapter extends ArrayAdapter<ProductInventory> {
    private Context context;
    private  int resource;



    public ProductInventoryListAdapter(Context context, int resource, List<ProductInventory> objects) {
        super(context, resource, objects);

        this.context =context;
        this.resource = resource;

    }

    @Override
    public View getView(int position,  View convertView, ViewGroup parent) {
        String name = getItem(position).getProduct().getName();
        double price = getItem(position).getPrice();
        long quantity = getItem(position).getQuantity();


        LayoutInflater inflater = LayoutInflater.from(context);

        convertView = inflater.inflate(resource,parent,false);

        TextView txtProductName =  convertView.findViewById(R.id.txtProductAdapterName);
        TextView txtProductPrice = convertView.findViewById(R.id.txtAdapterPrice);
        TextView txtProductQuantity = convertView.findViewById(R.id.txtAdapterQuantity);


        txtProductName.setText(name);
        txtProductPrice.setText("Price: "+Double.toString(price) );
        txtProductQuantity.setText("Quantity: "+Long.toString(quantity ));


        return convertView;
    }
}
