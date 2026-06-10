package com.kulakyokedici.kulakliksitesi.config.s3;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;

@Configuration
public class S3Config
{
    private final StorageProperties storageProperties;

    @Autowired
    public S3Config(StorageProperties storageProperties) {
        this.storageProperties = storageProperties;
    }

    @Bean
    public S3Client s3Client() {
        return S3Client.builder()
                .endpointOverride(URI.create(storageProperties.getEndpoint()))
                .credentialsProvider(StaticCredentialsProvider.create(
                        AwsBasicCredentials.create(
                                storageProperties.getAccessKey(),
                                storageProperties.getSecretKey()
                        )
                ))
                .region(Region.EU_CENTRAL_1)
                .forcePathStyle(true)
                .build();
    }
}
