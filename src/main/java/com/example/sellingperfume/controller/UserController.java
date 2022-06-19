package com.example.sellingperfume.controller;

import com.example.sellingperfume.DTO.OtpDTO;
import com.example.sellingperfume.entity.CategoryEntity;
import com.example.sellingperfume.entity.UserEntity;
import com.example.sellingperfume.services.impl.CategoryServicesImpl;
import com.example.sellingperfume.services.impl.CreateTokenInformationUser;
import com.example.sellingperfume.services.impl.UserServicesImpl;
import com.mysql.cj.Session;
import org.apache.catalina.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.bind.annotation.ModelAttribute;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.time.LocalDateTime;
import java.util.List;

@RestController
public class UserController {
    @Value("${upload.part}")
    public String upload;
    @Autowired
    public UserServicesImpl userServicesImpl;
    @Autowired
    public CategoryServicesImpl categoryServicesImpl;
    @Autowired
    public CreateTokenInformationUser createTokenInformationUser;

    @GetMapping(path = "home")
    public ModelAndView Homdepage() {
        return new ModelAndView("home");
    }

    private static Logger logger = LoggerFactory.getLogger(UserController.class);

    @PostMapping(path = "createRegister")
    public String creatUser(@Valid @ModelAttribute UserEntity userEntity, HttpSession session, @RequestParam("avatars") MultipartFile multipartFile, @RequestParam("OTP") Boolean otp) throws Exception {
        String PathUpload = upload + "/UserAvatar";
        if(!multipartFile.isEmpty()){
            userServicesImpl.uploadFile(PathUpload, multipartFile);
            userEntity.setAvatar(multipartFile.getOriginalFilename());
        }
        logger.info(userServicesImpl.Encrpytion(userEntity.getPassword()));
        userEntity.setPassword(userServicesImpl.Encrpytion(userEntity.getPassword()));

        logger.info(userServicesImpl.Decryption(userEntity.getPassword()));
        userEntity.setCreateAt(LocalDateTime.now());
        userEntity.setCreateBy(userEntity.getUsername());
        if (otp == true) {
            userEntity.setActive_otp(otp);
            userEntity.setSerectKey(userServicesImpl.createSerectKey());
            logger.info("serectkey"+userEntity.getSerectKey());
            byte[] imageQR = userServicesImpl.createQRCode(userEntity.getUsername(), userEntity.getSerectKey());
            userServicesImpl.ConvertByteToImage(imageQR,upload+"/QRImage",userEntity.getUsername());
        }
        session.setAttribute("isLogin",true);
        session.setAttribute("username", userEntity.getUsername());
        String tokenInfoUser = createTokenInformationUser.createTokenValue(userEntity.getUsername(),userEntity.getAcountable_user());
//        logger.info("token user: "+createTokenInformationUser.createTokenValue(userEntity.getUsername(),userEntity.getAcountable_user()));
//        String splitToken = createTokenInformationUser.decryptTokenUser(tokenInfoUser);
//        logger.info(splitToken);
        session.setAttribute("TokenInfoUser", tokenInfoUser);
        return userServicesImpl.createUser(userEntity).toString();
    }

    @GetMapping(path = "register")
    public ModelAndView register() {
        return new ModelAndView("Register");
    }

    @GetMapping(path = "listUser")
    public List<UserEntity> listUser() {
        List<UserEntity> listUser = userServicesImpl.getAllUser();
        return listUser;
    }

    @PostMapping(path = "createCategory")
    public CategoryEntity createCategory(@RequestBody CategoryEntity categoryEntity) {
        return categoryServicesImpl.CreateCategory(categoryEntity);
    }

    @GetMapping(path="login")
    public ModelAndView Login(){
        return new ModelAndView("Login");
    }

    @PostMapping(path="userLogin")
    public ModelAndView userLogin(@RequestBody UserEntity userLogin) throws NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, IOException, InvalidKeySpecException, InvalidKeyException {
        UserEntity userInfo = userServicesImpl.FindUserByUsername(userLogin.getUsername());
        logger.info(userInfo.getPassword());
        if(userServicesImpl.checkLogin(userInfo,userLogin.getPassword())==true){
            if(userInfo.getActive_otp()==true){
                return new ModelAndView("OTP");
            }
        }else{
            logger.info("thatbai");
        }
        return null;
    }

    @GetMapping(path="OTP")
    public ModelAndView OTP(){
        return new ModelAndView("OTP");
    }

    @PostMapping(path="chekOTP")
    public boolean checkOTP(@RequestBody OtpDTO otpDTO){
        StringBuilder strOtp = null;
        logger.info(otpDTO.getSixth());
        strOtp.append(otpDTO.getFirst()).append(otpDTO.getSecond()).append(otpDTO.getThird()).append(otpDTO.getFourth()).append(otpDTO.getFifth()).append(otpDTO.getSixth());
        return false;
    }
}
