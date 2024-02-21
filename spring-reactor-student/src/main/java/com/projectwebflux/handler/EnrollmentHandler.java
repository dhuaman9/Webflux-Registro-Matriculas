package com.projectwebflux.handler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.projectwebflux.dto.EnrollmentDTO;
import com.projectwebflux.model.Enrollment;
import com.projectwebflux.services.IEnrollmentService;
import com.projectwebflux.validation.RequestValidator;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

import java.net.URI;
import static org.springframework.web.reactive.function.BodyInserters.fromValue;

@Component
@RequiredArgsConstructor
public class EnrollmentHandler {

	private final IEnrollmentService service;
	
	@Qualifier("defaultMapper")
    private final ModelMapper mapper;
	
	//private final Validator validator;
    private final RequestValidator requestValidator;
	
	public Mono<ServerResponse> findAll(ServerRequest req) {
	        return ServerResponse
	                .ok()
	                .contentType(MediaType.APPLICATION_JSON)
	                .body(service.findAll().map(this::convertToDto), EnrollmentDTO.class);
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
	        Mono<EnrollmentDTO> monoEnrollmentDTO = req.bodyToMono(EnrollmentDTO.class);

	        return monoEnrollmentDTO
	                .flatMap(requestValidator::validate)
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

	        return req.bodyToMono(EnrollmentDTO.class)
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
	    
	
	private EnrollmentDTO convertToDto(Enrollment model) {
        return mapper.map(model, EnrollmentDTO.class);
    }

    private Enrollment convertToDocument(EnrollmentDTO dto) {
        return mapper.map(dto, Enrollment.class);
    }
	
}
