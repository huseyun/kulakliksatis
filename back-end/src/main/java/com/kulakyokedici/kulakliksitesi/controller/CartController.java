package com.kulakyokedici.kulakliksitesi.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kulakyokedici.kulakliksitesi.service.CartService;

@RestController
@RequestMapping("api/carts")
public class CartController
{
	private final CartService cartService;
	
	public CartController(
			CartService cartService)
	{
		this.cartService = cartService;
	}
	
	/*
	 * GET istekleri
	 */
}
