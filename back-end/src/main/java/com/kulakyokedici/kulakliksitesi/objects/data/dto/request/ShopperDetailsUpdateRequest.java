 package com.kulakyokedici.kulakliksitesi.objects.data.dto.request;

import jakarta.validation.constraints.NotBlank;

public record ShopperDetailsUpdateRequest (
		@NotBlank(message = "bir isim girilmelidir.")
		String firstName,
		@NotBlank(message = "bir soyisim girilmelidir.")
		String lastName) 
{}
