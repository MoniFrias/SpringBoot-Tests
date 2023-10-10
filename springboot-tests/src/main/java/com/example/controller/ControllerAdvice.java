package com.example.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.example.dto.Response;
import com.example.dto.ValidationException;

@RestControllerAdvice
public class ControllerAdvice {

	@ExceptionHandler(value = ValidationException.class)
	public ResponseEntity<Response> validationStudent(final ValidationException validation){
		Response response  = new Response(false, validation.getMessage(), null);
		return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
	}
}
