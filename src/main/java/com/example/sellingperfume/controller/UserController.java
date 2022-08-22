package com.example.sellingperfume.controller;

import com.example.sellingperfume.Common.TokenCommon;
import com.example.sellingperfume.DTO.OtpDTO;
import com.example.sellingperfume.entity.UserAuthorityEntity;
import com.example.sellingperfume.entity.UserEntity;
import com.example.sellingperfume.services.impl.MediaServicesImpl;
import com.example.sellingperfume.services.impl.UserAthorityServicesImpl;
import com.example.sellingperfume.services.impl.UserServicesImpl;
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
    private final String TEXT_TOKEN_USER = "tokenInfoUser";

    @Value("${upload.part}")
    private String upload;
    @Autowired
    private UserServicesImpl userServicesImpl;
    private TokenCommon tokenCommon;
    @Autowired
    private MediaServicesImpl mediaServicesImpl;
    @Autowired
    private UserAthorityServicesImpl userAthorityServicesImpl;

    private static Logger logger = LoggerFactory.getLogger(UserController.class);

    @GetMapping(path = "home")
    private ModelAndView Homdepage() {
        return new ModelAndView("home");
    }

    @PostMapping(path = "createRegister")
    private String creatUser(@Valid @ModelAttribute UserEntity userEntity, HttpSession session, @RequestParam("avatars") MultipartFile multipartFile, @RequestParam("OTP") Boolean otp) throws Exception {
        String PathUpload = upload + "/UserAvatar";
        tokenCommon = new TokenCommon();
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
        String tokenInfoUser = tokenCommon.createTokenValue(userEntity.getUsername(), "00000", "true");
        String splitToken = tokenCommon.decryptTokenUser(tokenInfoUser);
        logger.info(splitToken);
        session.setAttribute(TEXT_TOKEN_USER, tokenInfoUser);
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
        tokenCommon = new TokenCommon();
        UserEntity userInfo = userServicesImpl.FindUserByUsername(userLogin.getUsername());
        if (userServicesImpl.checkLogin(userInfo, userLogin.getPassword()) == true) {
            if (userInfo.getActive_otp() == true) {
                session.setAttribute("username", userLogin.getUsername());
                return "OTP";
            }
//            UserAuthorityEntity userAuthorityEntity = userAthorityServicesImpl.findAuthorityForUser(userInfo.getId());
            String TokenInfoUser = tokenCommon.createTokenValue(userInfo.getUsername(), "TODO", "true");
            session.setAttribute(TEXT_TOKEN_USER, TokenInfoUser);
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
            tokenCommon = new TokenCommon();
            if (userServicesImpl.checkOtpCode(String.valueOf(strOtp), userInfo.getSerectKey())) {
                session.setAttribute("isLogin", true);
                UserAuthorityEntity userAuthorityEntity = userAthorityServicesImpl.findAuthorityForUser(userInfo.getId());
                String tokenInfoUser = tokenCommon.createTokenValue(userInfo.getUsername(), "TODO", "TODO");
                logger.info("logintoken: " + tokenInfoUser);
                session.setAttribute(TEXT_TOKEN_USER, tokenInfoUser);
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
    private ModelAndView getListUser(Model model) {
        List<UserEntity> lUser = userServicesImpl.getAllUser();
        model.addAttribute("lUser", lUser);
        return new ModelAndView("User/UserList");
    }

    @GetMapping(path = "deleteAdmin")
    private ModelAndView deleteAdmin(@RequestParam("id") Long id, Model model) {
        UserEntity userEntity = userServicesImpl.finUserById(id);
        userServicesImpl.deleteUser(userEntity);
        return getListUser(model);
    }
}
