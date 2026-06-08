package com.security.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.security.entities.RefreshToken;
import com.security.repository.RefreshTokenRepo;

@Service
public class RefreshTokenService
{
	@Autowired
	public RefreshTokenRepo refreshTokenRepo;
	
	
	@Autowired
	private JwtUtil jwtUtil;
	
	public String logout(String refreshToken)
	{
		RefreshToken token =
				refreshTokenRepo.findByToken(refreshToken)
				.orElseThrow(()->new RuntimeException("Refresh Token not found"));
		
		refreshTokenRepo.delete(token);
		
		return "Logged Out successfully";
				
	}
	

}
