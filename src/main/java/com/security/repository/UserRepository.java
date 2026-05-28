package com.security.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.security.entities.Users;

public interface UserRepository extends JpaRepository<Users, Long> 
{
	Optional<Users> findByEmail(String email);

}
