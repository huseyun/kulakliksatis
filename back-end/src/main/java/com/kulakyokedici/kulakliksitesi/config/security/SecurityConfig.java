package com.kulakyokedici.kulakliksitesi.config.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.kulakyokedici.kulakliksitesi.config.security.filter.JwtAuthenticationFilter;



@Configuration
public class SecurityConfig {
	
	private final JwtAuthenticationFilter jwtAuthFilter;
	private final UserDetailsService userDetailsService;
	
	@Autowired
	public SecurityConfig(JwtAuthenticationFilter jwtAuthFilter, UserDetailsService userDetailsService) {
		this.jwtAuthFilter = jwtAuthFilter;
		this.userDetailsService = userDetailsService;
	}
	
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    
    @Bean // galiba standart
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
    
    @Bean // standart
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider(userDetailsService);
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }
    
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception
	{
		http.cors(Customizer.withDefaults())
		.csrf(csrf -> csrf.disable())
		.sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
		.addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class) // Kendi filtremizi standart filtreden önceye ekliyoruz.
		.authorizeHttpRequests((authz) -> authz
                .requestMatchers("/api/auth/login").permitAll()
                .requestMatchers("/error").permitAll()
                .requestMatchers("/api/admin/**").hasRole("ADMIN")
//                .requestMatchers("/api/profile/").hasAnyRole("ADMIN", "USER")
//				.requestMatchers(HttpMethod.GET, "/api/**").hasAnyRole("GUEST", "USER", "ADMIN")
//                .requestMatchers("/api/**").permitAll()
				.anyRequest().permitAll());
		
		return http.build();
	}

}
