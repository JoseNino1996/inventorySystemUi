package com.jbnjr.inventorysystem.model.stock;

import java.util.Date;

public class StockLog {
    private Long id;
    private Date date;
    private long addedQuantity;

    private ProductInventory productInventory;
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public long getAddedQuantity() {
        return addedQuantity;
    }

    public void setAddedQuantity(long addedQuantity) {
        this.addedQuantity = addedQuantity;
    }

    public ProductInventory getProductInventory() {
        return productInventory;
    }

    public void setProductInventory(ProductInventory productInventory) {
        this.productInventory = productInventory;
    }

    @Override
    public String toString() {
        return "StockLog{" +
                "id=" + id +
                ", date=" + date +
                ", addedQuantity=" + addedQuantity +
                ", productInventory=" + productInventory +
                '}';
    }
}
