package com.ecom.service;

import org.springframework.web.multipart.MultipartFile;

public interface S3ImageService 
{

    // Uploads the file to S3 under the configured product-image folder and
    // returns the public, clean S3 URL to store on the Product entity.
    String uploadProductImage(MultipartFile file);

}
