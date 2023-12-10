package com.shuaiwu.wsbook.utils;

import io.minio.*;
import io.minio.errors.*;
import lombok.extern.slf4j.Slf4j;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

/**
 * minio工具类
 * 2023-12-10 14:09
 */
@Slf4j
public class MinioUtil {

    public static void main(String[] args) throws IOException, ServerException, InsufficientDataException, ErrorResponseException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        MinioClient minioClient = MinioClient.builder()
                .endpoint("http://127.0.0.1:9000")
                .credentials("minio", "2wsx#EDC")
                .build();
        boolean exists = minioClient.bucketExists(BucketExistsArgs.builder().bucket("image").build());
        if (!exists){
            minioClient.makeBucket(MakeBucketArgs.builder().bucket("image").build());
        }else {
            log.info("bucket image is exists");
        }
        String s = "C:\\Users\\wslio\\Pictures\\golang.png";

        InputStream inputStream = new FileInputStream(s);
        minioClient.putObject(PutObjectArgs.builder()
                .bucket("image")
                .object("golang2.png")
                .stream(inputStream, inputStream.available(), -1)
                .build());
        log.info(s + " is succ upload, bucket image, file golang.png");
    }
}
