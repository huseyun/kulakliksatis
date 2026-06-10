package com.kulakyokedici.kulakliksitesi.objects.data.dto.response;

import java.util.List;

import com.kulakyokedici.kulakliksitesi.objects.data.dto.BiquadFilter;

public record EqualizeResponse(
		int fs,
		double preampDb,
		List<BiquadFilter> filters)
{

}
