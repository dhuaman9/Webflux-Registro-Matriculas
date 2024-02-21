package com.projectwebflux.services;

import com.projectwebflux.model.Student;

import reactor.core.publisher.Flux;

public interface IStudentService extends ICRUD<Student, String>{

	Flux<Student> findAllByOrderByAgeDesc();
	Flux<Student> findAllByOrderByAgeAsc();
	
}
