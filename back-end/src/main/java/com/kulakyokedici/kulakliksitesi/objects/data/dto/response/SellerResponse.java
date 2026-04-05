package com.kulakyokedici.kulakliksitesi.objects.data.dto.response;

public record SellerResponse(
		Long id,
		String username,
		String email,
		String companyName)
{
	public SellerResponse(
			Long id,
			String username,
			String email,
			String companyName)
	{
		this.id = id;
		this.username = username;
		this.email = email;
		this.companyName = companyName;
	}
}
