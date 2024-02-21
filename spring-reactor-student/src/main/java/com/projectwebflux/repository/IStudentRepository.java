package com.projectwebflux.repository;

import com.projectwebflux.model.Student;

import reactor.core.publisher.Flux;

public interface IStudentRepository extends IGenericRepository<Student, String>{

	Flux<Student> findAllByOrderByAgeDesc();
	Flux<Student> findAllByOrderByAgeAsc();
}
