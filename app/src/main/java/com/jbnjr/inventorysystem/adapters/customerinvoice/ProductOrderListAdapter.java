package com.jbnjr.inventorysystem.adapters.customerinvoice;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.jbnjr.inventorysystem.R;
import com.jbnjr.inventorysystem.model.customerinvoice.ProductOrder;
import com.jbnjr.inventorysystem.model.product.Product;

import java.util.List;

public class ProductOrderListAdapter  extends ArrayAdapter<ProductOrder> {

    private Context context;
    private  int resource;

    public ProductOrderListAdapter( Context context, int resource, List<ProductOrder> objects) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
    }


    @Override
    public View getView(int position, View convertView,  ViewGroup parent) {
        long orderedQty = getItem(position).getOrderedQty();
        double price = getItem(position).getPrice();
        Product product = getItem(position).getProduct();


        LayoutInflater inflater = LayoutInflater.from(context);

        convertView = inflater.inflate(resource,parent,false);

        TextView txtProductName =  convertView.findViewById(R.id.txtProductAdapterName);
        TextView txtProductPrice = convertView.findViewById(R.id.txtAdapterPrice);
        TextView txtOrderQuantity = convertView.findViewById(R.id.txtAdapterQuantity);

        txtProductName.setText(product.getName());
        txtProductPrice.setText(Double.toString(price));
        txtOrderQuantity.setText(Long.toString(orderedQty));
        return  convertView;
    }
}
