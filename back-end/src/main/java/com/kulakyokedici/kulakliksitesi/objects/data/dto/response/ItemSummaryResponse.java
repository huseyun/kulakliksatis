package com.kulakyokedici.kulakliksitesi.objects.data.dto.response;

public record ItemSummaryResponse(
		Long id,
		String title,
		Double price,
		boolean isRecommended,
		String thumbnailImageUrl)
{}
