package com.softserve.skillscope.amazon_s3;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.stereotype.Service;

import java.io.InputStream;

@Service
@Getter
@AllArgsConstructor
public class S3Service {
    private final String bucketName = "our bucketname";
    private final AmazonS3 s3;

    public void save(String bucketName, String filename, InputStream inputStream) {
        try {
            s3.putObject(bucketName, filename, inputStream, null);
        }
        catch (AmazonServiceException exception) {
            throw new IllegalStateException("Failed to store content to s3", exception);
        }
    }
}
