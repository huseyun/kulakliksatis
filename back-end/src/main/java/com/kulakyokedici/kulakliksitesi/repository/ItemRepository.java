package com.kulakyokedici.kulakliksitesi.repository;

import java.util.List;
import java.util.Optional;
import java.util.SortedSet;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.kulakyokedici.kulakliksitesi.objects.data.Item;

public interface ItemRepository extends CrudRepository<Item, Long> {
    
	// eager loading, proxy yerine tek sorguda getir.
	@EntityGraph(attributePaths = {"images", "smallImages"})
    SortedSet<Item> findBySellerId(Long sellerId);
	
	@EntityGraph(attributePaths = {"seller", "images", "smallImages"})
	Optional<Item> findById(Long id);
    
    Optional<Item> findByIdAndSellerId(Long itemId, Long sellerId);
    
    @Query("SELECT i FROM Item i WHERE i.seller.username = :username")
    SortedSet<Item> findBySellerUsername(@Param("username") String username);
    
    public boolean existsByName(String itemName);
    
    @EntityGraph(attributePaths = {"images", "smallImages"})
    public List<Item> findAll();
}