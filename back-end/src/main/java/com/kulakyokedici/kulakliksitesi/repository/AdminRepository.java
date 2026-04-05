package com.kulakyokedici.kulakliksitesi.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.kulakyokedici.kulakliksitesi.objects.data.Admin;

public interface AdminRepository extends CrudRepository<Admin, Long>
{
	public Optional<Admin> findById(Long id);
	
	public Optional<Admin> findByUsername(String username);
	
	public Optional<Admin> findByEmail(String email);
	
	public List<Admin> findAll();
}
