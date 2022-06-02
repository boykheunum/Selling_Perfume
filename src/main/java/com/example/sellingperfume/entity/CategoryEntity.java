package com.example.sellingperfume.entity;

import javax.persistence.*;

@Entity
@Table(name = "category")
public class CategoryEntity extends AbtractEntity{
    @Column(name = "name", columnDefinition = "nvarchar(50)")
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
