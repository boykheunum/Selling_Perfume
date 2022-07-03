package com.example.sellingperfume.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "bill")
public class BillEntity extends AbtractEntity {
    @NotBlank
    @NotNull
    @Column(name = "Username", columnDefinition = "nvarchar(50)")
    private String username;

    @Column(name = "GrandTotal")
    private float grandTotal;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public float getGrandTotal() {
        return grandTotal;
    }

    public void setGrandTotal(float grandTotal) {
        this.grandTotal = grandTotal;
    }
}
