package com.example.sellingperfume.services.impl;

import com.example.sellingperfume.services.IMediaServices;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.*;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
@Service
public class MediaServicesImpl implements IMediaServices {

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
        File publicKeyFile = createKeyFile(new File(".//RSA/publicKey.rsa"));
        FileOutputStream fos = new FileOutputStream(publicKeyFile.getAbsolutePath());
        fos.write(publicKey.getEncoded());
        fos.close();

        PrivateKey privateKey = kp.getPrivate();
        File privateKeyFile = createKeyFile(new File(".//RSA/privateKey.rsa"));
        fos = new FileOutputStream(privateKeyFile.getAbsolutePath());
        fos.write(privateKey.getEncoded());
        fos.close();

    }

    @Override
    public String Encrpytion(String pass) throws NoSuchPaddingException, NoSuchAlgorithmException, IllegalBlockSizeException, BadPaddingException, InvalidKeyException, IOException, InvalidKeySpecException {
        genaratePublicAndPrivatekey();
        FileInputStream fis = new FileInputStream(".//RSA/publicKey.rsa");
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
        FileInputStream fis = new FileInputStream(".//RSA/privateKey.rsa");
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
}
