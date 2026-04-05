package com.kulakyokedici.kulakliksitesi.mapper;

import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.kulakyokedici.kulakliksitesi.objects.data.Item;
import com.kulakyokedici.kulakliksitesi.objects.data.Seller;
import com.kulakyokedici.kulakliksitesi.objects.data.dto.request.SellerCreateRequest;
import com.kulakyokedici.kulakliksitesi.objects.data.dto.request.SellerDetailsUpdateRequest;
import com.kulakyokedici.kulakliksitesi.objects.data.dto.request.SellerUpdateRequest;
import com.kulakyokedici.kulakliksitesi.objects.data.dto.response.ItemSummaryResponse;
import com.kulakyokedici.kulakliksitesi.objects.data.dto.response.SellerDetailedResponse;
import com.kulakyokedici.kulakliksitesi.objects.data.dto.response.SellerResponse;

@Component
public class SellerMapper
{
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	public SellerMapper(PasswordEncoder passwordEncoder)
	{
		this.passwordEncoder = passwordEncoder;
	}
	
	public Seller toEntity(SellerUpdateRequest newSeller)
	{
		Seller seller = new Seller();
		seller.setUsername(newSeller.username());
		seller.setCompanyName(newSeller.companyName());
		seller.setEmail(newSeller.email());
		
		return seller;
	}
	
	public Seller toEntity(SellerCreateRequest newSeller)
	{
		Seller seller = new Seller();
		seller.setUsername(newSeller.username());
		seller.setPassword(passwordEncoder.encode(newSeller.password()));
		seller.setCompanyName(newSeller.companyName());
		seller.setEmail(newSeller.email());
		
		return seller;
	}
	
	public SellerDetailedResponse toDetailedResponse(Seller seller)
	{
		return new SellerDetailedResponse(
				seller.getId(),
				seller.getUsername(),
				seller.getEmail(),
				seller.getCompanyName(),
				seller.getItems().stream()
					.map(item -> toSummaryResponse(item))
					.collect(Collectors.toSet()));
	}
	
	public SellerResponse toResponse(Seller seller)
	{
		return new SellerResponse(
				seller.getId(),
				seller.getUsername(),
				seller.getEmail(),
				seller.getCompanyName());
	}
	
	public void updateEntity(Seller seller, SellerUpdateRequest req)
	{
		seller.setEmail(req.email());
		seller.setUsername(req.username());
		seller.setCompanyName(req.companyName());
	}
	
	public void updateEntity(Seller seller, SellerDetailsUpdateRequest req)
	{
		seller.setCompanyName(req.companyName());
	}
	
	// itemler için yardımcı metot.
	private ItemSummaryResponse toSummaryResponse(Item item)
	{
		return new ItemSummaryResponse(
				item.getId(),
				item.getTitle(),
				item.getPrice(),
				item.getImages().stream()
					.map(image -> image.getUrl())
					.collect(Collectors.toList()));
	}
}
