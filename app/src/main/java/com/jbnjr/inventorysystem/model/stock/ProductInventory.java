package com.jbnjr.inventorysystem.model.stock;

import android.os.Parcel;
import android.os.Parcelable;

import com.jbnjr.inventorysystem.model.product.Product;

public class ProductInventory implements Parcelable {
    private Long id;
    private double price;
    private long quantity;

    private Product product;

    protected ProductInventory(Parcel in) {
        if (in.readByte() == 0) {
            id = null;
        } else {
            id = in.readLong();
        }
        price = in.readDouble();
        quantity = in.readLong();
        product = in.readParcelable(Product.class.getClassLoader());
    }



    public static final Creator<ProductInventory> CREATOR = new Creator<ProductInventory>() {
        @Override
        public ProductInventory createFromParcel(Parcel in) {
            return new ProductInventory(in);
        }

        @Override
        public ProductInventory[] newArray(int size) {
            return new ProductInventory[size];
        }
    };

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public long getQuantity() {
        return quantity;
    }

    public void setQuantity(long quantity) {
        this.quantity = quantity;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    @Override
    public String toString() {
        return "ProductInventory{" +
                "id=" + id +
                ", price=" + price +
                ", quantity=" + quantity +
                ", product=" + product +
                '}';
    }

    public ProductInventory(Long id, double price, long quantity, Product product) {
        this.id = id;
        this.price = price;
        this.quantity = quantity;
        this.product = product;
    }

    public ProductInventory(){ }
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
        dest.writeDouble(price);
        dest.writeLong(quantity);
        dest.writeParcelable(product, flags);
    }
}
