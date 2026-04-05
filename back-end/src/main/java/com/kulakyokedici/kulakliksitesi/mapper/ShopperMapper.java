package com.kulakyokedici.kulakliksitesi.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.kulakyokedici.kulakliksitesi.objects.data.Shopper;
import com.kulakyokedici.kulakliksitesi.objects.data.dto.request.ShopperCreateRequest;
import com.kulakyokedici.kulakliksitesi.objects.data.dto.request.ShopperDetailsUpdateRequest;
import com.kulakyokedici.kulakliksitesi.objects.data.dto.request.ShopperUpdateRequest;
import com.kulakyokedici.kulakliksitesi.objects.data.dto.response.ShopperResponse;

@Component
public class ShopperMapper
{
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	public ShopperMapper(PasswordEncoder passwordEncoder)
	{
		this.passwordEncoder = passwordEncoder;
	}
	
	public Shopper toEntity(ShopperUpdateRequest newShopper)
	{
		Shopper shopper = new Shopper();
		
		shopper.setUsername(newShopper.username());
		shopper.setEmail(newShopper.email());
		shopper.setFirstName(newShopper.firstName());
		shopper.setLastName(newShopper.lastName());
		
		return shopper;
	}
	
	public Shopper toEntity(ShopperCreateRequest newShopper)
	{
		Shopper shopper = new Shopper();
		
		shopper.setUsername(newShopper.username());
		shopper.setPassword(passwordEncoder.encode(newShopper.password()));
		shopper.setEmail(newShopper.email());
		
		return shopper;
	}
	
	public ShopperResponse toResponse(Shopper shopper)
	{
		return new ShopperResponse(
				shopper.getId(),
				shopper.getUsername(),
				shopper.getEmail(),
				shopper.getFirstName(),
				shopper.getLastName());
	}
	
	public void updateEntity(Shopper shopper, ShopperUpdateRequest req)
	{
		shopper.setEmail(req.email());
		shopper.setFirstName(req.firstName());
		shopper.setLastName(req.lastName());
		shopper.setUsername(req.username());
	}
	
	public void updateEntity(Shopper shopper, ShopperDetailsUpdateRequest req)
	{
		shopper.setFirstName(req.firstName());
		shopper.setLastName(req.lastName());
	}
}
