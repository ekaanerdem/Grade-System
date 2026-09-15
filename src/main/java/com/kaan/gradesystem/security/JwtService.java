package com.kaan.gradesystem.security;

import io.jsonwebtoken.Jwts;                    //JWT oluşturmak ve okumak için
import io.jsonwebtoken.security.Keys;           //SECRET_KEY'i JWT'nin kullanabileceği anahtara çevirmek için
import org.springframework.stereotype.Service;  //@Service anotasyonu için

import javax.crypto.SecretKey;                  //JWT imzasında kullandığımız anahtarın Java tipi
import java.nio.charset.StandardCharsets;       //String secret'ı byte'a çevirirken UTF-8 kullanmak için
import java.util.Date;                          //issuedAt ve expiration zamanları için

/* generateToken()  →  extractUsername()  →  isTokenValid()
         ↓                   ↓                      ↓
     JWT üretir     JWT kime ait öğrenir    Beklediğimiz kullanıcıya ait mi kontrol eder */


@Service
public class JwtService{

    //Silindi çünkü JWT'yi imzalayan anahtar normalde kaynak kodunda bulunmamalı.
    //private final String SECRET_KEY = "grade-system-jwt-secret-key-2026"; //JWT'yi imzalamak için kullanılan gizli anahtar

    private final String SECRET_KEY = System.getenv("JWT_SECRET");

    private SecretKey getSigningKey(){
        return Keys.hmacShaKeyFor(SECRET_KEY.getBytes(StandardCharsets.UTF_8));
    }

public String generateToken (String username){
    return Jwts.builder()
            .subject(username)
            .issuedAt(new Date())
            .expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60))
            .signWith(getSigningKey())
            .compact();
    }

    //gelen token'ın içinden kullanıcı adını okuma:

public String extractUsername(String token){
    return Jwts.parser()                        //“Bir JWT okuyacağım.”
            .verifyWith(getSigningKey())       //“Bu token bizim secret key'imizle doğru şekilde imzalanmış mı kontrol et.”
            .build()                          
            .parseSignedClaims(token)         //JWT'yi parçala ve içindeki claim'leri (taşınan bigileri) oku.
            .getPayload()
            .getSubject();                 //sub değerini getiriyor.
}

    //token gerçekten bu kullanıcıya mı ait ve hâlâ geçerli mi?

public boolean isTokenValid(String token, String username){   //admin == admin    true ✅
    String tokenUsermane = extractUsername(token);           //user == admin    false ❌
    return tokenUsermane.equals(username);
}



}


