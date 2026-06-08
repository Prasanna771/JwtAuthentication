package dto;

public class LoginResponse 
{
	private String accessToken;
	private String refreshToken;
	
	
	public LoginResponse(String accessToken)
	{
		this.accessToken = accessToken;
	}


	public String getAccessToken()
	{
		return accessToken;
	}
	
	
	public void setAccessToken(String accessToken) 
	{
		this.accessToken = accessToken;
	}


	public String getRefreshToken() 
	{
		return refreshToken;
	}


	public void setRefreshToken(String refreshToken) 
	{
		this.refreshToken = refreshToken;
	}


	public LoginResponse(String accessToken, String refreshToken) 
	{
		super();
		this.accessToken = accessToken;
		this.refreshToken = refreshToken;
	}

	
}
