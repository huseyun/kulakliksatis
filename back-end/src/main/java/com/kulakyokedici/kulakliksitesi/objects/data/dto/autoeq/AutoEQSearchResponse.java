package com.kulakyokedici.kulakliksitesi.objects.data.dto.autoeq;

import java.util.List;

public record AutoEQSearchResponse(
		List<AutoEQSearchEntry> results,
		int total)
{

}
