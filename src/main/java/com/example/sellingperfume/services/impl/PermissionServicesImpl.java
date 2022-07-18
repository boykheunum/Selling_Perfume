package com.example.sellingperfume.services.impl;

import com.example.sellingperfume.entity.PermissionsEntity;
import com.example.sellingperfume.resposity.PermissionResposity;
import com.example.sellingperfume.services.IPermissionServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PermissionServicesImpl implements IPermissionServices {
    @Autowired
    private PermissionResposity permissionResposity;
    @Override
    public PermissionsEntity createPermissions(PermissionsEntity permissions) {
        return permissionResposity.save(permissions);
    }

    @Override
    public PermissionsEntity updatePermissions(PermissionsEntity permissions) {
        return permissionResposity.save(permissions);
    }

    private int getTotalPermissions() {
        return permissionResposity.totalPermissions();
    }

    @Override
    public StringBuilder convertTotalPermissionToString() {
        int totalRow = getTotalPermissions();
        if(totalRow==0){
            return null;
        }
        StringBuilder permissionString = new StringBuilder();
        for(int i = 0; i < totalRow; i++){
            permissionString.append('0');
        }
        return permissionString;
    }

    @Override
    public Optional<PermissionsEntity> getPermissionsById(Long id) {
        return permissionResposity.findById(id);
    }

    @Override
    public String convertPermissionsStringToID(String per_id) {
        return null;
    }

    @Override
    public List<PermissionsEntity> checkButtonSystemAdminCanUse(List<PermissionsEntity> lPermissions, int id){
        Long l = new Long(id);
        lPermissions.add(getPermissionsById(l).get());
        return lPermissions;
    }

    @Override
    public List<Integer> convertStringTotalRowToArray(String str) {
        List<Integer> adminDecentralizationButton = new ArrayList<>();
        if(str != null && !str.isEmpty()) {
            String arr[] = str.split("\\w");
            for (int i = 1; i < arr.length; i++) {
                if (arr[i].equals("1")) {
                    adminDecentralizationButton.add(i);
                }
            }
        }
        return adminDecentralizationButton;
    }


}
