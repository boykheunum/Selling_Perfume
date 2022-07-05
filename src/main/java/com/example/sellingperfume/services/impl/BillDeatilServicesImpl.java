package com.example.sellingperfume.services.impl;

import com.example.sellingperfume.entity.BillDetailEntity;
import com.example.sellingperfume.resposity.IBillDetailResposity;
import com.example.sellingperfume.services.IBillDetailServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BillDeatilServicesImpl implements IBillDetailServices {
    @Autowired
    IBillDetailResposity iBillDetailResposit;
    @Override
    public BillDetailEntity createBillDetail(BillDetailEntity billDetailEntity) {
        return iBillDetailResposit.save(billDetailEntity);
    }

    @Override
    public List<BillDetailEntity> getAllBillDetail() {
        return iBillDetailResposit.findAll();
    }

    @Override
    public List<BillDetailEntity> getAllBillDetailByBillId() {
        return null;
    }
}
