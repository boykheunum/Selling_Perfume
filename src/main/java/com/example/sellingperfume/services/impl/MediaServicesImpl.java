package com.example.sellingperfume.services.impl;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.ArrayList;
import java.util.List;


public abstract class MediaServicesImpl {
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
}
