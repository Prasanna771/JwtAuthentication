package com.security.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.security.entities.RefreshToken;
import com.security.entities.User;

@Repository
public interface RefreshTokenRepo extends JpaRepository<RefreshToken, Long>
{
	Optional<RefreshToken> findByToken(String token);
	
	Optional<RefreshToken> findByUser(User user);

}
