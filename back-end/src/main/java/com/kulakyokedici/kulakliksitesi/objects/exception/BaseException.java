package com.kulakyokedici.kulakliksitesi.objects.exception;

import org.springframework.http.HttpStatus;

public class BaseException extends RuntimeException {
	
	private final HttpStatus httpStatus;
	private final EErrorCode errorCode;
	
	public BaseException(
			String message,
			HttpStatus httpStatus,
			EErrorCode errorCode)
	{
		super(message);
		this.httpStatus = httpStatus;
		this.errorCode = errorCode;
	}
	
	public HttpStatus getHttpStatus()
	{
		return this.httpStatus;
	}
	
	public EErrorCode getErrorCode()
	{
		return this.errorCode;
	}
}
