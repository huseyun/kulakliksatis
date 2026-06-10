package com.kulakyokedici.kulakliksitesi.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.kulakyokedici.kulakliksitesi.objects.data.Cart;

public interface CartRepository extends CrudRepository<Cart, Long>
{
	public Optional<Cart> findById(Long id);
}
