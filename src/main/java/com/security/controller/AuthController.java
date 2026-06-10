package com.security.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.security.entities.User;
import com.security.service.JwtUtil;
import com.security.service.RefreshTokenService;
import com.security.service.UserService;

import dto.LoginRequest;
import dto.LoginResponse;
import dto.RefreshRequest;

@RestController
public class AuthController 
{
	@Autowired
	public UserService userService;
	
	@Autowired
	public RefreshTokenService refreshTokenService;
	
	
	@GetMapping("/greet")
	public String greet()
	{
		return "Hello, Welcome!";
	}
	
	@GetMapping("/test")
	public String test()
	{
		return "test endpoint";
	}
	
	
	@PostMapping("/register")
	public String register(@RequestBody User user)
	{
		return userService.register(user);
	}
	
	
	@PostMapping("/login")
	public LoginResponse login(@RequestBody LoginRequest request)
	{
		return userService.login(
				request.getEmail(),
				request.getPassword()
				);
	}
	
	
	@PostMapping("/refresh")
	public LoginResponse refreshToken(
	        @RequestBody RefreshRequest request)
	{
	    return userService.refreshToken(
	            request.getRefreshToken()
	    );
	}
	
	
	@PostMapping("/api/logout")
	public String logout(@RequestBody RefreshRequest request)
	{
		return refreshTokenService.logout(request.getRefreshToken());
	}
	
	
	
	@PutMapping("/update")
    public String update()
    {
        return "Data Updated!";
    }

    @DeleteMapping("/delete")
    public String delete()
    {
        return "Data Deleted!";
    }
	
	
	
}
