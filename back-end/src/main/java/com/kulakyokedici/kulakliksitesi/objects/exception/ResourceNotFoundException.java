package com.kulakyokedici.kulakliksitesi.objects.exception;

import org.springframework.http.HttpStatus;

public class ResourceNotFoundException extends BaseException {
	
	public ResourceNotFoundException(
			String resourceName, 
			String fieldName,
			Object fieldValue,
			EErrorCode errorCode) {
		super(String.format("%s, %s:%s değeri ile bulunamadı.", 
					resourceName,
					fieldName,
					fieldValue), 
				HttpStatus.NOT_FOUND,
				errorCode);
	}

}
