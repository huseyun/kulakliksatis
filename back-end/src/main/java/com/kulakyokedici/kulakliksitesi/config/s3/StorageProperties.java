package com.kulakyokedici.kulakliksitesi.config.s3;

import java.util.HashMap;
import java.util.Map;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;

@Component
@ConfigurationProperties(prefix = "storage")
@Getter
@Setter
public class StorageProperties {

    private String endpoint;
    private String publicEndPoint;
    
	private String accessKey;
    private String secretKey;
    
    private String bucketProductImages;
    
	// Tüm bucket isimlerini listelemek için yardımcı metot
    public Map<String, String> getAllBuckets() {
        Map<String, String> buckets = new HashMap<>();
        buckets.put("product-images", bucketProductImages);
        return buckets;
    }
}