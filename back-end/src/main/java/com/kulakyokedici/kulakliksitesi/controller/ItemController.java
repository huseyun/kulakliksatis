package com.kulakyokedici.kulakliksitesi.controller;

import java.net.URI;
import java.security.Principal;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kulakyokedici.kulakliksitesi.objects.data.dto.request.ItemCreateRequest;
import com.kulakyokedici.kulakliksitesi.objects.data.dto.request.ItemImageCreateRequest;
import com.kulakyokedici.kulakliksitesi.objects.data.dto.request.ItemUpdateRequest;
import com.kulakyokedici.kulakliksitesi.objects.data.dto.response.ItemResponse;
import com.kulakyokedici.kulakliksitesi.objects.data.dto.response.ItemSummaryResponse;
import com.kulakyokedici.kulakliksitesi.objects.data.dto.response.SellerResponse;
import com.kulakyokedici.kulakliksitesi.service.ItemService;
import com.kulakyokedici.kulakliksitesi.service.SellerService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("api/items")
public class ItemController
{
	private final ItemService itemService;
	private final SellerService sellerService;
	
	public ItemController(
			ItemService itemService,
			SellerService sellerService)
	{
		this.itemService = itemService;
		this.sellerService = sellerService;
	}
	
	/*
	 * GET istekleri
	 */
	
	@GetMapping
	public ResponseEntity<List<ItemSummaryResponse>> getSummaryItemList()
	{
		return ResponseEntity.ok(itemService.getSummaryAll());
	}
	
	@GetMapping("/search")
    public ResponseEntity<List<ItemSummaryResponse>> searchItems(
    		@RequestParam String keyword) {
        List<ItemSummaryResponse> results = itemService.search(keyword);
        return ResponseEntity.ok(results);
    }
	
}
