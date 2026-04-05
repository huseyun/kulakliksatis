package com.kulakyokedici.kulakliksitesi.objects.data.dto.request;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotBlank;

public record ItemUpdateRequest(
		@NotBlank(message = "eşya ismi girilmelidir.")
		String name,
		@NotBlank(message = "eşya başlığı girilmelidir.")
		String title,
		@NotBlank(message = "eşya markası girilmelidir.")
		String brand,
		@Nullable
		String description)
{

}
