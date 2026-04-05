package com.kulakyokedici.kulakliksitesi.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.kulakyokedici.kulakliksitesi.objects.data.EUserType;
import com.kulakyokedici.kulakliksitesi.objects.data.UserType;

// Bu interface, UserTypes tablosunda findById gibi temel veritabanı işlemlerini yapmamızı sağlar.
public interface UserTypeRepository extends CrudRepository<UserType, Long> {
	public Optional<UserType> findByName(EUserType roleAdmin);
}