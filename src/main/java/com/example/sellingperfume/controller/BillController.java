package com.example.sellingperfume.controller;

import com.example.sellingperfume.entity.BillDetailEntity;
import com.example.sellingperfume.entity.BillEntity;
import com.example.sellingperfume.entity.ProductEntity;
import com.example.sellingperfume.services.impl.BillDeatilServicesImpl;
import com.example.sellingperfume.services.impl.BillServicesImpl;
import com.example.sellingperfume.services.impl.CreateTokenInformationUser;
import com.example.sellingperfume.services.impl.ProductServicesImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

@RestController
public class BillController {
    @Autowired
    public BillDeatilServicesImpl billDeatilServicesimpl;
    @Autowired
    public BillServicesImpl billServicesImpl;
    @Autowired
    public CreateTokenInformationUser createTokenInformationUser;
    @Autowired
    public ProductServicesImpl productServicesImpl;
    private static Logger logger = LoggerFactory.getLogger(BillController.class);

    @PostMapping(path = "payCart")
    public String payCart(HttpSession session, Model model) throws GeneralSecurityException, IOException {
        float grandTotal = 0;
        if (session.getAttribute("TokenInfoUser") != null) {
            String tokenInforUser = (String) session.getAttribute("TokenInfoUser");
            String splitToken = createTokenInformationUser.decryptTokenUser(tokenInforUser);
            if (session.getAttribute("CartSession") != null) {
                Map<String, Integer> Cart = (Map<String, Integer>) session.getAttribute("CartSession");
                if (!Cart.isEmpty()) {
                    Set<String> keys = Cart.keySet();
                    List<ProductEntity> lProductEntity = new ArrayList<>();
                    for (String key : keys) {
                        float totalprice = 0;
                        ProductEntity productEntity = productServicesImpl.findProductByName(key);
                        totalprice = productEntity.getPrice() * Cart.get(key);
                        grandTotal += totalprice;
                        productEntity.setQuantity(productEntity.getQuantity()-Cart.get(key));
                        productServicesImpl.UpdateProduct(productEntity);
                        productEntity.setQuantity(Cart.get(key));
                        lProductEntity.add(productEntity);
                    }
                    BillEntity billEntity = new BillEntity();
                    billEntity.setUsername(splitToken.split(",")[1]);
                    billEntity.setCreateAt(LocalDateTime.now());
                    billEntity.setGrandTotal(grandTotal);
                    billEntity.setCreateBy(splitToken.split(",")[1]);
                    billServicesImpl.CreateBill(billEntity);
                    for(ProductEntity productEntity : lProductEntity){
                        BillDetailEntity billDetailEntity = new BillDetailEntity();
                        billDetailEntity.setBillId(billEntity.getId());
                        billDetailEntity.setPrice(productEntity.getPrice());
                        billDetailEntity.setQuantity(productEntity.getQuantity());
                        billDetailEntity.setProductName(productEntity.getProductName());
                        billDetailEntity.setCreateAt(LocalDateTime.now());
                        billDetailEntity.setCreateBy(splitToken.split(",")[1]);
                        billDeatilServicesimpl.createBillDetail(billDetailEntity);
                    }
                    Cart.clear();
                    logger.info("grandTotal: " + grandTotal);
                }
            }
            return "listDetailBill";
        }
        return "login";
    }
    @GetMapping(path = "listDetailBill")
    public ModelAndView listDetailBill(Model model){
        List<BillDetailEntity> listBillDetail = billDeatilServicesimpl.getAllBillDetail();
        model.addAttribute("listBillDetail",listBillDetail);
        return new ModelAndView("bill/listDetailBill");
    }
    @GetMapping(path="listBill")
    public ModelAndView listBill(Model model){
        List<BillEntity> lBill = billServicesImpl.getAllBill();
        model.addAttribute("lBill", lBill);
        return new ModelAndView("/bill/Listbill");
    }

    @GetMapping(path = "DeleteBill")
    public ModelAndView deleteBillByid(@Param("id") Long id, HttpSession session){
        if(session.getAttribute("TokenInfoUser") != null) {
            if (billServicesImpl.getBillById(id) != null) {
                BillEntity billEntity = billServicesImpl.getBillById(id).get();
                billServicesImpl.deleteBill(billEntity);
                return new ModelAndView("/bill/listBill");
            }
        }
        return new ModelAndView("login");
    }
}
