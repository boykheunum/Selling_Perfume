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
public class UserServicesImpl implements IUserServices, IMediaServices {
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
        String passInDB = Decryption(userEntity.getPassword());
        logger.info("check login"+password);
        if(passInDB.equals(password)){
            return true;
        }
        return false;
    }

    @Override
    public void uploadFile(String path, MultipartFile multipartFile) {
        File rootPathFile = new File(path);
        if (rootPathFile.exists() == false) {
            rootPathFile.mkdir();
        }
        List<File> uploadFile = new ArrayList<File>();
        String fileName = multipartFile.getOriginalFilename();
        File fileServer = new File(rootPathFile.getAbsoluteFile() + File.separator + fileName);
        try {
            BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(new FileOutputStream((fileServer)));
            bufferedOutputStream.write(multipartFile.getBytes());
            bufferedOutputStream.close();
            uploadFile.add(fileServer);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void genaratePublicAndPrivatekey() throws IOException {
        KeyPairGenerator kpg = null;
        try {
            kpg = KeyPairGenerator.getInstance("RSA");
            kpg.initialize(1024);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        KeyPair kp = kpg.genKeyPair();
        PublicKey publicKey = kp.getPublic();
        File publicKeyFile = createKeyFile(new File("D:/hoctap/ProjectJava/Selling_Perfume/RSA/publicKey.rsa"));
        FileOutputStream fos = new FileOutputStream(publicKeyFile.getAbsolutePath());
        fos.write(publicKey.getEncoded());
        fos.close();

        PrivateKey privateKey = kp.getPrivate();
        File privateKeyFile = createKeyFile(new File("D:/hoctap/ProjectJava/Selling_Perfume/RSA/privateKey.rsa"));
        fos = new FileOutputStream(privateKeyFile.getAbsolutePath());
        fos.write(privateKey.getEncoded());
        fos.close();

    }

    @Override
    public String Encrpytion(String pass) throws NoSuchPaddingException, NoSuchAlgorithmException, IllegalBlockSizeException, BadPaddingException, InvalidKeyException, IOException, InvalidKeySpecException {
        genaratePublicAndPrivatekey();
        FileInputStream fis = new FileInputStream("D:/hoctap/ProjectJava/Selling_Perfume/RSA/publicKey.rsa");
        byte[] b = new byte[fis.available()];
        fis.read(b);
        fis.close();

        // Tạo public key
        X509EncodedKeySpec spec = new X509EncodedKeySpec(b);
        KeyFactory factory = KeyFactory.getInstance("RSA");
        PublicKey pubKey = factory.generatePublic(spec);

        // Mã hoá dữ liệu
        Cipher c = Cipher.getInstance("RSA");
        c.init(Cipher.ENCRYPT_MODE, pubKey);

        byte encryptOut[] = c.doFinal(pass.getBytes());
        String strEncrypt = Base64.getEncoder().encodeToString(encryptOut);
        return strEncrypt;
    }

    @Override
    public String Decryption(String passCrpytion) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException, IOException, InvalidKeySpecException {

        //doc file chua public key
        FileInputStream fis = new FileInputStream("D:/hoctap/ProjectJava/Selling_Perfume/RSA/privateKey.rsa");
        byte[] b = new byte[fis.available()];
        fis.read(b);
        fis.close();

        // Tạo private key
        PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(b);
        KeyFactory factory = KeyFactory.getInstance("RSA");
        PrivateKey priKey = factory.generatePrivate(spec);

        // Giải mã dữ liệu
        Cipher c = Cipher.getInstance("RSA");
        c.init(Cipher.DECRYPT_MODE, priKey);
        byte decryptOut[] = c.doFinal(Base64.getDecoder().decode(passCrpytion));
        return new String(decryptOut);
    }

    private static File createKeyFile(File file) throws IOException {
        if (!file.exists()) {
            file.createNewFile();
        } else {
            file.delete();
            file.createNewFile();
        }
        return file;
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
}
