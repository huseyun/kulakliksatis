package com.kulakyokedici.kulakliksitesi.config.exception;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.kulakyokedici.kulakliksitesi.objects.exception.BaseException;
import com.kulakyokedici.kulakliksitesi.objects.exception.ResourceNotFoundException;
import com.kulakyokedici.kulakliksitesi.objects.exception.dto.ErrorResponse;
import com.kulakyokedici.kulakliksitesi.objects.exception.dto.ValidationErrorResponse;

@RestControllerAdvice
public class GlobalExceptionHandler {
	
	/*
	 * framework hataları bu kısımda.
	 */
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationException(MethodArgumentNotValidException ex) {
        
        List<ValidationErrorResponse> validationErrors = ex.getBindingResult().getFieldErrors()
            .stream()
            .map(error -> new ValidationErrorResponse(error.getField(), error.getDefaultMessage()))
            .toList();

        ErrorResponse errorResponse = new ErrorResponse(
            HttpStatus.BAD_REQUEST.value(),
            "validation hatası",
            LocalDateTime.now(),
            validationErrors
        );

        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }
	
	/*
	 * iş hataları bu kısımda.
	 */
	
	@ExceptionHandler(BaseException.class)
	public ResponseEntity<ErrorResponse> handleAllBusinessExceptions(BaseException ex)
	{
		ErrorResponse errorResponse = new ErrorResponse(
				ex.getHttpStatus().value(),
				ex.getMessage(),
				LocalDateTime.now());
		
		return new ResponseEntity<>(errorResponse, ex.getHttpStatus());
	}
	
	/*
	 * beklenmedik tüm hatalar bu kısımda.
	 */

	@ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleAllGeneric(Exception ex) {
        return new ResponseEntity<>(
            new ErrorResponse(500, ex.getMessage(), LocalDateTime.now()), // şimdilik error yollansın. bu bir güvenlik açığıdır
            HttpStatus.INTERNAL_SERVER_ERROR
        );
    }
}
