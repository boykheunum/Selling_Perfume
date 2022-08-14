package com.example.sellingperfume.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="authority")
public class AuthorityEntity extends AbtractEntity{
    @Column(name = "AuthorityName")
    private String authorityName;
}
