package com.projectwebflux.services.implement;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.projectwebflux.model.User;
import com.projectwebflux.repository.IGenericRepository;
import com.projectwebflux.repository.IRoleRepository;
import com.projectwebflux.repository.IUserRepository;
import com.projectwebflux.services.IUserService;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class UserServiceImpl extends CRUDImpl<User, String> implements IUserService {

	private final IUserRepository repository;
    private final IRoleRepository rolRepository;
    private final BCryptPasswordEncoder bcrypt;
	 
	@Override
	protected IGenericRepository<User, String> getRepository() {
		return repository;
	}
	
	@Override
    public Mono<User> saveHash(User user) {
        user.setPassword(bcrypt.encode(user.getPassword()));
        return repository.save(user);

    }

    @Override
    public Mono<com.projectwebflux.security.User> searchByUser(String username) {
        Mono<User> monoUser = repository.findOneByUsername(username);
        List<String> roles = new ArrayList<>();

        return monoUser.flatMap(u -> {
            return Flux.fromIterable(u.getRoles())
                    .flatMap(rol -> {
                        return rolRepository.findById(rol.getId())
                                .map(r -> {
                                    roles.add(r.getName());
                                    return r;
                                });
                    }).collectList().flatMap(list -> {
                        u.setRoles(list);
                        return Mono.just(u);
                    });
        }).flatMap(u -> Mono.just(new com.projectwebflux.security.User(u.getUsername(), u.getPassword(), u.isStatus(), roles)));
   
    	
    }
	

}
