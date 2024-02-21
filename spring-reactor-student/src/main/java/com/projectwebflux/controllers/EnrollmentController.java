package com.projectwebflux.controllers;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Flux;

import java.net.URI;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.bind.annotation.*;

import com.projectwebflux.dto.EnrollmentDTO;
import com.projectwebflux.model.Enrollment;
import com.projectwebflux.pagination.PageSupport;
import com.projectwebflux.services.IEnrollmentService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/enrollments")
@RequiredArgsConstructor
public class EnrollmentController {

	private final IEnrollmentService service;
	@Qualifier("enrollmentMapper")
    private final ModelMapper mapper;
	
    @GetMapping
    public Mono<ResponseEntity<Flux<EnrollmentDTO>>> findAll() {
//        Flux<Enrollment> fx = service.findAll();

//        return Mono.just(ResponseEntity.ok()
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .body(fx)
//                )
//                .defaultIfEmpty(ResponseEntity.notFound().build());
        
        Flux<EnrollmentDTO> fx = service.findAll().map(this::convertToDto); //e -> convertToDto(e)

        return Mono.just(ResponseEntity.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(fx)
                )
                .defaultIfEmpty(ResponseEntity.notFound().build());
        
    }
    
    @GetMapping("/{id}")
    public Mono<ResponseEntity<EnrollmentDTO>> findById(@PathVariable("id") String id) {
//        return service.findById(id)
//                .map(e -> ResponseEntity.ok()
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .body(e)
//                )
//                .defaultIfEmpty(ResponseEntity.notFound().build());
    	
    	
    	return service.findById(id)
                .map(this::convertToDto)
                .map(e -> ResponseEntity.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(e)
                )
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }
    
    @PostMapping
    public Mono<ResponseEntity<EnrollmentDTO>> save(@Valid @RequestBody EnrollmentDTO dto,  final ServerHttpRequest req) {
    
//    	        return service.save(enrollment)
//                .map(e -> ResponseEntity.created(
//                                        URI.create(req.getURI().toString().concat("/").concat(e.getId()))
//                                )
//                                .contentType(MediaType.APPLICATION_JSON)
//                                .body(e)
//                )
//                .defaultIfEmpty(ResponseEntity.notFound().build());
    	 
    	return service.save(convertToDocument(dto))
                 .map(this::convertToDto)
                 .map(e -> ResponseEntity.created(
                                         URI.create(req.getURI().toString().concat("/").concat(e.getId()))
                                 )
                                 .contentType(MediaType.APPLICATION_JSON)
                                 .body(e)
                 )
                 .defaultIfEmpty(ResponseEntity.notFound().build());
    	
    }
	
  
	
    @PutMapping("/{id}")
    public Mono<ResponseEntity<EnrollmentDTO>> update(@Valid @PathVariable("id") String id, @RequestBody EnrollmentDTO dto) {
//        return Mono.just(enrollment)
//                .map(e -> {
//                    e.setId(id);
//                    return e;
//                })
//                .flatMap( e -> service.update(id, enrollment))
//                .map(e -> ResponseEntity
//                        .ok()
//                        .body(e)
//                )
//                .defaultIfEmpty(ResponseEntity.notFound().build());
    	
    	 return Mono.just(dto)
                 .map(e -> {
                     e.setId(id);
                     return e;
                 })
                 .flatMap( e -> service.update(id, convertToDocument(dto)))
                 .map(this::convertToDto)
                 .map(e -> ResponseEntity
                         .ok()
                         .body(e)
                 )
                 .defaultIfEmpty(ResponseEntity.notFound().build());
    }
    
    @DeleteMapping("/{id}")
    public Mono<ResponseEntity<Object>> delete(@PathVariable("id") String id) {
        return service.delete(id)
                .flatMap(result -> {
                    if(result){
                        return Mono.just(ResponseEntity.noContent().build());
                    }else{
                        return Mono.just(ResponseEntity.notFound().build());
                    }
                })
                .defaultIfEmpty(ResponseEntity.notFound().build());
    	
    }
    
    @GetMapping("/pageable")
    public Mono<ResponseEntity<PageSupport<EnrollmentDTO>>> getPage(
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "2") int size
    ){
        return service.getPage(PageRequest.of(page, size))
                .map(pageSupport -> new PageSupport<>(
                            pageSupport.getContent().stream().map(this::convertToDto).toList(),
                            pageSupport.getPageNumber(),
                            pageSupport.getPageSize(),
                            pageSupport.getTotalElements()
                            )
                )
                .map(e -> ResponseEntity.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(e)
                )
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }
    
    private EnrollmentDTO convertToDto(Enrollment model){
        return mapper.map(model, EnrollmentDTO.class);
    }

    private Enrollment convertToDocument(EnrollmentDTO dto){
        return mapper.map(dto, Enrollment.class);
    }
    
	
}
