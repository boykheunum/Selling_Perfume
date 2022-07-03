package com.example.sellingperfume.services.impl;

import com.example.sellingperfume.entity.BillEntity;
import com.example.sellingperfume.resposity.IBillResposity;
import com.example.sellingperfume.services.IBillServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BillServicesImpl implements IBillServices {
    @Autowired
    public IBillResposity iBillResposity;

    @Override
    public BillEntity CreateBill(BillEntity billEntity) {
        return iBillResposity.save(billEntity);
    }

    @Override
    public List<BillEntity> getAllBill() {
        return iBillResposity.findAll();
    }

    @Override
    public List<BillEntity> getAllBillByUser() {
        return null;
    }

    @Override
    public Optional<BillEntity> getBillById(Long id) {
        return iBillResposity.findById(id);
    }

    @Override
    public void deleteBill(BillEntity billEntity) {
        iBillResposity.delete(billEntity);
    }
}
