package com.kulakyokedici.kulakliksitesi.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.kulakyokedici.kulakliksitesi.mapper.AdminMapper;
import com.kulakyokedici.kulakliksitesi.objects.data.Admin;
import com.kulakyokedici.kulakliksitesi.objects.data.EUserType;
import com.kulakyokedici.kulakliksitesi.objects.data.dto.request.UserCreateRequest;
import com.kulakyokedici.kulakliksitesi.objects.data.dto.request.UserUpdateRequest;
import com.kulakyokedici.kulakliksitesi.objects.data.dto.response.AdminResponse;
import com.kulakyokedici.kulakliksitesi.objects.exception.ResourceNotFoundException;
import com.kulakyokedici.kulakliksitesi.repository.AdminRepository;
import com.kulakyokedici.kulakliksitesi.repository.UserTypeRepository;

import jakarta.transaction.Transactional;

@Service
public class AdminService
{
	private final AdminRepository adminRepository;
    private final UserTypeRepository userTypesRepository;
	private final AdminMapper adminMapper;
	
	@Autowired
	public AdminService(AdminRepository adminRepository,
			UserTypeRepository userTypesRepository,
			AdminMapper adminMapper)
	{
		this.adminRepository = adminRepository;
		this.userTypesRepository = userTypesRepository;
		this.adminMapper = adminMapper;
	}
	
	public List<AdminResponse> getAll()
	{
		List<Admin> admins = adminRepository.findAll();
		
		return admins.stream()
				.map(admin -> adminMapper.toResponse(admin))
				.collect(Collectors.toList());
	}
	
	public AdminResponse getById(Long id)
	{
		Admin admin = adminRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("admin", "id", id));
		
		return adminMapper.toResponse(admin);
	}
	
	public AdminResponse getByUsername(String username)
	{
		Admin admin = adminRepository.findByUsername(username)
				.orElseThrow(() -> new ResourceNotFoundException("admin", "username", username));
		
		return adminMapper.toResponse(admin);
	}
	
	public AdminResponse getByEmail(String email)
	{
		Admin admin = adminRepository.findByEmail(email)
				.orElseThrow(() -> new ResourceNotFoundException("admin", "email", email));
		
		return adminMapper.toResponse(admin);
	}
	
	public void add(UserCreateRequest newAdmin)
	{
		Admin admin = adminMapper.toEntity(newAdmin);
		
		admin.getUserTypes().add(userTypesRepository.findByName(EUserType.ADMIN)
				.orElseThrow(() -> new ResourceNotFoundException("user type", "user type name", EUserType.ADMIN)));
		
        adminRepository.save(admin);
	}
	
	@Transactional
	public void update(Long adminId, UserUpdateRequest req)
	{
		Admin existing = adminRepository.findById(adminId)
				.orElseThrow(() -> new ResourceNotFoundException("admin", "id", adminId));
		
		adminMapper.updateEntity(existing, req);
	}
}
