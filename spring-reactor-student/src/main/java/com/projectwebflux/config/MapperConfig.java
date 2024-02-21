package com.projectwebflux.config;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import com.projectwebflux.dto.CourseDTO;
import com.projectwebflux.dto.EnrollmentDTO;
import com.projectwebflux.model.Course;
import com.projectwebflux.model.Enrollment;

@Configuration
public class MapperConfig {
	
	@Bean("defaultMapper")
	@Primary
	public ModelMapper defaultMapper() {
		return new ModelMapper();
	}

	@Bean("courseMapper")
	public ModelMapper CourseMapper() {
		ModelMapper mapper = new ModelMapper();
		mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

		//Escritura
        TypeMap<CourseDTO, Course> typeMap1 = mapper.createTypeMap(CourseDTO.class, Course.class);
        typeMap1.addMapping(CourseDTO::getNameCourse, (dest, v) -> dest.setName((String) v));
        typeMap1.addMapping(CourseDTO::getAcronymCourse, (dest, v) -> dest.setAcronym((String) v));
        typeMap1.addMapping(CourseDTO::getStateCourse, (dest, v) -> dest.setState((Boolean) v));

        //Lectura
        TypeMap<Course, CourseDTO> typeMap2 = mapper.createTypeMap(Course.class, CourseDTO.class);
        typeMap2.addMapping(Course::getName, (dest, v) -> dest.setNameCourse((String) v));
        typeMap2.addMapping(Course::getAcronym, (dest, v) -> dest.setAcronymCourse((String) v));
        typeMap2.addMapping(Course::getState, (dest, v) -> dest.setStateCourse((Boolean) v));

		return mapper;
	}
	
	@Bean("enrollmentMapper")
	public ModelMapper EnrollmentMapper() {
		ModelMapper mapper = new ModelMapper();

		mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

		 //Lectura
        TypeMap<Enrollment, EnrollmentDTO> typeMap1 = mapper.createTypeMap(Enrollment.class, EnrollmentDTO.class);
        typeMap1.addMapping(e -> e.getStudent().getId(), (dest, v) -> dest.getStudent().setId((String) v));
        typeMap1.addMapping(e -> e.getStudent().getNames(), (dest, v) -> dest.getStudent().setNamesStudent((String) v));
        typeMap1.addMapping(e -> e.getStudent().getSurnames(), (dest, v) -> dest.getStudent().setSurnamesStudent((String) v));
        typeMap1.addMapping(e -> e.getStudent().getDni(), (dest, v) -> dest.getStudent().setDniStudent((String) v));

        //Escritura
        TypeMap<EnrollmentDTO, Enrollment> typeMap2 = mapper.createTypeMap(EnrollmentDTO.class, Enrollment.class);
        typeMap2.addMapping(e -> e.getStudent().getId(), (dest, v) -> dest.getStudent().setId((String) v));
        typeMap2.addMapping(e -> e.getStudent().getNamesStudent(), (dest, v) -> dest.getStudent().setNames((String) v));
        typeMap2.addMapping(e -> e.getStudent().getSurnamesStudent(), (dest, v) -> dest.getStudent().setSurnames((String) v));
        typeMap2.addMapping(e -> e.getStudent().getDniStudent(), (dest, v) -> dest.getStudent().setDni((String) v));
        
		return mapper;
	}

}
