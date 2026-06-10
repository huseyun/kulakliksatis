package com.kulakyokedici.kulakliksitesi.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.kulakyokedici.kulakliksitesi.objects.data.Category;
import com.kulakyokedici.kulakliksitesi.objects.data.ECategory;

public interface CategoryRepository extends CrudRepository<Category, Long> {
	public Optional<Category> findByCategory(ECategory category);
}
