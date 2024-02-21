package com.projectwebflux.services;

import com.projectwebflux.model.User;

import reactor.core.publisher.Mono;

public interface IUserService extends ICRUD<User, String>{

	
	 Mono<User> saveHash(User user);
	 Mono<com.projectwebflux.security.User> searchByUser(String username);
}
