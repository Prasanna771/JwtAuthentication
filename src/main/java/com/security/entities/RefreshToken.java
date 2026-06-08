package com.security.entities;

import java.time.LocalDateTime;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;

@Entity
public class RefreshToken 
{
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;
	
	private String token;
	
	private LocalDateTime expiryDate;
	
	@OneToOne
	private User user;

	
	public long getId()
	{
		return id;
	}

	
	
	public void setId(long id)
	{
		this.id = id;
	}

	
	public String getToken() 
	{
		return token;
	}

	
	public void setToken(String token) 
	{
		this.token = token;
	}

	

	public LocalDateTime getExpiryDate() 
	{
		return expiryDate;
	}

	public void setExpiryDate(LocalDateTime expiryDate)
	{
		this.expiryDate = expiryDate;
	}

	
	public User getUser()
	{
		return user;
	}

	
	public void setUser(User user) 
	{
		this.user = user;
	}
	
	
	
	

}
