package com.jbnjr.inventorysystem.model.customerinvoice;

import com.jbnjr.inventorysystem.model.customer.Customer;

import java.util.Date;
import java.util.List;

public class CustomerInvoice {
    private Long id;
    private double amountDue;
    private Date date;
    private double amountTendered;

    private Customer customer;

    private List<ProductOrder> productOrderList;


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
        for(ProductOrder productOrder : getProductOrderList()) {
            amountDue += productOrder.getPrice() * productOrder.getOrderedQty() ;
        }

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

}
