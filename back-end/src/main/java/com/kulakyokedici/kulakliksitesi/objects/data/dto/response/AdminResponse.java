package com.kulakyokedici.kulakliksitesi.objects.data.dto.response;

public record AdminResponse(
		Long id,
		String username,
		String email) 
{
	public AdminResponse(
			Long id,
			String username,
			String email)
	{
		this.id = id;
		this.username = username;
		this.email = email;
	}
}
