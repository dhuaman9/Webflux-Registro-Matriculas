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

import com.projectwebflux.dto.StudentDTO;
import com.projectwebflux.model.Student;
import com.projectwebflux.pagination.PageSupport;
import com.projectwebflux.services.IStudentService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/students")
@RequiredArgsConstructor
public class StudentController {

	private final IStudentService service;
	
	@Qualifier("defaultMapper")
	private final ModelMapper mapper;
	
	
    @GetMapping
    public Mono<ResponseEntity<Flux<StudentDTO>>> findAll() {
//        Flux<Student> fx = service.findAll();
//        return Mono.just(ResponseEntity.ok()
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .body(fx)
//                )
//                .defaultIfEmpty(ResponseEntity.notFound().build());
    	
    	Flux<StudentDTO> fx = service.findAll().map(this::convertToDto); //e -> convertToDto(e)

        return Mono.just(ResponseEntity.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(fx)
                )
                .defaultIfEmpty(ResponseEntity.notFound().build());
    	
    	
    }
    
    @GetMapping("/listarPorEdadDesc")
    public Mono<ResponseEntity<Flux<StudentDTO>>> findAllByOrderByAgeDesc() {
//        Flux<Student> fx = service.findAllByOrderByAgeDesc();
//
//        return Mono.just(ResponseEntity.ok()
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .body(fx)
//                )
//                .defaultIfEmpty(ResponseEntity.notFound().build());
    	
    	 Flux<StudentDTO> fx = service.findAllByOrderByAgeDesc().map(this::convertToDto); //e -> convertToDto(e)

         return Mono.just(ResponseEntity.ok()
                         .contentType(MediaType.APPLICATION_JSON)
                         .body(fx)
                 )
                 .defaultIfEmpty(ResponseEntity.notFound().build());
    }
    
    @GetMapping("/listarPorEdadAsc")
    public Mono<ResponseEntity<Flux<StudentDTO>>> findAllByOrderByAgeAsc() {
//        Flux<Student> fx = service.findAllByOrderByAgeAsc();
//
//        return Mono.just(ResponseEntity.ok()
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .body(fx)
//                )
//                .defaultIfEmpty(ResponseEntity.notFound().build());
    	
    	 Flux<StudentDTO> fx = service.findAllByOrderByAgeAsc().map(this::convertToDto); //e -> convertToDto(e)

         return Mono.just(ResponseEntity.ok()
                         .contentType(MediaType.APPLICATION_JSON)
                         .body(fx)
                 )
                 .defaultIfEmpty(ResponseEntity.notFound().build());
    }
    
    @GetMapping("/{id}")
    public Mono<ResponseEntity<StudentDTO>> findById(@PathVariable("id") String id) {
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
    public Mono<ResponseEntity<StudentDTO>> save(@Valid @RequestBody StudentDTO dto, final ServerHttpRequest req) {
    
//    	        return service.save(student)
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
    public Mono<ResponseEntity<StudentDTO>> update(@Valid @PathVariable("id") String id, @RequestBody StudentDTO dto) {
//        return Mono.just(student)
//                .map(e -> {
//                    e.setId(id);
//                    return e;
//                })
//                .flatMap( e -> service.update(id, student))
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
    public Mono<ResponseEntity<PageSupport<StudentDTO>>> getPage(
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
    
    private StudentDTO convertToDto(Student model){
        return mapper.map(model, StudentDTO.class);
    }

    private Student convertToDocument(StudentDTO dto){
        return mapper.map(dto, Student.class);
    }
	
}
