package com.kulakyokedici.kulakliksitesi.objects.data.dto.request;

import jakarta.validation.constraints.NotBlank;

public record UserUpdateRequest(
		@NotBlank(message = "kullanici adi bos olamaz.")
		String username,
		@NotBlank(message = "sifre bos olamaz.")
		String password,
		@NotBlank(message = "e-posta girilmelidir.")
		String email)
{}
