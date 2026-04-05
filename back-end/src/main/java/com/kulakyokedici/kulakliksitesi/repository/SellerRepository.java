package com.kulakyokedici.kulakliksitesi.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.repository.CrudRepository;

import com.kulakyokedici.kulakliksitesi.objects.data.Seller;

public interface SellerRepository extends CrudRepository<Seller, Long>
{
	@EntityGraph(attributePaths = {"items", "items.images", "items.smallImages"})
	public Optional<Seller> findById(Long id);
	
	public Optional<Seller> findByUsername(String username);
	
	public Optional<Seller> findByEmail(String username);
	
	public Optional<Seller> findByCompanyName(String companyName);
	
	// eager loading, proxy yerine tek sorguda getir.
	@EntityGraph(attributePaths = {"items", "items.images", "items.smallImages"})
	public List<Seller> findAll();
}
