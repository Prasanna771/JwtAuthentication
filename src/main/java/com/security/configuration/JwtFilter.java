package com.security.configuration;

import java.io.IOException;
import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.security.service.JwtUtil;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtFilter extends OncePerRequestFilter
{
    @Autowired
    private JwtUtil jwtUtil;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain)
            throws ServletException, IOException
    {
        String authHeader = request.getHeader("Authorization");

        if (authHeader != null && authHeader.startsWith("Bearer ")) 
        {
            String token = authHeader.substring(7);

            // 1. Validate the token and ensure no authentication already exists in this context
            if (jwtUtil.validateToken(token) && SecurityContextHolder.getContext().getAuthentication() == null)
            {
                String username = jwtUtil.extractUsername(token);
                
                // 2. Create the authentication token. 
                // Collections.emptyList() represents user authorities/roles. Update this if you have roles.
                UsernamePasswordAuthenticationToken authToken = 
                        new UsernamePasswordAuthenticationToken(username, null, Collections.emptyList());
                
                // 3. Build details from the request
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                
                // 4. Crucial Step: Set the authentication context for Spring Security
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }

        // Continue down the filter chain
        filterChain.doFilter(request, response);
    }
}