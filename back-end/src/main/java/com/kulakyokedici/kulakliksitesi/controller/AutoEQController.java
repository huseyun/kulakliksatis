package com.kulakyokedici.kulakliksitesi.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kulakyokedici.kulakliksitesi.objects.data.dto.autoeq.AutoEQSearchResponse;
import com.kulakyokedici.kulakliksitesi.objects.data.dto.request.EqualizeRequest;
import com.kulakyokedici.kulakliksitesi.objects.data.dto.response.EqualizeResponse;
import com.kulakyokedici.kulakliksitesi.service.autoeq.AutoEQService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/autoeq")
public class AutoEQController {
	
	private final AutoEQService autoEQService;
	
	public AutoEQController(AutoEQService autoEQService)
	{
		this.autoEQService = autoEQService;
	}
	
	/**
	 * Kullanıcının kulaklığını mağazadaki ürüne benzetecek EQ profili.
	 * Public endpoint — auth gerekmez.
	 */
	@PostMapping("/equalize")
	public ResponseEntity<EqualizeResponse> equalize(@Valid @RequestBody EqualizeRequest request)
	{
		EqualizeResponse response = this.autoEQService.equalize(request);
		return ResponseEntity.ok(response);
	}
	
	/**
	 * Kulaklık arama — frontend autocomplete'i bunu çağırır.
	 * Public endpoint — auth gerekmez.
	 */
	@GetMapping("/headphones")
	public ResponseEntity<AutoEQSearchResponse> searchHeadphones(
			@RequestParam(value = "q", required = false) String query,
			@RequestParam(value = "limit", defaultValue = "20") int limit)
	{
		AutoEQSearchResponse response = this.autoEQService.searchHeadphones(query, limit);
		return ResponseEntity.ok(response);
	}

}