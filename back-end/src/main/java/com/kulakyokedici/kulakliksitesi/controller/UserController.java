package com.kulakyokedici.kulakliksitesi.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kulakyokedici.kulakliksitesi.objects.data.dto.request.UserPasswordUpdateRequest;
import com.kulakyokedici.kulakliksitesi.objects.data.dto.response.UserResponse;
import com.kulakyokedici.kulakliksitesi.service.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("api/users")
public class UserController
{
	
	private final UserService userService;

	@Autowired
	public UserController(
			UserService userService)
	{
		this.userService = userService;
	}
	
	/*
	 * PUT istekleri
	 */
	
	@PreAuthorize("isAuthenticated()")
	@PutMapping
	public ResponseEntity<Void> updateUserPassword(
			@Valid @RequestBody UserPasswordUpdateRequest req,
			Principal principal)
	{
		UserResponse currentUser = userService.getByUsername(principal.getName());
		
		userService.updatePassword(currentUser.id(), req);
		
		return ResponseEntity.noContent().build();
	}
	
}
