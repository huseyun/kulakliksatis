package com.kulakyokedici.kulakliksitesi.service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.kulakyokedici.kulakliksitesi.mapper.SellerMapper;
import com.kulakyokedici.kulakliksitesi.objects.data.EUserType;
import com.kulakyokedici.kulakliksitesi.objects.data.Seller;
import com.kulakyokedici.kulakliksitesi.objects.data.UserType;
import com.kulakyokedici.kulakliksitesi.objects.data.dto.request.SellerCreateRequest;
import com.kulakyokedici.kulakliksitesi.objects.data.dto.request.SellerDetailsUpdateRequest;
import com.kulakyokedici.kulakliksitesi.objects.data.dto.request.SellerUpdateRequest;
import com.kulakyokedici.kulakliksitesi.objects.data.dto.response.SellerDetailedResponse;
import com.kulakyokedici.kulakliksitesi.objects.data.dto.response.SellerResponse;
import com.kulakyokedici.kulakliksitesi.objects.exception.ResourceNotFoundException;
import com.kulakyokedici.kulakliksitesi.repository.SellerRepository;
import com.kulakyokedici.kulakliksitesi.repository.UserTypeRepository;

import jakarta.transaction.Transactional;

@Service
public class SellerService
{
	private SellerRepository sellerRepository;
    private final UserTypeRepository userTypesRepository;
	private SellerMapper sellerMapper;
	
	@Autowired
	public SellerService(SellerRepository sellerRepository,
			UserTypeRepository userTypesRepository,
			SellerMapper sellerMapper)
	{
		this.sellerRepository = sellerRepository;
		this.userTypesRepository = userTypesRepository;
		this.sellerMapper = sellerMapper;
		
	}
	
	public SellerResponse getById(Long id)
	{
		Seller seller = sellerRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("seller", "id", id));
		
		return sellerMapper.toResponse(seller);
	}
	
	public SellerDetailedResponse getDetailedResponseById(Long id)
	{
		Seller seller = sellerRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("seller", "id", id));
		
		return sellerMapper.toDetailedResponse(seller);
	}
	
	public SellerResponse getByUsername(String username)
	{
		Seller seller = sellerRepository.findByUsername(username)
				.orElseThrow(() -> new ResourceNotFoundException("seller", "username", username));
		
		return sellerMapper.toResponse(seller);
	}
	
	public SellerResponse getByEmail(String email)
	{
		Seller seller = sellerRepository.findByEmail(email)
				.orElseThrow(() -> new ResourceNotFoundException("seller", "email", email));
		
		return sellerMapper.toResponse(seller);
	}
	
	public SellerResponse getByCompanyName(String companyName)
	{
		Seller seller = sellerRepository.findByCompanyName(companyName)
				.orElseThrow(() -> new ResourceNotFoundException("seller", "companyName", companyName));
		
		return sellerMapper.toResponse(seller);
	}
	
	public List<SellerDetailedResponse> getAll()
	{
		List<Seller> allSellers =  sellerRepository.findAll();
		
		return allSellers.stream()
				.map(seller -> sellerMapper.toDetailedResponse(seller))
				.collect(Collectors.toList());
	}
	
	public void add(SellerCreateRequest newSeller)
	{
		Seller seller = sellerMapper.toEntity(newSeller);
		
		seller.getUserTypes().add(userTypesRepository.findByName(EUserType.SELLER)
				.orElseThrow(() -> new ResourceNotFoundException("user type", "user type name", EUserType.SELLER)));
		
		sellerRepository.save(seller);
		
	}
	
	@Transactional
	public void update(Long id, SellerUpdateRequest req)
	{
		Seller existing = sellerRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("seller", "id", id));
		
		sellerMapper.updateEntity(existing, req);
	}
	
	@Transactional
	public void updateDetails(Long id, SellerDetailsUpdateRequest req)
	{
		Seller existing = sellerRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("seller", "id", id));
		
		sellerMapper.updateEntity(existing, req);
	}
}