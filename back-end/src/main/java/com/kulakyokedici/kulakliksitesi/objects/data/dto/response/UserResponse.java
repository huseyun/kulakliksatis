package com.kulakyokedici.kulakliksitesi.objects.data.dto.response;

import java.util.Set;

public record UserResponse(
		Long id,
		String username,
		String email,
		Set<UserTypeResponse> userType) 
{
	public UserResponse(
			Long id,
			String username,
			String email,
			Set<UserTypeResponse> userType)
	{
		this.id = id;
		this.username = username;
		this.email = email;
		this.userType = userType;
	}
}
