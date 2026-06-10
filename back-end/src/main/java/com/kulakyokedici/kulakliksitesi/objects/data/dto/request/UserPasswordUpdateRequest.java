package com.kulakyokedici.kulakliksitesi.objects.data.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UserPasswordUpdateRequest(
		@NotBlank(message = "sifre bos olamaz.")
		@Size(min=8)
		String password)
{}
