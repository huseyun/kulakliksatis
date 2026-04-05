//AI

package com.kulakyokedici.kulakliksitesi.service.security;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService 
{

	@Value("${jwt-secret-key}")
    private String SECRET_KEY;

    // Token'ın geçerlilik süresi (milisaniye cinsinden). Örnek: 24 saat.
    private static final long EXPIRATION_TIME = 1000 * 60 * 60 * 24;

    /**
     * Gelen bir token'dan kullanıcı adını (subject) çıkarır.
     */
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    /**
     * Gelen bir token'ı ve kullanıcı detaylarını alarak token'ın geçerli olup olmadığını kontrol eder.
     * Kullanıcı adı eşleşmeli ve token'ın süresi dolmamış olmalı.
     */
    public boolean isTokenValid(String token, UserDetails userDetails) 
    {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername())) && !isTokenExpired(token);
    }

    /**
     * Sadece UserDetails kullanarak yeni bir JWT oluşturur.
     */
    public String generateToken(UserDetails userDetails) 
    {
        // Token'a ekstra bilgiler (claims) eklemek istemediğimiz için boş bir map kullanıyoruz.
        return generateToken(new HashMap<>(), userDetails);
    }

    /**
     * UserDetails ve token'a eklenecek ekstra bilgiler (claims) ile yeni bir JWT oluşturur.
     * Örneğin, kullanıcının rollerini veya diğer özel bilgilerini buraya ekleyebilirsiniz.
     */
    public String generateToken(Map<String, Object> extraClaims, UserDetails userDetails) 
    {
        return Jwts.builder()
                .setClaims(extraClaims) // Ekstra bilgileri ekle
                .setSubject(userDetails.getUsername()) // Token'ın konusunu (sahibini) ayarla
                .setIssuedAt(new Date(System.currentTimeMillis())) // Başlangıç zamanını ayarla
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME)) // Bitiş zamanını ayarla
                .signWith(getSignInKey(), SignatureAlgorithm.HS256) // İmza algoritması ve anahtarı ayarla
                .compact(); // Token'ı oluştur ve string olarak döndür
    }

    // --- Private Yardımcı Metotlar ---

    private boolean isTokenExpired(String token) 
    {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) 
    {
        return extractClaim(token, Claims::getExpiration);
    }

    /**
     * Token içerisindeki tüm bilgileri (claims) çeken ana metot.
     */
    private Claims extractAllClaims(String token) 
    {
        return Jwts.parserBuilder()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    /**
     * Tek bir claim'i çıkarmak için kullanılan genel (generic) metot.
     * Hangi claim'i istediğimizi bir fonksiyon olarak (claimsResolver) iletiyoruz.
     */
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) 
    {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    /**
     * String olarak tanımlanan SECRET_KEY'i, JWT imzalama işlemi için uygun
     * bir Key nesnesine dönüştürür.
     */
    private Key getSignInKey() 
    {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}