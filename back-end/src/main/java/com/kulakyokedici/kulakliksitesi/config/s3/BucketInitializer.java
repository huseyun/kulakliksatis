package com.kulakyokedici.kulakliksitesi.config.s3;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.CreateBucketRequest;
import software.amazon.awssdk.services.s3.model.HeadBucketRequest;
import software.amazon.awssdk.services.s3.model.NoSuchBucketException;
import software.amazon.awssdk.services.s3.model.PutBucketPolicyRequest;

@Component
public class BucketInitializer {

    private final S3Client s3Client;
    private final StorageProperties storageProperties;
    
    @Autowired
    public BucketInitializer(
    		S3Client s3Client,
    		StorageProperties storageProperties)
    {
    	this.s3Client = s3Client;
    	this.storageProperties = storageProperties;
    }

    @PostConstruct
    public void initBuckets() {
        Map<String, String> buckets = storageProperties.getAllBuckets();

        for (Map.Entry<String, String> entry : buckets.entrySet()) {
            String bucketName = entry.getValue();
            String label = entry.getKey();

            if (bucketExists(bucketName)) {
                System.out.println("Bucket zaten mevcut: " + label + " " + bucketName);
            } else {
                s3Client.createBucket(CreateBucketRequest.builder()
                        .bucket(bucketName)
                        .build());
                System.out.println("Bucket oluşturuldu:  " + label + " " + bucketName);
                
                makeBucketPublic(bucketName);
            }
        }
    }

    private boolean bucketExists(String bucketName) {
        try {
            s3Client.headBucket(HeadBucketRequest.builder()
                    .bucket(bucketName)
                    .build());
            return true;
        } catch (NoSuchBucketException e) {
            return false;
        }
    }
    
    private void makeBucketPublic(String bucketName) {
        // S3'ün anladığı kurallar bütününü (Policy) JSON string'i olarak hazırlıyoruz
        String publicPolicy = "{"
                + "\"Version\":\"2012-10-17\","
                + "\"Statement\":["
                + "  {"
                + "    \"Sid\":\"PublicRead\","
                + "    \"Effect\":\"Allow\"," // İzin Ver
                + "    \"Principal\": \"*\"," // Herkese (İnternetteki herkes)
                + "    \"Action\":[\"s3:GetObject\"]," // Sadece objeleri okuma/indirme yetkisi
                + "    \"Resource\":[\"arn:aws:s3:::" + bucketName + "/*\"]" // Bu kovanın içindeki tüm dosyalara (/*)
                + "  }"
                + "]"
                + "}";

        // Amazon SDK'ya kuralı işleme emri veriyoruz
        PutBucketPolicyRequest policyReq = PutBucketPolicyRequest.builder()
                .bucket(bucketName)
                .policy(publicPolicy)
                .build();

        s3Client.putBucketPolicy(policyReq);
        System.out.println("Bucket yetkisi 'Public Read-Only' olarak ayarlandı: " + bucketName);
    }
}