package com.kulakyokedici.kulakliksitesi.objects.data;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "user_types")
public class UserType
{
	public UserType() {}
	
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Enumerated(EnumType.STRING)
    private EUserType name;
    
    public EUserType getName()
    {
    	return name;
    }
    
    public Long getId()
    {
    	return id;
    }
    
    public void setName(EUserType name)
    {
    	this.name = name;
    }
}
