package com.kulakyokedici.kulakliksitesi.objects.data.dto.request;

import jakarta.validation.constraints.NotBlank;

public record ShopperUpdateRequest(
		@NotBlank(message = "kullanici adi bos olamaz.")
		String username,
		@NotBlank(message = "e-posta girilmelidir.")
		String email,
		@NotBlank(message = "bir isim girilmelidir.")
		String firstName,
		@NotBlank(message = "bir soyisim girilmelidir.")
		String lastName) 
{}