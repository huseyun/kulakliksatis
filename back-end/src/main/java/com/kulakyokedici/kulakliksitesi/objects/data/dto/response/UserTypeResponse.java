package com.kulakyokedici.kulakliksitesi.objects.data.dto.response;

import com.kulakyokedici.kulakliksitesi.objects.data.EUserType;

public record UserTypeResponse(
		EUserType userType) 
{
	public UserTypeResponse(
			EUserType userType)
	{
		this.userType = userType;
	}
}
