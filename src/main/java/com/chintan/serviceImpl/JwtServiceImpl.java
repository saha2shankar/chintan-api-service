package com.chintan.serviceImpl;

import java.security.Key;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.chintan.entity.User;
import com.chintan.service.JwtService;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtServiceImpl implements JwtService {

	private String secretkey = "";

	public JwtServiceImpl() {
		try {
			KeyGenerator keyGen = KeyGenerator.getInstance("HmacSHA256");
			SecretKey sk = keyGen.generateKey();
			secretkey = Base64.getEncoder().encodeToString(sk.getEncoded());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public String generateToken(User user) {
		Map<String, Object> claims = new HashMap<>();
		claims.put("role", user.getRoles());
		claims.put("status", user.getStatus().getIsActive());

		String token = Jwts.builder().claims().add(claims)
				.subject(user.getEmail())
				.issuedAt(new Date(System.currentTimeMillis()))
				.expiration(new Date(System.currentTimeMillis() + 60 * 60 * 10))
				.and()
				.signWith(getkey())
				.compact();
		return token;
	}

	private Key getkey() {
		byte[] keyBytes = Decoders.BASE64.decode(secretkey);
		return Keys.hmacShaKeyFor(keyBytes);
	}

	@Override
	public String extractUsername(String token) {
		
		Claims claims =  extractAllClaims(token);
		
		return claims.getSubject();
	}
	
	public String role(String token) {
		Claims claims =  extractAllClaims(token);
		String role = (String)claims.get("role");
		return role;
	}

	private Claims extractAllClaims(String token) {
		Claims claims = Jwts.parser()
				.verifyWith(decrytKey(secretkey))
				.build()
				.parseSignedClaims(token)
				.getPayload();
		return claims;
	}

	private SecretKey decrytKey(String secretkey) {
		byte[] keyBytes = Decoders.BASE64.decode(secretkey);
	return Keys.hmacShaKeyFor(keyBytes);
	
	}

	@Override
	public Boolean validateToken(String token, UserDetails userDetails) {
		String username = extractUsername(token);
	Boolean isExpired =	isTokenExpired(token);
	if(username.equalsIgnoreCase(userDetails.getUsername()) && !isExpired)
		
	{
		return true;
	}
		return false;
	}

	private Boolean isTokenExpired(String token) {
		Claims claims = extractAllClaims(token);
		Date expiredDate = claims.getExpiration();
		return expiredDate.before(new Date());
	}
}
