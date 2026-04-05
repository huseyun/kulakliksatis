// AI

package com.kulakyokedici.kulakliksitesi.config.security.filter;

import java.io.IOException;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.kulakyokedici.kulakliksitesi.service.security.JwtService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;

    public JwtAuthenticationFilter(JwtService jwtService, UserDetailsService userDetailsService) {
        this.jwtService = jwtService;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        final String authHeader = request.getHeader("Authorization");

        // Token yoksa veya formatı yanlışsa, isteği sonraki filtreye devret ve çık.
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        String jwt = authHeader.substring(7);

        // 1. Gelen token'ı ayrıştırıp içinden kullanıcı adını (subject) alıyoruz.
        String username = jwtService.extractUsername(jwt);

        // 2. Kullanıcı adı varsa VE bu istek için daha önce kimlik doğrulaması yapılmamışsa...
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            
            // 3. UserDetailsService'i çağırarak sistemdeki kullanıcıyı buluyoruz.
            //    Bu, kullanıcının rollerini ve yetkilerini almak için gerekli.
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);

            // 4. Token'ın hala geçerli olup olmadığını kontrol ediyoruz.
            if (jwtService.isTokenValid(jwt, userDetails)) {
                
                // 5. HER ŞEY YOLUNDA! Artık Spring Security'e bu kullanıcının kimliğinin
                //    doğrulandığını söyleyebiliriz. Bunun için bir Authentication nesnesi oluşturup
                //    SecurityContextHolder'a yerleştiriyoruz.
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities()
                );
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }
        
        // 6. İsteği zincirdeki bir sonraki filtreye iletiyoruz.
        filterChain.doFilter(request, response);
    }
}