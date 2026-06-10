package com.kulakyokedici.kulakliksitesi.objects.exception.dto;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.kulakyokedici.kulakliksitesi.objects.exception.EErrorCode;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record ErrorResponse(
		int statusCode,
		EErrorCode errorCode,
		String message,
		LocalDateTime timestamp,
		List<ValidationErrorResponse> validationErrors)
{
	
	// yazmaya gerek yok aslında 
	public ErrorResponse(
		int statusCode,
		EErrorCode errorCode,
		String message,
		LocalDateTime timestamp,
		List<ValidationErrorResponse> validationErrors)
	{
		this.statusCode = statusCode;
		this.errorCode = errorCode;
		this.message = message;
		this.timestamp = timestamp;
		this.validationErrors = validationErrors;
	}
	
	// basit hatalar için constructor, validationerrors nesnesini içermeyebilir
	public ErrorResponse(
			int statusCode,
			EErrorCode errorCode,
			String message,
			LocalDateTime timestamp)
		{
			this(statusCode, errorCode, message, timestamp, null);
		}
}
