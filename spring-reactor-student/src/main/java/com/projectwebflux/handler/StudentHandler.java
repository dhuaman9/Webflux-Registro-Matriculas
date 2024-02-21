package com.projectwebflux.handler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.projectwebflux.dto.StudentDTO;
import com.projectwebflux.model.Student;
import com.projectwebflux.services.IStudentService;
import com.projectwebflux.validation.RequestValidator;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

import java.net.URI;
import static org.springframework.web.reactive.function.BodyInserters.fromValue;

@Component
@RequiredArgsConstructor
public class StudentHandler {

	private final IStudentService service;
	
	@Qualifier("defaultMapper")
    private final ModelMapper mapper;
	
	//private final Validator validator;
    private final RequestValidator requestValidator;
	
	public Mono<ServerResponse> findAll(ServerRequest req) {
	        return ServerResponse
	                .ok()
	                .contentType(MediaType.APPLICATION_JSON)
	                .body(service.findAll().map(this::convertToDto), StudentDTO.class);
	}
	
	
	public Mono<ServerResponse> findAllByOrderByAgeDesc(ServerRequest req) {
        return ServerResponse
                .ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(service.findAllByOrderByAgeDesc().map(this::convertToDto), StudentDTO.class);
	}
	
	public Mono<ServerResponse> findAllByOrderByAgeAsc(ServerRequest req) {
        return ServerResponse
                .ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(service.findAllByOrderByAgeAsc().map(this::convertToDto), StudentDTO.class);
	}
	
	public Mono<ServerResponse> findById(ServerRequest req) {
	        String id = req.pathVariable("id");

	        return service.findById(id)
	                .map(this::convertToDto)
	                .flatMap(e -> ServerResponse
	                        .ok()
	                        .contentType(MediaType.APPLICATION_JSON)
	                        .body(fromValue(e)) //se inserta de forma estatica el BodyInserters, para usar solo el fromValue
	                )
	                .switchIfEmpty(ServerResponse.notFound().build());
	}

	public Mono<ServerResponse> create(ServerRequest req) {
	      Mono<StudentDTO> monoStudentDTO = req.bodyToMono(StudentDTO.class);

	      return monoStudentDTO
	                .flatMap(requestValidator::validate)   //requestValidator::validate  = e -> requestValidator.validate(e)
	                .flatMap(e -> service.save(this.convertToDocument(e)))
	                .map(this::convertToDto)
	                .flatMap(e -> ServerResponse
	                        .created(URI.create(req.uri().toString().concat("/").concat(e.getId())))
	                        .contentType(MediaType.APPLICATION_JSON)
	                        .body(fromValue(e))
	                );
	}
	
	
	public Mono<ServerResponse> update(ServerRequest req) {
	     String id = req.pathVariable("id");

	     return req.bodyToMono(StudentDTO.class)
	                .map(e -> {
	                    e.setId(id);
	                    return e;
	                })
	                .flatMap(requestValidator::validate)
	                .flatMap(e -> service.update(id, convertToDocument(e)))
	                .map(this::convertToDto)
	                .flatMap(e -> ServerResponse
	                        .ok()
	                        .contentType(MediaType.APPLICATION_JSON)
	                        .body(fromValue(e))
	                )
	                .switchIfEmpty(ServerResponse.notFound().build());
	}

	public Mono<ServerResponse> delete(ServerRequest req) {
	     String id = req.pathVariable("id");

	     return service.delete(id)
	                .flatMap(result -> {
	                    if(result){
	                        return ServerResponse.noContent().build();
	                    }else{
	                        return ServerResponse.notFound().build();
	                    }
	                });
	}
	    
	
	private StudentDTO convertToDto(Student model) {
        return mapper.map(model, StudentDTO.class);
    }

    private Student convertToDocument(StudentDTO dto) {
        return mapper.map(dto, Student.class);
    }
	
}
