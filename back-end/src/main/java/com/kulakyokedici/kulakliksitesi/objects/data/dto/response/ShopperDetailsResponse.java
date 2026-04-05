package com.kulakyokedici.kulakliksitesi.objects.data.dto.response;

public record ShopperDetailsResponse(
		String firstName,
		String lastName)
{
	public ShopperDetailsResponse(
			String firstName,
			String lastName)
	{
		this.firstName = firstName;
		this.lastName = lastName;
	}
}
