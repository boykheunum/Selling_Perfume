package com.example.sellingperfume.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "billdetail")
public class BillDetailEntity extends AbtractEntity {
    @Column(name = "product_name", columnDefinition = "nvarchar(50)")
    private String productName;

    @Column(name = "quantity", columnDefinition = "int")
    private int quantity;

    @Column(name = "bill_id")
    private long billId;

    @Column(name = "price", columnDefinition = "float")
    private float price;

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public long getBillId() {
        return billId;
    }

    public void setBillId(long billId) {
        this.billId = billId;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }
}
