package com.example.sellingperfume.controller;

import com.example.sellingperfume.DTO.OtpDTO;
import com.example.sellingperfume.entity.AuthorityEntity;
import com.example.sellingperfume.entity.UserAuthorityEntity;
import com.example.sellingperfume.entity.UserEntity;
import com.example.sellingperfume.services.impl.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import java.time.LocalDateTime;
import java.util.List;

@RestController
public class UserController {
    @Value("${upload.part}")
    private String upload;
    @Autowired
    private UserServicesImpl userServicesImpl;
    @Autowired
    private CreateTokenInformationUser createTokenInformationUser;
    @Autowired
    private MediaServicesImpl mediaServicesImpl;
    @Autowired
    private UserAthorityServicesImpl userAthorityServicesImpl;

    @GetMapping(path = "home")
    private ModelAndView Homdepage() {
        return new ModelAndView("home");
    }

    private static Logger logger = LoggerFactory.getLogger(UserController.class);

    @PostMapping(path = "createRegister")
    private String creatUser(@Valid @ModelAttribute UserEntity userEntity, HttpSession session, @RequestParam("avatars") MultipartFile multipartFile, @RequestParam("OTP") Boolean otp) throws Exception {
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
        String tokenInfoUser = createTokenInformationUser.createTokenValue(userEntity.getUsername(),"tam chua co", "TODO");
        String splitToken = createTokenInformationUser.decryptTokenUser(tokenInfoUser);
        logger.info(splitToken);
        session.setAttribute("TokenInfoUser", tokenInfoUser);
        return userServicesImpl.createUser(userEntity).toString();
    }

    @GetMapping(path = "register")
    private ModelAndView register() {
        return new ModelAndView("Register");
    }

    @GetMapping(path = "login")
    private ModelAndView Login() {
        return new ModelAndView("Login");
    }

    @PostMapping(path = "userLogin")
    private String userLogin(@RequestBody UserEntity userLogin, HttpSession session) throws Exception {
        UserEntity userInfo = userServicesImpl.FindUserByUsername(userLogin.getUsername());
        if (userServicesImpl.checkLogin(userInfo, userLogin.getPassword()) == true) {
            if (userInfo.getActive_otp() == true) {
                session.setAttribute("username", userLogin.getUsername());
                return "OTP";
            }
            UserAuthorityEntity userAuthorityEntity = userAthorityServicesImpl.findAuthorityForUser(userInfo.getId());
            String authorityStr = userAthorityServicesImpl.checkAuthority(userAuthorityEntity).toString();
            String TokenInfoUser = createTokenInformationUser.createTokenValue(userInfo.getUsername(), authorityStr, "TODO");
            session.setAttribute("TokenInfoUser",TokenInfoUser);
            session.setAttribute("isLogin", true);
            return "homepage";
        }
        return "login";
    }

    @GetMapping(path = "OTP")
    private ModelAndView OTP(HttpSession session) {
        try {
            String username = session.getAttribute("username").toString();
            logger.info(username);
        } catch (NullPointerException ex) {
            return new ModelAndView("Login");
        }
        return new ModelAndView("OTP");
    }

    @PostMapping(path = "chekOTP")
    private String checkOTP(@RequestBody OtpDTO otpDTO, HttpSession session) {
        try {
            String username = session.getAttribute("username").toString();
            StringBuilder strOtp = new StringBuilder();
            strOtp.append(otpDTO.getFirst()).append(otpDTO.getSecond()).append(otpDTO.getThird()).append(otpDTO.getFourth()).append(otpDTO.getFifth()).append(otpDTO.getSixth());
            UserEntity userInfo = userServicesImpl.FindUserByUsername(username);
//            session.setAttribute("username", null);

            if (userServicesImpl.checkOtpCode(String.valueOf(strOtp), userInfo.getSerectKey())) {
                session.setAttribute("isLogin", true);
                UserAuthorityEntity userAuthorityEntity = userAthorityServicesImpl.findAuthorityForUser(userInfo.getId());
                String authorityStr = userAthorityServicesImpl.checkAuthority(userAuthorityEntity).toString();
                String tokenInfoUser = createTokenInformationUser.createTokenValue(userInfo.getUsername(), authorityStr, "TODO");
                logger.info("logintoken: "+tokenInfoUser);
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

    @GetMapping(path = "listUser")
    private ModelAndView getListUser(Model model){
        List<UserEntity> lUser = userServicesImpl.getAllUser();
        model.addAttribute("lUser", lUser);
        return new ModelAndView("User/UserList");
    }

    @GetMapping(path = "deleteAdmin")
    private ModelAndView deleteAdmin(@RequestParam("id")Long id, Model model){
        UserEntity userEntity = userServicesImpl.finUserById(id);
        userServicesImpl.deleteUser(userEntity);
        return getListUser(model);
    }
}
