package com.projectwebflux.controllers;


import lombok.RequiredArgsConstructor;

import java.util.Date;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.projectwebflux.security.AuthRequest;
import com.projectwebflux.security.AuthResponse;
import com.projectwebflux.security.ErrorLogin;
import com.projectwebflux.security.JwtUtil;
import com.projectwebflux.services.IUserService;

import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
public class LoginController {

	private final JwtUtil jwtUtil;
	private final IUserService service;

	@PostMapping("/login")
	public Mono<ResponseEntity<?>> login(@RequestBody AuthRequest authRequest) {
		return service.searchByUser(authRequest.getUsername()).map(userDetails -> {
			if (BCrypt.checkpw(authRequest.getPassword(), userDetails.getPassword())) {
				String token = jwtUtil.generateToken(userDetails);
				Date expiration = jwtUtil.getExpirationDateFromToken(token);

				return ResponseEntity.ok(new AuthResponse(token, expiration));
			} else {
				return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
						.body(new ErrorLogin("Bad Credentials", new Date()));
			}
		}).defaultIfEmpty(ResponseEntity.status(HttpStatus.UNAUTHORIZED).build());
	}

}
