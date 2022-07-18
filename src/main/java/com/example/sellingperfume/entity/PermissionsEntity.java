package com.example.sellingperfume.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "permissions")
public class PermissionsEntity extends AbtractEntity{
    private String permissionName;

    public String getPermissionName() {
        return permissionName;
    }

    public void setPermissionName(String permissionName) {
        this.permissionName = permissionName;
    }
}
