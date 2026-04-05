package com.kulakyokedici.kulakliksitesi.controller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kulakyokedici.kulakliksitesi.objects.data.dto.request.UserUpdateRequest;
import com.kulakyokedici.kulakliksitesi.objects.data.dto.response.UserResponse;
import com.kulakyokedici.kulakliksitesi.service.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("api/admin/users")
public class AdminUserController
{
	private UserService userService;
	
	@Autowired
	public AdminUserController(
			UserService userService)
	{
		this.userService = userService;
	}
	
	/*
	 * GET istekleri
	 */
	
	@GetMapping
	public ResponseEntity<?> getUser(
			@RequestParam(name = "username", required = false) String username,
			@RequestParam(name = "email", required = false) String email)
	{
		UserResponse userResponse = null;
		
		if(username != null && !username.isBlank())
			userResponse = userService.getByUsername(username);
		else if(email != null && !email.isBlank())
			userResponse = userService.getByEmail(email);
		else
			return ResponseEntity.ok(userService.getAll());
		
		return ResponseEntity.ok(userResponse);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<UserResponse> getUserById(@PathVariable Long id)
	{
		return ResponseEntity.ok(userService.getById(id));
	}
	
	
	/*
	 * POST istekleri
	 */
	
	/*
	 * PUT istekleri
	 */
	
	@PutMapping("/{id}")
	public ResponseEntity<Void> updateUser(@PathVariable Long id,
			@Valid @RequestBody UserUpdateRequest newUser)
	{
		userService.update(id, newUser);
		return ResponseEntity.noContent().build();
	}
	
	/*
	 * DELETE istekleri
	 */
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteUser(@PathVariable Long id)
	{
		userService.delete(id);
		
		return ResponseEntity.noContent().build();
	}
}