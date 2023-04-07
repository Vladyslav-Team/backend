package com.softserve.skillscope.amazon_s3;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ListObjectsV2Result;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.amazonaws.services.s3.model.S3ObjectSummary;
import com.amazonaws.util.IOUtils;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Getter
@AllArgsConstructor
public class S3ServiceImpl implements S3Service {
    private final String bucketName = "vladyslav-team-bucket";
    private final AmazonS3 s3;

    public void save(String bucketName, String filename, InputStream inputStream) {
        try {
            s3.putObject(bucketName, filename, inputStream, null);
        }
        catch (AmazonServiceException exception) {
            throw new IllegalStateException("Failed to store content to s3", exception);
        }
    }
    @Override
    public byte[] download(String filename) {
        S3Object object = s3.getObject(bucketName, filename);
        S3ObjectInputStream objectContent = object.getObjectContent();
        try {
            return IOUtils.toByteArray(objectContent);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String deleteFile(String filename) {
        s3.deleteObject(bucketName, filename);
        return "file deleted";
    }

    @Override
    public List<String> listAllFiles() {
        ListObjectsV2Result listObjectsV2Result = s3.listObjectsV2(bucketName);
        return listObjectsV2Result.getObjectSummaries().stream().map(S3ObjectSummary::getKey).collect(Collectors.toList());
    }
}
