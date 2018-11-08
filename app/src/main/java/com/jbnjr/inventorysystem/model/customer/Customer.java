package com.jbnjr.inventorysystem.model.customer;

import android.os.Parcel;
import android.os.Parcelable;


public class Customer implements  Parcelable{


    private long id;
    private String name;
    private String address;
    private Long contactNumber;

    protected Customer(Parcel in) {
        id = in.readLong();
        name = in.readString();
        address = in.readString();
        contactNumber = in.readLong();
    }

    public Customer(long id, String name, String address, long contactNumber) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.contactNumber = contactNumber;
    }

    public Customer() { }

    public static final Parcelable.Creator<Customer> CREATOR = new Parcelable.Creator<Customer>() {
        @Override
        public Customer createFromParcel(Parcel in) {
            return new Customer(in);
        }

        @Override
        public Customer[] newArray(int size) {
            return new Customer[size];
        }
    };

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public long getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(long contactNumber) {
        this.contactNumber = contactNumber;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(id);
        dest.writeString(name);
        dest.writeString(address);
        dest.writeLong(contactNumber);
    }

    @Override
    public String toString() {
        return name;
    }
}
