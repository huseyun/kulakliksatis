package com.kulakyokedici.kulakliksitesi.controller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kulakyokedici.kulakliksitesi.objects.data.dto.request.UserCreateRequest;
import com.kulakyokedici.kulakliksitesi.objects.data.dto.request.UserUpdateRequest;
import com.kulakyokedici.kulakliksitesi.objects.data.dto.response.AdminResponse;
import com.kulakyokedici.kulakliksitesi.service.AdminService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("api/admin")
public class AdminAdminController
{
	private final AdminService adminService;
	
	@Autowired
	public AdminAdminController(
			AdminService adminService)
	{
		this.adminService = adminService;
	}
	
	/*
	 * GET istekleri
	 */
	
	@GetMapping("/admins")
	public ResponseEntity<?> getAdmin(
			@RequestParam(name = "username", required = false) String username,
			@RequestParam(name = "email", required = false) String email)
	{
		AdminResponse adminResponse = null;
		
		if(username != null && !username.isBlank())
			adminResponse = adminService.getByUsername(username);
		else if(email != null && !email.isBlank())
			adminResponse = adminService.getByEmail(email);
		else
			return ResponseEntity.ok(adminService.getAll());
		
		return ResponseEntity.ok(adminResponse);
	}
	
	@GetMapping("/admins/{id}")
	public ResponseEntity<AdminResponse> getAdminById(@PathVariable Long id)
	{
		return ResponseEntity.ok(adminService.getById(id));
	}
	
	/*
	 * POST istekleri
	 * yeni kaynak eklemek için.
	 */
	
	@PostMapping("/admins")
	public ResponseEntity<Void> addAdmin(@Valid @RequestBody UserCreateRequest newAdmin)
	{
		adminService.add(newAdmin);
		return ResponseEntity.ok().build();
	}
	
	/*
	 * PUT istekleri
	 * varolan kaynağı güncelleme.
	 */
	
	@PutMapping("/admins/{adminId}")
	public ResponseEntity<Void> updateAdmin(@PathVariable Long adminId,
			@Valid @RequestBody UserUpdateRequest newAdmin)
	{
		adminService.update(adminId, newAdmin);
		return ResponseEntity.noContent().build();
	}
}
