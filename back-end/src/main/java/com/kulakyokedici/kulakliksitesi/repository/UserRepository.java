package com.kulakyokedici.kulakliksitesi.repository;


import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.kulakyokedici.kulakliksitesi.objects.data.User;

public interface UserRepository extends CrudRepository<User, Long>
{
	public Optional<User> findById(Long id);
	
	public Optional<User> findByUsername(String username);
	
	public Optional<User> findByEmail(String email);
	
	public List<User> findAll();
	
	public boolean existsByUsername(String username);
}