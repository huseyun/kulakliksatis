package com.kulakyokedici.kulakliksitesi.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.kulakyokedici.kulakliksitesi.mapper.UserMapper;
import com.kulakyokedici.kulakliksitesi.objects.data.User;
import com.kulakyokedici.kulakliksitesi.objects.data.dto.request.UserPasswordUpdateRequest;
import com.kulakyokedici.kulakliksitesi.objects.data.dto.request.UserUpdateRequest;
import com.kulakyokedici.kulakliksitesi.objects.data.dto.response.UserResponse;
import com.kulakyokedici.kulakliksitesi.objects.exception.ResourceNotFoundException;
import com.kulakyokedici.kulakliksitesi.repository.UserRepository;

import jakarta.transaction.Transactional;

@Service
public class UserService
{
	private UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;
	private final UserMapper userMapper;
	
	@Autowired
	public UserService(
			UserRepository userRepository,
			PasswordEncoder passwordEncoder,
			UserMapper userMapper)
	{
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
		this.userMapper = userMapper;
	}
	
	public List<UserResponse> getAll()
	{
		List<User> users = userRepository.findAll();
		
		return users.stream()
				.map(user -> userMapper.toUserResponse(user))
				.collect(Collectors.toList());
	}
	
	public UserResponse getByUsername(String username)
	{
		User user = userRepository.findByUsername(username)
				.orElseThrow(() -> new ResourceNotFoundException("user", "username", username));
		
		return userMapper.toUserResponse(user);
	}
	
	// exception testi
	public UserResponse getById(long id)
	{
		User user = userRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("user", "id", id));
		
		return userMapper.toUserResponse(user);
	}
	
	public UserResponse getByEmail(String email)
	{
		User user = userRepository.findByEmail(email)
				.orElseThrow(() -> new ResourceNotFoundException("user", "email", email));
		
		return userMapper.toUserResponse(user);
	}
	
	@Transactional
	public void update(Long id, UserUpdateRequest req)
	{
		User existing = userRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("user", "id", id));
		
		userMapper.updateEntity(existing, req);
	}
	
	@Transactional
	public void updatePassword(Long id, UserPasswordUpdateRequest req)
	{
		User existing = userRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("user", "id", id));
		
		userMapper.updateEntity(existing, req);
	}
	
	public void delete(Long id)
	{
		userRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("user", "id", id));
		
		userRepository.deleteById(id);
	}
}

