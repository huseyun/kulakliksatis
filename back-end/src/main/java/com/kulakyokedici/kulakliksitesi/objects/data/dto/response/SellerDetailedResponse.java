package com.kulakyokedici.kulakliksitesi.objects.data.dto.response;

import java.util.Set;

public record SellerDetailedResponse(
		Long id,
		String username,
		String email,
		String companyName,
		Set<ItemSummaryResponse> items)
{
	public SellerDetailedResponse(
			Long id,
			String username,
			String email,
			String companyName,
			Set<ItemSummaryResponse> items)
	{
		this.id = id;
		this.username = username;
		this.email = email;
		this.companyName = companyName;
		this.items = items;
	}
}
