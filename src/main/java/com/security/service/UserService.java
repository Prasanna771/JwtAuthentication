package com.security.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.security.entities.Users;
import com.security.repository.UserRepository;

@Service
public class UserService 
{
	@Autowired
    private UserRepository userRepo;
	
	@Autowired
	private JwtUtil jwtUtil;


	public String login(String email, String password) {

        Users user = userRepo.findByEmail(email)
                .orElseThrow(() ->
                        new RuntimeException("User not found! Please register."));

        
        if (!user.getPassword().equals(password)) {
            throw new RuntimeException("Invalid password!");
        }

        
        return jwtUtil.generateToken(email);
    
}}
