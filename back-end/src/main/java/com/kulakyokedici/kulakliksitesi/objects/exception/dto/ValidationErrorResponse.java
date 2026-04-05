package com.kulakyokedici.kulakliksitesi.objects.exception.dto;

public record ValidationErrorResponse(
		String field,
		String message)
{}
