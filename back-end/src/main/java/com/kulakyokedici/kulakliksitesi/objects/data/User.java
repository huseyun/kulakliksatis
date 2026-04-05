package com.kulakyokedici.kulakliksitesi.objects.data;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

//hibernate annotasyonu.
@Entity
//alt siniflarda nasil baglanacak? yeni tablo olusturup foreign key ile.
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "all_users")
public abstract class User
{
	
	public User() {}
	
	public User(String username, String password)
	{
		this.username = username;
		this.password = password;
	}
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "username", unique = true)
	@Size(
			min = 4,
			max = 16,
			message = "username is too short or long")
	@NotBlank
	private String username;
	
	@Column(name = "password")
	@NotBlank
	private String password;
	
	@Column(name = "email", unique = true)
	@Email(message = "wrong e-mail format")
	private String email;
	
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_typelist",
    	joinColumns = @JoinColumn(name = "user_id"),
    	inverseJoinColumns = @JoinColumn(name = "usertype_id"))
	private Set<UserType> userTypes = new HashSet<>();
    
    @CreationTimestamp
    private LocalDateTime userCreationTime;
    
    @UpdateTimestamp
    private LocalDateTime userLastupdated;
    
    public String getPassword()
    {
    	return password;
    }
    
    public String getUsername()
    {
    	return username;
    }
    
    public Long getId()
    {
    	return id;
    }
    
    public String getEmail()
    {
    	return email;
    }
    
    public Set<UserType> getUserTypes()
    {
    	return userTypes;
    }
    
    public void setUsername(String username)
    {
    	this.username =  username;
    }
    
    public void setPassword(String password)
    {
    	this.password = password;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    public void setUserTypes(Set<UserType> userTypes) {
        this.userTypes = userTypes;
    }
    
    public void resetId()
	{
		this.id = null;
	}
    
    public void addUserType(UserType userType) {
        this.userTypes.add(userType);
    }

    public void removeUserType(UserType userType) {
        this.userTypes.remove(userType);
    }

    public boolean hasUserType(String typeName) {
        return userTypes.stream()
            .anyMatch(type -> type.getName().equals(typeName));
    }
    
//	public void fullUpdate(User sourceUser)
//	{
//		this.setUsername(sourceUser.getUsername());
//		this.setEmail(sourceUser.getEmail());
//		this.setPassword(sourceUser.getPassword());
//	}
}
