package com.projectwebflux.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.projectwebflux.handler.CourseHandler;
import com.projectwebflux.handler.EnrollmentHandler;
import com.projectwebflux.handler.StudentHandler;

import static org.springframework.web.reactive.function.server.RequestPredicates.*;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class RouterConfig {

	// Functional Endpoints
	@Bean
	public RouterFunction<ServerResponse> studentRoutes(StudentHandler handler) {
		return route(GET("/v2/students"), handler::findAll)// req -> handler.findAll(req)
				.andRoute(GET("/v2/students/listarPorEdadDesc"), handler::findAllByOrderByAgeDesc)
				.andRoute(GET("/v2/students/listarPorEdadAsc"), handler::findAllByOrderByAgeAsc)
				.andRoute(GET("/v2/students/{id}"), handler::findById)
				.andRoute(POST("/v2/students"), handler::create)
				.andRoute(PUT("/v2/students/{id}"), handler::update)
				.andRoute(DELETE("/v2/students/{id}"), handler::delete);
	}

	
	@Bean
	public RouterFunction<ServerResponse> courseRoutes(CourseHandler handler) {
		return route(GET("/v2/courses"), handler::findAll)// req -> handler.findAll(req)
				.andRoute(GET("/v2/courses/{id}"), handler::findById)
				.andRoute(POST("/v2/courses"), handler::create)
				.andRoute(PUT("/v2/courses/{id}"), handler::update)
				.andRoute(DELETE("/v2/courses/{id}"), handler::delete);
	}

	@Bean
	public RouterFunction<ServerResponse> enrollmentRoutes(EnrollmentHandler handler) {
		return route(GET("/v2/enrollments"), handler::findAll)// req -> handler.findAll(req)
				.andRoute(GET("/v2/enrollments/{id}"), handler::findById)
				.andRoute(POST("/v2/enrollments"), handler::create)
				.andRoute(PUT("/v2/enrollments/{id}"), handler::update)
				.andRoute(DELETE("/v2/enrollments/{id}"), handler::delete);
	}

}
