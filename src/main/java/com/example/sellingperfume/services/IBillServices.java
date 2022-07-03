package com.example.sellingperfume.services;

import com.example.sellingperfume.entity.BillEntity;

import java.util.List;
import java.util.Optional;

public interface IBillServices {
    public BillEntity CreateBill(BillEntity billEntity);

    public List<BillEntity> getAllBill();

    public List<BillEntity> getAllBillByUser();

    public Optional<BillEntity> getBillById(Long id);

    public void deleteBill(BillEntity billEntity);
}
