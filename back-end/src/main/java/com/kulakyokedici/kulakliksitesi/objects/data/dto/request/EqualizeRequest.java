package com.kulakyokedici.kulakliksitesi.objects.data.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record EqualizeRequest(
		@NotBlank(message = "Kullanıcı kulaklığı ID'si boş olamaz.")
		String userHeadphoneId,
		
		@NotNull(message = "Ürün ID boş olamaz.")
		@Positive(message = "Ürün ID pozitif olmalı.")
		Long productId)
{

}
