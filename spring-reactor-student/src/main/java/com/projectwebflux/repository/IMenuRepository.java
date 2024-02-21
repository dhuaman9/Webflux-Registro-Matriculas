package com.projectwebflux.repository;


import org.springframework.data.mongodb.repository.Query;

import com.projectwebflux.model.Menu;

import reactor.core.publisher.Flux;

public interface IMenuRepository extends IGenericRepository<Menu, String> {

    @Query("{'roles' :  { $in:  ?0 }}")
    Flux<Menu> getMenus(String[] roles);
}
