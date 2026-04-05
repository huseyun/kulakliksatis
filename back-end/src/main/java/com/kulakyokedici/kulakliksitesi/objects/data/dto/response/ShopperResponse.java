package com.kulakyokedici.kulakliksitesi.objects.data.dto.response;

public record ShopperResponse(
		Long id,
		String username,
		String email,
		String firstName,
		String lastName) 
{
	public ShopperResponse(
		Long id,
		String username,
		String email,
		String firstName,
		String lastName)
	{
		this.id = id;
		this.username = username;
		this.email = email;
		this.firstName = firstName;
		this.lastName = lastName;
	}
	
}
