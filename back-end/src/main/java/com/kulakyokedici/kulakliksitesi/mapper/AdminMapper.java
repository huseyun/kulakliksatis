package com.kulakyokedici.kulakliksitesi.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.kulakyokedici.kulakliksitesi.objects.data.Admin;
import com.kulakyokedici.kulakliksitesi.objects.data.dto.request.UserCreateRequest;
import com.kulakyokedici.kulakliksitesi.objects.data.dto.request.UserUpdateRequest;
import com.kulakyokedici.kulakliksitesi.objects.data.dto.response.AdminResponse;

@Component
public class AdminMapper {
	
	private final PasswordEncoder passwordEncoder;
	
	@Autowired
	public AdminMapper(
			PasswordEncoder passwordEncoder)
	{
		this.passwordEncoder = passwordEncoder;
	}
	
	public AdminResponse toResponse(Admin admin)
	{
		return new AdminResponse(
				admin.getId(),
				admin.getUsername(),
				admin.getEmail());
	}
	
	public Admin toEntity(UserCreateRequest req)
	{
		Admin admin = new Admin();
		
		admin.setEmail(req.email());
		admin.setUsername(req.username());
		admin.setPassword(passwordEncoder.encode(req.password()));
		
		return admin;
	}
	
	public Admin toEntity(UserUpdateRequest req)
	{
		Admin admin = new Admin();
		
		admin.setEmail(req.email());
		admin.setUsername(req.username());
		admin.setPassword(passwordEncoder.encode(req.password()));
		
		return admin;
	}
	
	public void updateEntity(Admin admin, UserUpdateRequest req)
	{
		admin.setEmail(req.email());
		admin.setUsername(req.username());
		admin.setPassword(passwordEncoder.encode(req.password()));
	}
}
