package com.projectwebflux.services.implement;

import org.springframework.stereotype.Service;

import com.projectwebflux.model.Student;
import com.projectwebflux.repository.IGenericRepository;
import com.projectwebflux.repository.IStudentRepository;
import com.projectwebflux.services.IStudentService;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;

@Service
@RequiredArgsConstructor
public class StudentServiceImpl extends CRUDImpl<Student, String> implements IStudentService {

	private final IStudentRepository repository;
	
	@Override
	protected IGenericRepository<Student, String> getRepository() {
		return repository;
	}

	@Override
	public Flux<Student> findAllByOrderByAgeDesc() {
		return repository.findAllByOrderByAgeDesc();
	}

	@Override
	public Flux<Student> findAllByOrderByAgeAsc() {
		return repository.findAllByOrderByAgeAsc();
	}

	
	
	

}
