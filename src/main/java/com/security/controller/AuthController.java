package com.security.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.security.service.UserService;


@RestController
public class AuthController 
{

	@Autowired
    private UserService userService;

    @PostMapping("/login")
    public String login(@RequestParam String email,
                        @RequestParam String password) 
    {

        return userService.login(email, password);

    
    }
    
  
    
}
