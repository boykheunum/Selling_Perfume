package com.example.sellingperfume.services.impl;

import com.example.sellingperfume.entity.UserEntity;
import com.example.sellingperfume.resposity.IUserResposity;
import com.example.sellingperfume.services.IUserServices;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import org.apache.commons.codec.binary.Base32;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.text.DecimalFormat;
import java.util.Base64;
import java.util.List;
import java.util.Random;

@Service
public class UserServicesImpl implements IUserServices {
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

    @Override
    public String createSerectKey() {
        Base32 base32 = new Base32();
        return base32.encode(new DecimalFormat("000000").format(new Random().nextInt(999999)).getBytes(StandardCharsets.UTF_8)).toString();
    }

    @Override
    public byte[] createQRCode(String serectKey, String Username) {
        String data = String.format("otpauth://totp/%s:%s?secret=%s&issuer=%s","SellingPerfume", Username, serectKey, "Authenticator");
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

}
