package com.example.sellingperfume.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "product")
public class ProductEntity extends AbtractEntity{
    @Column(name = "catagory_id")
    private Long catagory_id;

    @Column(name = "product_name", columnDefinition = "nvarchar(50)", unique = true)
    private String productName;

    @Column(name="image", columnDefinition = "nvarchar(255)")
    private String image;

    @Column(name="input_price", columnDefinition = "float")
    private float inputPrice;

    @Column(name="price", columnDefinition = "float")
    private float price;

    @Column(name = "describe_product", columnDefinition = "varchar(500)")
    private String describe;

    @Column(name="quantity", columnDefinition = "int")
    private int quantity;

    public Long getCatagory_id() {
        return catagory_id;
    }

    public void setCatagory_id(Long catagory_id) {
        this.catagory_id = catagory_id;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public float getInputPrice() {
        return inputPrice;
    }

    public void setInputPrice(float inputPrice) {
        this.inputPrice = inputPrice;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
