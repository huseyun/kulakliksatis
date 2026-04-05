package com.kulakyokedici.kulakliksitesi.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.kulakyokedici.kulakliksitesi.objects.data.User;
import com.kulakyokedici.kulakliksitesi.objects.data.dto.request.UserPasswordUpdateRequest;
import com.kulakyokedici.kulakliksitesi.objects.data.dto.request.UserUpdateRequest;
import com.kulakyokedici.kulakliksitesi.objects.data.dto.response.UserResponse;
import com.kulakyokedici.kulakliksitesi.objects.data.dto.response.UserTypeResponse;

@Component
public class UserMapper {
	
	private final PasswordEncoder passwordEncoder;
	
	@Autowired
	public UserMapper(
			PasswordEncoder passwordEncoder)
	{
		this.passwordEncoder = passwordEncoder;
	}
	
	public UserResponse toUserResponse(User user) {
		return new UserResponse(
				user.getId(),
				user.getUsername(),
				user.getEmail(),
				user.getUserTypes().stream()
					.map(userType -> new UserTypeResponse(userType.getName()))
					.collect(java.util.stream.Collectors.toSet()));
	}
	
	public void updateEntity(User user, UserUpdateRequest req)
	{
		user.setEmail(req.email());
		user.setUsername(req.username());
		user.setPassword(passwordEncoder.encode(req.password()));
	}
	
	public void updateEntity(User user, UserPasswordUpdateRequest req)
	{
		user.setPassword(passwordEncoder.encode(req.password()));
	}
}
