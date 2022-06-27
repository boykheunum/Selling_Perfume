package com.example.sellingperfume.controller;

import com.example.sellingperfume.DTO.OtpDTO;
import com.example.sellingperfume.entity.CategoryEntity;
import com.example.sellingperfume.entity.UserEntity;
import com.example.sellingperfume.services.impl.CategoryServicesImpl;
import com.example.sellingperfume.services.impl.CreateTokenInformationUser;
import com.example.sellingperfume.services.impl.MediaServicesImpl;
import com.example.sellingperfume.services.impl.UserServicesImpl;
import com.mysql.cj.Session;
import org.apache.catalina.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
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
import java.net.URI;
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

    @Autowired
    public MediaServicesImpl mediaServicesImpl;

    @GetMapping(path = "home")
    public ModelAndView Homdepage() {
        return new ModelAndView("home");
    }

    private static Logger logger = LoggerFactory.getLogger(UserController.class);

    @PostMapping(path = "createRegister")
    public String creatUser(@Valid @ModelAttribute UserEntity userEntity, HttpSession session, @RequestParam("avatars") MultipartFile multipartFile, @RequestParam("OTP") Boolean otp) throws Exception {
        String PathUpload = upload + "/UserAvatar";
        if (!multipartFile.isEmpty()) {
            mediaServicesImpl.uploadFile(PathUpload, multipartFile);
            userEntity.setAvatar(multipartFile.getOriginalFilename());
        }
        logger.info(mediaServicesImpl.Encrpytion(userEntity.getPassword()));
        userEntity.setPassword(mediaServicesImpl.Encrpytion(userEntity.getPassword()));

        logger.info(mediaServicesImpl.Decryption(userEntity.getPassword()));
        userEntity.setCreateAt(LocalDateTime.now());
        userEntity.setCreateBy(userEntity.getUsername());
        if (otp == true) {
            userEntity.setActive_otp(otp);
            userEntity.setSerectKey(userServicesImpl.createSerectKey());
            logger.info("serectkey" + userEntity.getSerectKey());
            byte[] imageQR = userServicesImpl.createQRCode(userEntity.getUsername(), userEntity.getSerectKey());
            userServicesImpl.ConvertByteToImage(imageQR, upload + "/QRImage", userEntity.getUsername());
        }
        session.setAttribute("isLogin", true);
        session.setAttribute("username", userEntity.getUsername());
        String tokenInfoUser = createTokenInformationUser.createTokenValue(userEntity.getUsername(), userEntity.getAcountable_user());
        logger.info("token user: "+createTokenInformationUser.createTokenValue(userEntity.getUsername(),userEntity.getAcountable_user()));
        String splitToken = createTokenInformationUser.decryptTokenUser(tokenInfoUser);
        logger.info(splitToken);
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

    @GetMapping(path = "login")
    public ModelAndView Login() {
        return new ModelAndView("Login");
    }

    @PostMapping(path = "userLogin")
    public String userLogin(@RequestBody UserEntity userLogin, HttpSession session) throws Exception {
        UserEntity userInfo = userServicesImpl.FindUserByUsername(userLogin.getUsername());
        if (userServicesImpl.checkLogin(userInfo, userLogin.getPassword()) == true) {
            if (userInfo.getActive_otp() == true) {
                session.setAttribute("username", userLogin.getUsername());
                return "OTP";
            }
            String TokenInfoUser = createTokenInformationUser.createTokenValue(userInfo.getUsername(), userInfo.getAcountable_user());
            session.setAttribute("TokenInfoUser",TokenInfoUser);
            session.setAttribute("isLogin", true);
            return "homepage";
        }
        return "login";
    }

    @GetMapping(path = "OTP")
    public ModelAndView OTP(HttpSession session) {
        try {
            String username = session.getAttribute("username").toString();
            logger.info(username);
        } catch (NullPointerException ex) {
            return new ModelAndView("Login");
        }
        return new ModelAndView("OTP");
    }

    @PostMapping(path = "chekOTP")
    public String checkOTP(@RequestBody OtpDTO otpDTO, HttpSession session) {
        try {
            String username = session.getAttribute("username").toString();
            StringBuilder strOtp = new StringBuilder();
            strOtp.append(otpDTO.getFirst()).append(otpDTO.getSecond()).append(otpDTO.getThird()).append(otpDTO.getFourth()).append(otpDTO.getFifth()).append(otpDTO.getSixth());
            UserEntity userInfo = userServicesImpl.FindUserByUsername(username);
            session.setAttribute("username", null);
            if (userServicesImpl.checkOtpCode(String.valueOf(strOtp), userInfo.getSerectKey())) {
                session.setAttribute("isLogin", true);
                String tokenInfoUser = createTokenInformationUser.createTokenValue(userInfo.getUsername(), userInfo.getAcountable_user());
                logger.info("logintoken"+tokenInfoUser);
                session.setAttribute("TokenInfoUser", tokenInfoUser);
                return "homepage";
            }
            return "OTP";
        } catch (NullPointerException ex) {
            return "login";
        } catch (Exception e) {
            return "login";
        }
    }
}
