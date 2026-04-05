package com.kulakyokedici.kulakliksitesi.controller.admin;

import java.util.Set;

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

import com.kulakyokedici.kulakliksitesi.objects.data.dto.request.SellerCreateRequest;
import com.kulakyokedici.kulakliksitesi.objects.data.dto.request.SellerUpdateRequest;
import com.kulakyokedici.kulakliksitesi.objects.data.dto.response.ItemSummaryResponse;
import com.kulakyokedici.kulakliksitesi.objects.data.dto.response.SellerDetailedResponse;
import com.kulakyokedici.kulakliksitesi.objects.data.dto.response.SellerResponse;
import com.kulakyokedici.kulakliksitesi.service.ItemService;
import com.kulakyokedici.kulakliksitesi.service.SellerService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("api/admin")
public class AdminSellerController
{
	private final SellerService sellerService;
	private final ItemService itemService;
	
	@Autowired
	public AdminSellerController(
			SellerService sellerService,
			ItemService itemService)
	{
		this.sellerService = sellerService;
		this.itemService = itemService;
	}
	
	/*
	 * GET istekleri
	 */

	@GetMapping("/sellers")
	public ResponseEntity<?> getSeller(
			@RequestParam(name = "username", required = false) String username,
			@RequestParam(name = "email", required = false) String email,
			@RequestParam(name = "company_name", required = false) String companyName)
	{
		SellerResponse sellerResponse = null;
		
		if(username != null && !username.isBlank())
			sellerResponse = sellerService.getByUsername(username);
		else if(email != null && !email.isBlank())
			sellerResponse = sellerService.getByEmail(email);
		else if(companyName != null && !companyName.isBlank())
			sellerResponse = sellerService.getByCompanyName(companyName);
		else
			return ResponseEntity.ok(sellerService.getAll());
		
		return ResponseEntity.ok(sellerResponse);
	}
	
	@GetMapping("/sellers/{id}")
	public ResponseEntity<SellerDetailedResponse> getSellerById(@PathVariable Long id)
	{
		return ResponseEntity.ok(sellerService.getDetailedResponseById(id));
	}
	
	@GetMapping("/sellers/{sellerId}/items")
	public ResponseEntity<Set<ItemSummaryResponse>> getItemsBySellerId(@PathVariable Long sellerId)
	{
		return ResponseEntity.ok(itemService.getSummaryAllBySellerId(sellerId));
	}
	
	/*
	 * POST istekleri
	 * yeni kaynak eklemek için.
	 */
	
	@PostMapping("/sellers")
	public ResponseEntity<Void> addSeller(@Valid @RequestBody SellerCreateRequest req)
	{	
		sellerService.add(req);
		return ResponseEntity.ok().build();
	}
	
	/*
	 * PUT istekleri
	 * kaynak güncellemek için.
	 */
	
	@PutMapping("/sellers/{sellerId}")
	public ResponseEntity<Void> updateSeller(@PathVariable Long sellerId,
			@Valid @RequestBody SellerUpdateRequest newSeller)
	{
		sellerService.update(sellerId, newSeller);
		return ResponseEntity.noContent().build();
	}
	
}
