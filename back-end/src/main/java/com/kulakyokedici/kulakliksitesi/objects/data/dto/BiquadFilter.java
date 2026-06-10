package com.kulakyokedici.kulakliksitesi.objects.data.dto;

public record BiquadFilter(
		String type,
		double fc,
		double q,
		double gain)
{

}
