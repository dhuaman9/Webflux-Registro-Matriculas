package com.projectwebflux.services.implement;


import org.springframework.stereotype.Service;

import com.projectwebflux.model.Course;
import com.projectwebflux.repository.IGenericRepository;
import com.projectwebflux.repository.ICourseRepository;
import com.projectwebflux.services.ICourseService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CourseServiceImpl extends CRUDImpl<Course, String> implements ICourseService {

	private final ICourseRepository repository;
	 
	@Override
	protected IGenericRepository<Course, String> getRepository() {
		return repository;
	}


	

}
