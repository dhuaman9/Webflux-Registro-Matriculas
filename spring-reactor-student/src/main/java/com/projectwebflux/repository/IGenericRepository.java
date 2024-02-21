package com.projectwebflux.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.data.repository.NoRepositoryBean;

//para evitar que Spring Data la detecte como un repositorio.  ¿?
@NoRepositoryBean
public interface IGenericRepository <T, ID> extends ReactiveMongoRepository<T, ID>{
	
	

}
