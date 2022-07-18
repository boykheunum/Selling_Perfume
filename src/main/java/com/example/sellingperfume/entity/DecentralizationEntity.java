package com.example.sellingperfume.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "decentralization")
public class DecentralizationEntity extends AbtractEntity {
    @NotBlank
    @NotNull
    @Column(name = "Username", columnDefinition = "nvarchar(50)", unique = true)
    private String username;

    @Column(name = "TableName", columnDefinition = "nvarchar(50)")
    private String tableName;

    @Column(name = "create", columnDefinition = "boolean default 0")
    private boolean create;
    @Column(name = "delete", columnDefinition = "boolean default 0")
    private boolean delete;
    @Column(name = "update", columnDefinition = "boolean default 0")
    private boolean update;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public boolean isCreate() {
        return create;
    }

    public void setCreate(boolean create) {
        this.create = create;
    }

    public boolean isDelete() {
        return delete;
    }

    public void setDelete(boolean delete) {
        this.delete = delete;
    }

    public boolean isUpdate() {
        return update;
    }

    public void setUpdate(boolean update) {
        this.update = update;
    }
}
