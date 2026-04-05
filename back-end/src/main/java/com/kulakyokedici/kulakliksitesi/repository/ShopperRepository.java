package com.kulakyokedici.kulakliksitesi.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.kulakyokedici.kulakliksitesi.objects.data.Shopper;

public interface ShopperRepository extends CrudRepository<Shopper, Long>
{
	public Optional<Shopper> findById(Long id);
	
	public Optional<Shopper> findByUsername(String username);
	
	public Optional<Shopper> findByEmail(String email);
	
	public List<Shopper> findAll();
}
