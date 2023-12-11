package com.shuaiwu.wsbook.utils;

import io.minio.*;
import io.minio.errors.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.InputStreamReader;
import javax.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * minio工具类 2023-12-10 14:09
 */
@Slf4j
@Component
public class MinioUtil {

    @Value("${minio.url}")
    private String miniourlT;

    @Value("${minio.user}")
    private String miniouserT;

    @Value("${minio.password}")
    private String miniopasswordT;

    private static String miniourl;
    private static String miniouser;
    private static String miniopassword;


    @PostConstruct
    public void init(){
        miniourl = this.miniourlT;
        miniouser = this.miniouserT;
        miniopassword = this.miniopasswordT;
    }

    public static void main(String[] args) throws IOException {
        String gbk = HttpUtil.getNotSSL("http://127.0.0.1:9000/book/31028/40532074.txt", "", null, "UTF-8");
        System.out.println(gbk);
    }

    /**
     *
     * @param bucket
     * @param objName
     * @return
     */
    public static InputStream getObject(String bucket, String objName)
        throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        InputStream inputStream = createClient().getObject(GetObjectArgs.builder()
            .bucket(bucket)
            .object(objName)
            .build());
        return inputStream;
    }

    /**
     * @param bucket  存储在minio的目录
     * @param objName 存储在minio的文件名
     * @param is      流
     */
    public static boolean putObject(String bucket, String objName, InputStream is) {
        try {
            createClient().putObject(PutObjectArgs.builder()
                .bucket(bucket)
                .object(objName)
                .stream(is, is.available(), -1)
                .build());
        } catch (Exception e) {
            log.error("", e);
            return false;
        }
        return true;
    }

    private static MinioClient createClient() {
        return MinioClient.builder()
            .endpoint(miniourl)
            .credentials(miniouser, miniopassword)
            .build();
    }

    private static MinioClient createBucket(String bucket)
        throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        MinioClient minioClient = createClient();
        boolean exists = minioClient.bucketExists(
            BucketExistsArgs.builder().bucket(bucket).build());
        if (!exists) {
            minioClient.makeBucket(MakeBucketArgs.builder().bucket(bucket).build());
        }
        return minioClient;
    }
}
