package com.kulakyokedici.kulakliksitesi.objects.data.dto.request;

import jakarta.validation.constraints.NotBlank;

public record SellerDetailsUpdateRequest(
		@NotBlank(message = "şirket ismi boş bırakılamaz.")
		String companyName)
{

}
