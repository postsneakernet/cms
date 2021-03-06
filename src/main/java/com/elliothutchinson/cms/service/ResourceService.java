package com.elliothutchinson.cms.service;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;

import javax.servlet.http.HttpServletResponse;

import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

@Service
public class ResourceService {

    public ResourceService() {
    }
    
    public void getResource(String filename, HttpServletResponse response) throws IOException {
        InputStream is = new FileInputStream(filename);
        IOUtils.copy(is, response.getOutputStream());
        response.flushBuffer();
    }

    public boolean saveResource(String filename, MultipartFile file) {
        if (file.isEmpty()) {
            return false;
        }
        try {
            BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(new File(filename)));
            FileCopyUtils.copy(file.getInputStream(), stream);
            stream.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteResource(String filename) {
        File file = new File(filename);

        if (!file.exists()) {
            return true;
        }

        if (backupResource(file) && file.delete()) {
            return true;
        }

        return false;
    }

    public boolean backupResource(File src) {
        // TODO create dir if not exist
        File backup = new File(src.getParent() + "/deleted/" + src.getName());

        try {
            if (backup.exists()) {
                return true;
            }
            Files.copy(src.toPath(), backup.toPath());
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }
}
