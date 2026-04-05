package com.kulakyokedici.kulakliksitesi.objects.data.dto.request;

import jakarta.validation.constraints.NotBlank;

public record UserPasswordUpdateRequest(
		@NotBlank(message = "sifre bos olamaz.")
		String password)
{}
