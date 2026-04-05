package com.kulakyokedici.kulakliksitesi.objects.security;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.kulakyokedici.kulakliksitesi.objects.data.User;

public class SecurityUser implements UserDetails
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -5057701963947689943L;
	private User user;
	
	public SecurityUser(User user)
	{
		this.user = user;
	}
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities()
	{
		List<SimpleGrantedAuthority> list = user.getUserTypes().stream()
				.map(userType -> new SimpleGrantedAuthority("ROLE_" + userType.getName()))
				.collect(Collectors.toList());
		return list;
	}

	@Override
	public String getPassword()
	{
		return user.getPassword();
	}

	@Override
	public String getUsername()
	{
		return user.getUsername();
	}
	
	@Override
	public boolean isAccountNonExpired() {
	    return true;
	}

	@Override
	public boolean isAccountNonLocked() {
	    return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
	    return true;
	}

	@Override
	public boolean isEnabled() {
	    return true;
	}

}
