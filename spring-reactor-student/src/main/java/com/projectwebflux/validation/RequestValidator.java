package com.projectwebflux.validation;

import java.util.Set;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;
import org.springframework.web.server.ResponseStatusException;

@Component
@RequiredArgsConstructor
public class RequestValidator {

	private final Validator validator;

    public <T> Mono<T> validate(T t) {
        if (t == null) {
            return Mono.error(new ResponseStatusException(HttpStatus.BAD_REQUEST));
        }

        Set<ConstraintViolation<T>> constraints = validator.validate(t);

        if (constraints == null || constraints.isEmpty()) {
            return Mono.just(t);
        }

        return Mono.error(new ResponseStatusException(HttpStatus.BAD_REQUEST));

    }
	
}
