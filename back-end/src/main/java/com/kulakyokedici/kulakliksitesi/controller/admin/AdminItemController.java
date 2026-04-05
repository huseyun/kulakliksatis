package com.kulakyokedici.kulakliksitesi.controller.admin;

import java.util.Set;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kulakyokedici.kulakliksitesi.objects.data.dto.response.ItemSummaryResponse;
import com.kulakyokedici.kulakliksitesi.service.ItemService;

@RestController
@RequestMapping("api/admin")
public class AdminItemController
{
	private final ItemService itemService;
	
	public AdminItemController(
			ItemService itemService)
	{
		this.itemService = itemService;
	}
}
