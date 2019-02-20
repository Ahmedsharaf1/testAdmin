package com.sharaf.security.demo.security;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class TokenUtil {
	
	private final String CLAIMS_SUBJECT= "sub";
	private final String CLAIMS_CREATED = "created";
	@Value("${auth.experation}")
	private long TOKEN_VALIDATY =608400L;
	@Value("${auth.secert}")
	private String TOKEN_SECRET;
	
	public String generatedToken(UserDetails userDetails) {
		Map<String ,Object> claims =new HashMap<>();
		claims.put(CLAIMS_SUBJECT, userDetails.getUsername());
		claims.put(CLAIMS_CREATED,new Date());
		return Jwts.builder().setClaims(claims).setExpiration(generatedExpiration()).
	         	signWith(SignatureAlgorithm.HS512, TOKEN_SECRET).compact();
	}
		
	public String getUserNameFromToken(String token) {
		try {
			Claims claims = getClaims(token);
			return claims.getSubject();
		}
		catch(Exception ex) {
			return null;
		}
	}
	public  boolean isTokenValid(String token, UserDetails userDetails) {
		String username = getUserNameFromToken(token);
		return (username.equals(userDetails.getUsername()) &&  !isTokenExp(token));
	}
	
	private Date generatedExpiration() {
		return new Date(System.currentTimeMillis()+TOKEN_VALIDATY*1000);
	}
	
	private boolean isTokenExp(String token) {
		Date expiration = getClaims(token).getExpiration();
		return expiration.before(new Date(System.currentTimeMillis()));
	}
	private Claims getClaims(String token) {
		Claims claims;
		try {
			claims = Jwts.parser().setSigningKey(TOKEN_SECRET).parseClaimsJws(token).getBody();
			return claims;	
		}
		catch(Exception ex) {
			claims = null;
		}
		return claims;
	}

}
