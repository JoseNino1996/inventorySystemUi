package com.jbnjr.inventorysystem.model.customerinvoice;

import android.os.Parcel;
import android.os.Parcelable;

import com.jbnjr.inventorysystem.model.customer.Customer;

import java.util.Date;
import java.util.List;

public class CustomerInvoice  implements Parcelable {
    private Long id;
    private double amountDue;
    private Date date;
    private double amountTendered;

    private Customer customer;

    private List<ProductOrder> productOrderList;

    public  CustomerInvoice() {}
    protected CustomerInvoice(Parcel in) {
        if (in.readByte() == 0) {
            id = null;
        } else {
            id = in.readLong();
        }
        amountDue = in.readDouble();
        amountTendered = in.readDouble();
        customer = in.readParcelable(Customer.class.getClassLoader());
        productOrderList = in.createTypedArrayList(ProductOrder.CREATOR);
    }

    public static final Creator<CustomerInvoice> CREATOR = new Creator<CustomerInvoice>() {
        @Override
        public CustomerInvoice createFromParcel(Parcel in) {
            return new CustomerInvoice(in);
        }

        @Override
        public CustomerInvoice[] newArray(int size) {
            return new CustomerInvoice[size];
        }
    };

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public void setAmountDue(double amountDue) {
        this.amountDue = amountDue;
    }

    public Date getDate() {

        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Customer getCustomer() {
        return customer;
    }

    public double getAmountDue() {


        return amountDue;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public List<ProductOrder> getProductOrderList() {
        return productOrderList;
    }

    public void setProductOrderList(List<ProductOrder> productOrderList) {
        this.productOrderList = productOrderList;
    }


    public double getAmountTendered() {
        return amountTendered;
    }

    public void setAmountTendered(double amountTendered) {
        this.amountTendered = amountTendered;
    }



    @Override
    public String toString() {
        return "CustomerInvoice{" +
                "id=" + id +
                ", amountDue=" + amountDue +
                ", date=" + date +
                ", customer=" + customer +
                ", productOrderList=" + productOrderList +
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
        dest.writeDouble(amountDue);
        dest.writeDouble(amountTendered);
        dest.writeParcelable(customer, flags);
        dest.writeTypedList(productOrderList);
    }
}
