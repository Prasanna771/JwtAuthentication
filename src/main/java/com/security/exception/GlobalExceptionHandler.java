package com.security.exception;

import java.util.HashMap;
import java.util.Map;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler 
{
	@ExceptionHandler(RuntimeException.class)
	public Map<String, String> handleRuntimeException(RuntimeException ex)
	{
		Map<String, String> error = new HashMap<>();
		
		error.put("Message", ex.getMessage());
		
		return error;
	}
}

