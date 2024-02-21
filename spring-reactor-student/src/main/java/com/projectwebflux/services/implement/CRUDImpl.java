package com.projectwebflux.services.implement;

import org.springframework.data.domain.Pageable;

import com.projectwebflux.pagination.PageSupport;
import com.projectwebflux.repository.IGenericRepository;
import com.projectwebflux.services.ICRUD;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public abstract class CRUDImpl<T, ID> implements ICRUD<T, ID> {

	protected abstract IGenericRepository<T, ID> getRepository();

	@Override
	public Mono<T> save(T t) {
		return getRepository().save(t);
	}

	@Override
	public Mono<T> update(ID id, T t) {
		return getRepository().findById(id).flatMap(e -> getRepository().save(t));
	}

	@Override
	public Flux<T> findAll() {
		return getRepository().findAll();
	}

	@Override
	public Mono<T> findById(ID id) {
		return getRepository().findById(id);
	}

	@Override
	public Mono<Boolean> delete(ID id) {
		return getRepository().findById(id).hasElement().flatMap(result -> {
			if (result) {
				return getRepository().deleteById(id).thenReturn(true);
			} else {
				return Mono.just(false);
			}
		});
	}
	
	 @Override
	    public Mono<PageSupport<T>> getPage(Pageable pageable) {
	        return getRepository().findAll()  //es un Flux<T>
	                .collectList()
	                .map(list -> new PageSupport<>(
	                    list.stream()
	                            .skip(pageable.getPageNumber() * pageable.getPageSize())
	                            .limit(pageable.getPageSize()).toList()
	                        ,
	                    pageable.getPageNumber(),
	                    pageable.getPageSize(),
	                    list.size()
	                ));
	    }
	
	
}
