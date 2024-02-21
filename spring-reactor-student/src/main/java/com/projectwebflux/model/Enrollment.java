package com.projectwebflux.model;


import java.time.LocalDateTime;
import java.util.Set;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Document(collection = "enrollment")
public class Enrollment {

	@Id
	@EqualsAndHashCode.Include
	private String id;

	@Field
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private LocalDateTime dateEnrollment;

	@Field
	private Student student;

	@Field
	private Set<Course> courses;
	
	@Field
    private boolean state;
	
}
