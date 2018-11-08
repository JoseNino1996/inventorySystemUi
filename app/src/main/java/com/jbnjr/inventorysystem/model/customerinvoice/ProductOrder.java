package com.jbnjr.inventorysystem.model.customerinvoice;

import android.os.Parcel;
import android.os.Parcelable;

import com.jbnjr.inventorysystem.model.product.Product;

public class ProductOrder  implements Parcelable {
    private Long id;
    private long orderedQty;
    private double price;

    private Product product;

    protected ProductOrder(Parcel in) {
        if (in.readByte() == 0) {
            id = null;
        } else {
            id = in.readLong();
        }
        orderedQty = in.readLong();
        price = in.readDouble();
        product = in.readParcelable(Product.class.getClassLoader());
    }
    public  ProductOrder() {}
    public static final Creator<ProductOrder> CREATOR = new Creator<ProductOrder>() {
        @Override
        public ProductOrder createFromParcel(Parcel in) {
            return new ProductOrder(in);
        }

        @Override
        public ProductOrder[] newArray(int size) {
            return new ProductOrder[size];
        }
    };

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public long getOrderedQty() {
        return orderedQty;
    }

    public void setOrderedQty(long orderedQty) {
        this.orderedQty = orderedQty;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }


    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    @Override
    public String toString() {
        return "ProductOrder{" +
                "id=" + id +
                ", orderedQty=" + orderedQty +
                ", product=" + product +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        if (id == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeLong(id);
        }
        dest.writeLong(orderedQty);
        dest.writeDouble(price);
        dest.writeParcelable(product, flags);
    }
}
