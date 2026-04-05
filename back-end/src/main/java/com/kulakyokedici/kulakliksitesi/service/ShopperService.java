package com.kulakyokedici.kulakliksitesi.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kulakyokedici.kulakliksitesi.mapper.ShopperMapper;
import com.kulakyokedici.kulakliksitesi.objects.data.EUserType;
import com.kulakyokedici.kulakliksitesi.objects.data.Shopper;
import com.kulakyokedici.kulakliksitesi.objects.data.dto.request.ShopperCreateRequest;
import com.kulakyokedici.kulakliksitesi.objects.data.dto.request.ShopperDetailsUpdateRequest;
import com.kulakyokedici.kulakliksitesi.objects.data.dto.request.ShopperUpdateRequest;
import com.kulakyokedici.kulakliksitesi.objects.data.dto.response.ShopperResponse;
import com.kulakyokedici.kulakliksitesi.objects.exception.ResourceNotFoundException;
import com.kulakyokedici.kulakliksitesi.repository.ShopperRepository;
import com.kulakyokedici.kulakliksitesi.repository.UserTypeRepository;

import jakarta.transaction.Transactional;

@Service
public class ShopperService
{
	private final ShopperRepository shopperRepository;
	private final ShopperMapper shopperMapper;
	private final UserTypeRepository userTypeRepository;
	
	@Autowired
	public ShopperService(
			ShopperRepository shopperRepository,
			ShopperMapper shopperMapper,
			UserTypeRepository userTypeRepository)
	{
		this.shopperRepository = shopperRepository;
		this.shopperMapper = shopperMapper; 
		this.userTypeRepository = userTypeRepository;
	}
	
	public ShopperResponse getById(Long id)
	{
		Shopper shopper = shopperRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("shopper", "id", id));
		
		return shopperMapper.toResponse(shopper);
	}
	
	public ShopperResponse getByUsername(String username)
	{
		Shopper shopper = shopperRepository.findByUsername(username)
				.orElseThrow(() -> new ResourceNotFoundException("shopper", "username", username));
		
		return shopperMapper.toResponse(shopper);
	}
	
	public ShopperResponse getByEmail(String email)
	{
		Shopper shopper = shopperRepository.findByEmail(email)
				.orElseThrow(() -> new ResourceNotFoundException("shopper", "email", email));
		
		return shopperMapper.toResponse(shopper);
	}
	
	public List<ShopperResponse> getAll()
	{
		List<Shopper> shoppers = shopperRepository.findAll();
		
		return shoppers.stream()
				.map(seller -> shopperMapper.toResponse(seller))
				.collect(Collectors.toList());
	}
	
	public ShopperResponse add(ShopperCreateRequest req)
	{
		Shopper shopper = shopperMapper.toEntity(req);
		
		shopper.getUserTypes().add(
				userTypeRepository.findByName(EUserType.SHOPPER)
				.orElseThrow(() -> new ResourceNotFoundException("user type", "user type name", EUserType.SHOPPER)));
		
		shopperRepository.save(shopper);
		
		return shopperMapper.toResponse(shopper);
	}
	
	@Transactional
	public void update(Long id,
			ShopperUpdateRequest req)
	{
	    Shopper existing = shopperRepository.findById(id)
	    		.orElseThrow(() -> new ResourceNotFoundException("shopper", "id", id));
	    
	    shopperMapper.updateEntity(existing, req);
	}
	
	@Transactional
	public void updateDetails(Long id, 
			ShopperDetailsUpdateRequest newShopperDetails)
	{
		Shopper existing = shopperRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("shopper", "id", id));
		
		shopperMapper.updateEntity(existing, newShopperDetails);
	}
}
