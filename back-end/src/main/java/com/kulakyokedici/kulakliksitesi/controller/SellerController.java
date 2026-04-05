package com.kulakyokedici.kulakliksitesi.controller;

import java.security.Principal;
import java.util.Set;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kulakyokedici.kulakliksitesi.objects.data.dto.request.SellerDetailsUpdateRequest;
import com.kulakyokedici.kulakliksitesi.objects.data.dto.response.ItemSummaryResponse;
import com.kulakyokedici.kulakliksitesi.objects.data.dto.response.SellerResponse;
import com.kulakyokedici.kulakliksitesi.service.ItemService;
import com.kulakyokedici.kulakliksitesi.service.SellerService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/sellers")
@PreAuthorize("hasRole('SELLER')")
public class SellerController
{
	private final SellerService sellerService;
	private final ItemService itemService;
	
	public SellerController(
			SellerService sellerService,
			ItemService itemService)
	{
		this.sellerService = sellerService;
		this.itemService = itemService;
	}
	
	/*
	 * GET istekleri
	 */
	
	@GetMapping("/me/items")
	public ResponseEntity<Set<ItemSummaryResponse>> getItems(
			Principal principal)
	{
		SellerResponse resp = sellerService.getByUsername(principal.getName());
		
		return ResponseEntity.ok(itemService.getSummaryAllBySellerId(resp.id()));
	}
	
}
