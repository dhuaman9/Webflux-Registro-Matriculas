package com.projectwebflux.services.implement;

import org.springframework.stereotype.Service;

import com.projectwebflux.model.Enrollment;
import com.projectwebflux.repository.IEnrollmentRepository;
import com.projectwebflux.repository.IGenericRepository;
import com.projectwebflux.services.IEnrollmentService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EnrollmentServiceImpl extends CRUDImpl<Enrollment, String> implements IEnrollmentService {

	private final IEnrollmentRepository repository;
	 
	@Override
	protected IGenericRepository<Enrollment, String> getRepository() {
		return repository;
	}
	
	

}
