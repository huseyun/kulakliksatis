package com.kulakyokedici.kulakliksitesi.objects.data.dto.autoeq;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public record AutoEQEqualizeResponse(
		String source_id,
		String target_id,
		int fs,
		@JsonProperty("preamp_db") double preampDb,
		List<AutoEQBiquadFilter> filters)
{

}
