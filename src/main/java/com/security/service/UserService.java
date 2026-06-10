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
    private UserRepo userRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private RefreshTokenRepo refreshTokenRepo;

  
    // Register
    public String register(User user)
    {
        if (userRepo.findByEmail(user.getEmail()).isPresent())
        {
            throw new RuntimeException(
                    "Email already exists");
        }

        user.setPassword(
                passwordEncoder.encode(
                        user.getPassword()
                )
        );

        // Default role
        if (user.getRole() == null ||
                user.getRole().isBlank())
        {
            user.setRole("USER");
        }

        userRepo.save(user);

        return "User Registered Successfully";
    }

  
    // Login
    public LoginResponse login(
            String email,
            String password)
    {
        System.out.println(
                "Email received: " + email);

        User user = userRepo.findByEmail(email)
                .orElseThrow(() ->
                        new RuntimeException(
                                "User Not Found"));

        boolean matches =
                passwordEncoder.matches(
                        password,
                        user.getPassword()
                );

        if (!matches)
        {
            throw new RuntimeException(
                    "Invalid Credentials");
        }

       

        
        // Generate Access Token
        String accessToken =
                jwtUtil.generateToken(
                        user.getEmail(),
                        user.getRole()
                );

        
        // Generate Refresh Token
        String refreshToken =
                jwtUtil.generateRefreshToken(
                        user.getEmail()
                );

        // Delete old refresh token
        refreshTokenRepo
                .findByUser(user)
                .ifPresent(
                        refreshTokenRepo::delete
                );

        // Save new refresh token
        RefreshToken refresh =
                new RefreshToken();

        refresh.setToken(refreshToken);
        refresh.setUser(user);
        refresh.setExpiryDate(
                LocalDateTime.now()
                        .plusDays(1)
        );

        refreshTokenRepo.save(refresh);

        return new LoginResponse(
                accessToken,
                refreshToken
        );
    }

    
    // Refresh Token
    public LoginResponse refreshToken(
            String refreshToken)
    {
        RefreshToken token =
                refreshTokenRepo
                        .findByToken(refreshToken)
                        
                        .orElseThrow(() -> new RuntimeException("Refresh Token Not Found" ));
                               

        if (token.getExpiryDate().isBefore(LocalDateTime.now()))
        {
            refreshTokenRepo.delete(token);

            throw new RuntimeException("Refresh Token Expired");
        }

        String newAccessToken =jwtUtil.generateToken(
                        token.getUser().getEmail(),
                        token.getUser().getRole());
        
        return new LoginResponse(newAccessToken,refreshToken);
       
    }

    
    // Logout
    public String logout(String refreshToken)
    {
        RefreshToken token =
                refreshTokenRepo
                        .findByToken(refreshToken).orElseThrow(() ->new RuntimeException("Refresh Token Not Found"));

        refreshTokenRepo.delete(token);

        return "Logged Out Successfully";
    }
}