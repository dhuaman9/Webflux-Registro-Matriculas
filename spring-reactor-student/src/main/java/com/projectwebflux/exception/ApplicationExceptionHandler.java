package com.projectwebflux.exception;

import java.time.format.DateTimeParseException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ApplicationExceptionHandler {

	@ExceptionHandler({NullPointerException.class })
	public ResponseEntity<Map<String, Object>> nullPointerException(NullPointerException e) {
		Map<String, Object> response = new HashMap<>();
		response.put("message", "Bad Request, existen valores nulos");
		response.put("responseCode", 400);
		response.put("errorDetails", e.getMessage());
		return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler({DateTimeParseException.class })
	public ResponseEntity<Map<String, Object>> dateTimeException(DateTimeParseException e) {
		Map<String, Object> response = new HashMap<>();
		response.put("message", "Error al ingresar la fecha, debe tener el formato: yyyy-MM-dd HH:mm:ss");
		response.put("responseCode", 400);
		response.put("errorDetails", e.getMessage());
		return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler({DuplicateKeyException.class })
	public ResponseEntity<Map<String, Object>> duplicateDNIException(DuplicateKeyException e) {
		Map<String, Object> response = new HashMap<>();
		response.put("message", "Bad Request: El DNI ya existe en la base de datos.");
		response.put("responseCode", 400);
		response.put("errorDetails", e.getMessage());
		return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
	}
	
}
