package com.example.sellingperfume.services.impl;

import com.example.sellingperfume.controller.UserController;
import com.example.sellingperfume.entity.UserEntity;
import com.example.sellingperfume.resposity.IUserResposity;
import com.example.sellingperfume.services.IMediaServices;
import com.example.sellingperfume.services.IUserServices;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import org.apache.commons.codec.binary.Base32;
import org.apache.commons.lang3.RandomStringUtils;
import org.jboss.aerogear.security.otp.Totp;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.imageio.ImageIO;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.text.DecimalFormat;
import java.util.*;

@Service
public class UserServicesImpl implements IUserServices {
    private static Logger logger = LoggerFactory.getLogger(UserServicesImpl.class);
    @Autowired
    public IUserResposity iUserResposity;

    @Override
    public List<UserEntity> getAllUser() {
        return iUserResposity.findAll();
    }

    @Override
    public UserEntity createUser(UserEntity userEntity) {
        return iUserResposity.save(userEntity);
    }

    @Autowired
    public MediaServicesImpl mediaServicesImpl;

    @Override
    public String createSerectKey() {
        Base32 base32 = new Base32();
        return base32.encodeAsString(RandomStringUtils.randomAlphabetic(10).getBytes());
    }

    @Override
    public byte[] createQRCode(String serectKey, String Username) {
        String data = String.format("otpauth://totp/%s:%s?secret=%s&issuer=%s", "SellingPerfume", Username, serectKey, "Authenticator");
        return confiRQCode(data, 300, 300);
    }

    @Override
    public byte[] confiRQCode(String data, int width, int height) {
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        try {
            BitMatrix bitMatrix = qrCodeWriter.encode(data, BarcodeFormat.QR_CODE, width, height);
            ByteArrayOutputStream outPutStream = new ByteArrayOutputStream();
            MatrixToImageWriter.writeToStream(bitMatrix, "png", outPutStream);
            return outPutStream.toByteArray();
        } catch (WriterException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public UserEntity FindUserByUsername(String usName) {
        UserEntity user = iUserResposity.getUserByUserName(usName);
        return user;
    }

    @Override
    public boolean checkLogin(UserEntity userEntity, String password) throws NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, IOException, InvalidKeySpecException, InvalidKeyException {
        String passInDB = mediaServicesImpl.Decryption(userEntity.getPassword());
        logger.info("check login"+password);
        if(passInDB.equals(password)){
            return true;
        }
        return false;
    }

    @Override
    public boolean checkOtpCode(String otpCode, String serectKey) {
        Totp totp = new Totp(serectKey);
        if(totp.verify(otpCode)){
            return true;
        }
        return false;
    }

    @Override
    public Optional<UserEntity> finUserById(Long id) {
        return iUserResposity.findById(id);
    }

    @Override
    public void deleteUser(UserEntity userEntity) {
        iUserResposity.delete(userEntity);
    }

    public ImageIO ConvertByteToImage(byte[] image, String path, String fileName) {
        // TODO Auto-generated method stub
        ByteArrayInputStream bis = new ByteArrayInputStream(image);
        BufferedImage bImage2;
        File pathFile = new File(path);
        if (!pathFile.exists()) {
            pathFile.mkdir();
        }
        try {
            bImage2 = ImageIO.read(bis);
            ImageIO.write(bImage2, "jpg", new File(pathFile.getAbsoluteFile() + File.separator + fileName + ".png"));
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public StringBuilder ShowButtonAdmin(String str) {
//        StringBuilder adminDecentralizationButton = null;
//        if(str != null && !str.isEmpty()) {
//            String arr[] = str.split("\\w");
//            for (int i = 1; i < arr.length; i++) {
//                if (arr[i].equals("1")) {
//                    adminDecentralizationButton.append(i);
//                }
//            }
//        }
//        return adminDecentralizationButton;
        return null;
    }

    private StringBuilder checkShowButtonAdmin(String str){
        StringBuilder adminDecentralizationButton = null;
        if(str != null && !str.isEmpty()) {
            String arr[] = str.split("\\w");
            for (int i = 1; i < arr.length; i++) {
                if (arr[i].equals("1")) {
                    adminDecentralizationButton.append(i);
                }
            }
        }
        return adminDecentralizationButton;
    }
}
