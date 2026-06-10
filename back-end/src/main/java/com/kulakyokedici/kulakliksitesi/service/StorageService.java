package com.kulakyokedici.kulakliksitesi.service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.kulakyokedici.kulakliksitesi.objects.exception.StorageException;

import net.coobird.thumbnailator.Thumbnails;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

@Service
public class StorageService 
{
	private final S3Client s3Client;
	
	@Autowired
	public StorageService(
			S3Client s3Client)
	{
		this.s3Client = s3Client;
	}
	
	public void uploadFile(
			InputStream input, 
			long contentLength, 
			String contentType, 
			String bucketName, 
			String objectKey)
	{
			PutObjectRequest req = PutObjectRequest.builder()
					.bucket(bucketName)
					.key(objectKey)
					.contentType(contentType)
					.build();
			
				s3Client.putObject(
						req, 
						RequestBody.fromInputStream(input, contentLength));
	}
	
	public void uploadFile(
			MultipartFile file, 
			long contentLength, 
			String contentType, 
			String bucketName, 
			String objectKey)
	{
			try
			{
				InputStream inputStream = file.getInputStream();
				
				uploadFile(
						inputStream,
						contentLength,
						contentType,
						bucketName,
						objectKey);
			} catch (IOException e)
			{
				// yanlış exception olabilir.
				throw new StorageException("dosya sisteminde bir hata oluştu:" + e.getMessage());
			}
	}
	
	public void reshapeAndUploadImage(
			MultipartFile file,
			int width,
			int height,
			String bucketName,
			String key)
	{
		byte[] reshapedBytes = reshapeImage(file, width, height);
		
		InputStream inputStream = new ByteArrayInputStream(reshapedBytes);
		long reshapedLength = reshapedBytes.length;
		
		uploadFile(
				inputStream,
				reshapedLength,
				"image/jpeg",
				bucketName,
				key);
	}
	
	private byte[] reshapeImage(MultipartFile file, int width, int height)
	{
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		
		try 
		{
			Thumbnails.of(file.getInputStream())
					.size(width, height)
				    .outputFormat("jpg")
				   	.toOutputStream(outputStream);
		} catch (IOException e) 
		{
			throw new StorageException("dosya sisteminde bir hata oluştu:" + e.getMessage());
		}
		
		return outputStream.toByteArray();
	}
	
//	private String getExtension(String fileName) {
//        if (fileName != null && fileName.contains(".")) {
//            return fileName.substring(fileName.lastIndexOf("."));
//        }
//        return ".jpg";
//    }
}
