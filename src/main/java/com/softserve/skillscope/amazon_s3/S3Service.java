package com.softserve.skillscope.amazon_s3;

import java.io.InputStream;
import java.util.List;

public interface S3Service {
    void save(String bucketName, String filename, InputStream inputStream);
    byte[] download(String filename);
    String deleteFile(String filename);
    List<String> listAllFiles();
}
