package com.example.sellingperfume.services;

import com.example.sellingperfume.entity.PermissionsEntity;
import com.example.sellingperfume.resposity.PermissionResposity;

import java.util.List;
import java.util.Optional;

public interface IPermissionServices {
    public PermissionsEntity createPermissions(PermissionsEntity permissions);

    public PermissionsEntity updatePermissions(PermissionsEntity permissions);

    public StringBuilder convertTotalPermissionToString();//chuyen so luong bang = so luong so 0 co trong chuoi

    public Optional<PermissionsEntity> getPermissionsById(Long id);

    public String convertPermissionsStringToID(String per_id);

    public List<PermissionsEntity> checkButtonSystemAdminCanUse(List<PermissionsEntity> lPermissions, int id);//lay danh sach system admin duoc su dung

    public List<Integer> convertStringTotalRowToArray(String str);//kiem tra admin duoc su dung nhung system nao

}
