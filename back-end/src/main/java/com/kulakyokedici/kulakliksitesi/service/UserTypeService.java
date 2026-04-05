package com.kulakyokedici.kulakliksitesi.service;

import org.springframework.stereotype.Service;

import com.kulakyokedici.kulakliksitesi.repository.SellerRepository;

@Service
public class UserTypeService {
	private SellerRepository sellerRepository;

	public UserTypeService(SellerRepository sellerRepository)
	{
		this.sellerRepository = sellerRepository;
	}
	
}
