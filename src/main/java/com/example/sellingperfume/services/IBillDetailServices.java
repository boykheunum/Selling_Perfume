package com.example.sellingperfume.services;

import com.example.sellingperfume.entity.BillDetailEntity;
import com.example.sellingperfume.entity.BillEntity;

import java.util.List;

public interface IBillDetailServices {
    public BillDetailEntity createBillDetail(BillDetailEntity billDetailEntity);

    public List<BillDetailEntity>getAllBillDetail();

    public List<BillDetailEntity>getAllBillDetailByBillId();

}
