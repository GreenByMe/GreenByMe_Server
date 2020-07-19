package org.greenbyme.angelhack.util;

import org.springframework.context.annotation.Configuration;

@Configuration
public class FileUploadProperties {
    private String uploadDir = "./uploads";
 
    public String getUploadDir() {
        return uploadDir;
    }
 
    public void setUploadDir(String uploadDir) {
        this.uploadDir = uploadDir;
    }
}