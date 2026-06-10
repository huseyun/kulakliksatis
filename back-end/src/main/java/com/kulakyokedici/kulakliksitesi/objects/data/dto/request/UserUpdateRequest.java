package com.kulakyokedici.kulakliksitesi.objects.data.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UserUpdateRequest(
		@NotBlank(message = "kullanici adi bos olamaz.")
		@Size(
				min = 4,
				max = 16,
				message = "username cannot be shorter than 4, and longer than 16 characters")
		String username,
		@NotBlank(message = "e-posta girilmelidir.")
		String email)
{}
