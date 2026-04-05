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

import com.kulakyokedici.kulakliksitesi.objects.data.dto.request.ShopperCreateRequest;
import com.kulakyokedici.kulakliksitesi.objects.data.dto.request.ShopperDetailsUpdateRequest;
import com.kulakyokedici.kulakliksitesi.objects.data.dto.request.ShopperUpdateRequest;
import com.kulakyokedici.kulakliksitesi.objects.data.dto.response.ShopperResponse;
import com.kulakyokedici.kulakliksitesi.service.ShopperService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("api/admin")
public class AdminShopperController
{
	private final ShopperService shopperService;
	
	@Autowired
	public AdminShopperController(
			ShopperService shopperService)
	{
		this.shopperService = shopperService;
	}
	
	/*
	 * GET istekleri
	 */
	
	// isim soyisim araması eklenecek
	@GetMapping("/shoppers")
	public ResponseEntity<?> getShopper(
			@RequestParam(name = "username", required = false) String username,
			@RequestParam(name = "email", required = false) String email)
	{
		ShopperResponse shopperResponse = null;
		
		if(username != null && !username.isBlank())
			shopperResponse = shopperService.getByUsername(username);
		else if(email != null && !email.isBlank())
			shopperResponse = shopperService.getByEmail(email);
		else
			return ResponseEntity.ok(shopperService.getAll());
		
		return ResponseEntity.ok(shopperResponse);
	}
	
	@GetMapping("/shoppers/{id}")
	public ResponseEntity<ShopperResponse> getShopperById(@PathVariable Long id)
	{
		return ResponseEntity.ok(shopperService.getById(id));
	}
	
	/*
	 * POST istekleri
	 * yeni kaynak eklemek için.
	 */
	
	@PostMapping("/shoppers")
	public ResponseEntity<Void> addShopper(@Valid @RequestBody ShopperCreateRequest req)
	{
		shopperService.add(req);
		return ResponseEntity.ok().build();
	}
	
	/*
	 * PUT istekleri
	 * kaynak güncellemek için.
	 */
	
	@PutMapping("/shoppers/{shopperId}")
	public ResponseEntity<Void> updateShopper(@PathVariable Long shopperId, 
			@Valid @RequestBody ShopperUpdateRequest newShopper)
	{
		shopperService.update(shopperId, newShopper);
		return ResponseEntity.noContent().build();
	}
	
	@PutMapping("/shoppers/{shopperId}/details")
	public ResponseEntity<Void> updateShopperDetails(@PathVariable Long shopperId, 
			@Valid @RequestBody ShopperDetailsUpdateRequest newShopperDetails)
	{
		shopperService.updateDetails(shopperId, newShopperDetails);
		return ResponseEntity.noContent().build();
	}
}
