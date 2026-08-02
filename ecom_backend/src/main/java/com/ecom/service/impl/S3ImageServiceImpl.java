package com.ecom.service.impl;

import java.io.IOException;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.ecom.controller.OrderController;
import com.ecom.custom_exception.BadApiRequestException;
import com.ecom.service.S3ImageService;

import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.core.sync.RequestBody;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class S3ImageServiceImpl implements S3ImageService
{

    private final OrderController orderController;

    @Value("${aws.s3.bucket-name}")
    private String bucketName;

    @Value("${aws.s3.region}")
    private String region;

    @Value("${aws.s3.product-image-folder}")
    private String productImageFolder;
    
    @Value("${aws.access-key-id}")
    private String accessKeyId;
    
    @Value("${aws.secret-access-key}")
    private String secretAccessKey;

    private static final java.util.Set<String> ALLOWED_CONTENT_TYPES =
            java.util.Set.of("image/jpeg", "image/png", "image/webp");

    S3ImageServiceImpl(OrderController orderController) {
        this.orderController = orderController;
    }

    @Override
    public String uploadProductImage(MultipartFile file) {
        if (file == null || file.isEmpty()) {
            throw new BadApiRequestException("No image file was provided");
        }
        if (!ALLOWED_CONTENT_TYPES.contains(file.getContentType())) {
            throw new BadApiRequestException("Only JPEG, PNG, or WEBP images are allowed");
        }

        String extension = getExtension(file.getOriginalFilename());
        String key = productImageFolder + UUID.randomUUID() + extension;

        // A fresh client per call keeps this stateless and simple; Spring/AWS SDK
        // internally pool the underlying HTTP connections.
//        try (S3Client s3Client = S3Client.builder().region(Region.of(region)).build()) 
        
        AwsBasicCredentials awsCredentials = AwsBasicCredentials.create(accessKeyId, secretAccessKey);
        try(S3Client s3Client = S3Client.builder()
        		.region(Region.of(region))
        		.credentialsProvider(StaticCredentialsProvider.create(awsCredentials)).
        		build()){
        	
//        	System.out.println("Bucket ="+bucketName);
//        	System.out.println("Region ="+region);
//        	System.out.println("Access Key ="+accessKeyId);
//        	System.out.println("Secret Key ="+secretAccessKey);
        	
            PutObjectRequest request = PutObjectRequest.builder()
                    .bucket(bucketName)
                    .key(key)
                    .contentType(file.getContentType())
                    .build();

            s3Client.putObject(request, RequestBody.fromInputStream(file.getInputStream(), file.getSize()));
        } catch (IOException e) {
            log.error("Failed to read uploaded image", e);
            throw new BadApiRequestException("Could not read the uploaded image");
        }

        return String.format("https://%s.s3.%s.amazonaws.com/%s", bucketName, region, key);
    }

    private String getExtension(String originalFilename) {
        if (originalFilename == null || !originalFilename.contains(".")) {
            return ".jpg";
        }
        return originalFilename.substring(originalFilename.lastIndexOf('.'));
    }

}
