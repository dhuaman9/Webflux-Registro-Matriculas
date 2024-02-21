package com.projectwebflux.repository;

import com.projectwebflux.model.User;

import reactor.core.publisher.Mono;

public interface IUserRepository extends IGenericRepository<User, String>{

	Mono<User> findOneByUsername(String username);
}
