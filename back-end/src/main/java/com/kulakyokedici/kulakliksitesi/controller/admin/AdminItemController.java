package com.kulakyokedici.kulakliksitesi.controller.admin;

import java.security.Principal;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.kulakyokedici.kulakliksitesi.objects.data.dto.request.ItemUpdateRequest;
import com.kulakyokedici.kulakliksitesi.objects.data.dto.response.ItemSummaryResponse;
import com.kulakyokedici.kulakliksitesi.objects.data.dto.response.SellerResponse;
import com.kulakyokedici.kulakliksitesi.service.ItemService;

@RestController
@RequestMapping("api/admin/items")
public class AdminItemController
{
	private final ItemService itemService;
	
	public AdminItemController(
			ItemService itemService)
	{
		this.itemService = itemService;
	}
	
	/*
	 * GET istekleri
	 */
	
	@GetMapping
	public ResponseEntity<List<ItemSummaryResponse>> getSummaryItemList()
	{
		return ResponseEntity.ok(itemService.getSummaryAll());
	}
	
	/*
	 * POST istekleri
	 */
	@PostMapping("/{id}/images")
	public ResponseEntity<Void> addItemImages(
			@PathVariable Long id,
			@RequestPart List<MultipartFile> files,
			@RequestParam List<Boolean> isThumbnail)
	{
		itemService.addImages(files, id, isThumbnail);
		
		return ResponseEntity.noContent().build();
	}
	
	/*
	 * PUT istekleri
	 */
	
	@PutMapping("/{id}")
	public ResponseEntity<Void> updateItem(
			@PathVariable Long id,
			@RequestBody ItemUpdateRequest req)
	{
		itemService.update(id, req);
		
		return ResponseEntity.noContent().build();
	}
	
	/*
	 * DELETE istekleri
	 */
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteItem(
			@PathVariable Long id)
	{
		itemService.delete(id);
		
		return ResponseEntity.noContent().build();
	}
}
