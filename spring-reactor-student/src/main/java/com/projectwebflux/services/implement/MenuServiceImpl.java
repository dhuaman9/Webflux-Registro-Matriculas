package com.projectwebflux.services.implement;

import com.projectwebflux.model.Menu;
import com.projectwebflux.repository.IGenericRepository;
import com.projectwebflux.repository.IMenuRepository;
import com.projectwebflux.services.IMenuService;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;


@Service
@RequiredArgsConstructor
public class MenuServiceImpl extends CRUDImpl<Menu, String> implements IMenuService {

	private final IMenuRepository repo;

	@Override
	protected IGenericRepository<Menu, String> getRepository() {
		return repo;
	}
	
	
	@Override
	public Flux<Menu> getMenus(String[] roles) {
		return repo.getMenus(roles);
	}

	
}
