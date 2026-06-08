package com.security.service;

import java.time.LocalDateTime;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.security.entities.RefreshToken;
import com.security.entities.User;
import com.security.repository.RefreshTokenRepo;
import com.security.repository.UserRepo;

import dto.LoginResponse;


@Service
public class UserService 

{
	@Autowired
	public UserRepo userRepo;
	
	@Autowired
	public PasswordEncoder passwordEncoder;
	
	@Autowired
	private JwtUtil jwtUtil;
	
	@Autowired
	private RefreshTokenRepo refreshTokenRepo;
	
	public String register(User user)
	{
		if(userRepo.findByEmail(user.getEmail()).isPresent())
		{
			return "Email already exists";
		}
		
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		
		userRepo.save(user);
		
		return "User Registered Successfully";
	}
	
	
		public LoginResponse login(String email, String password)
			{
				System.out.println("Email received: " + email);
				User user = userRepo.findByEmail(email).orElseThrow(()->new RuntimeException("User not Found"));
				boolean matches= passwordEncoder.matches(password, user.getPassword());
		
				if(matches) 
					{
			
					 String accessToken =
				                jwtUtil.generateToken(
				                        user.getEmail()
				                );

					 String refreshToken = jwtUtil.generateRefreshToken(user.getEmail());
					 
					 RefreshToken refresh = new RefreshToken();
					 refreshTokenRepo.findByUser(user).ifPresent(refreshTokenRepo::delete);
					 refresh.setToken(refreshToken);
					 refresh.setUser(user);
					 refresh.setExpiryDate(LocalDateTime.now().plusHours(2));
					 refreshTokenRepo.save(refresh);
				        return new LoginResponse(accessToken,refreshToken);
					
					}
				throw new RuntimeException("Invalid Credentials");
				
	}
		
		public LoginResponse refreshToken(String refreshToken)
		{
		    RefreshToken token =
		            refreshTokenRepo.findByToken(refreshToken)
		            .orElseThrow(() ->
		                    new RuntimeException(
		                            "Refresh Token Not Found"));

		    if(token.getExpiryDate()
		            .isBefore(LocalDateTime.now()))
		    {
		    	refreshTokenRepo.delete(token);
		    	
		        throw new RuntimeException(
		                "Refresh Token Expired");
		    }

		    String newAccessToken =
		            jwtUtil.generateToken(
		                    token.getUser().getEmail()
		            );

		    return new LoginResponse(
		            newAccessToken,
		            refreshToken
		    );
		}
}
