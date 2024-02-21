package com.projectwebflux.security;

import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.web.server.context.ServerSecurityContextRepository;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextImpl;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

//Clase S6
@Component
@RequiredArgsConstructor
public class SecurityContextRepository  implements ServerSecurityContextRepository  { // implements ServerSecurityContextRepository 

  private final AuthenticationManager authenticationManager;

  @Override
  public Mono<Void> save(ServerWebExchange exchange, SecurityContext context) {
      return null;
  }

  @Override
  public Mono<SecurityContext> load(ServerWebExchange exchange) {
      ServerHttpRequest request = exchange.getRequest();
      String authHeader = request.getHeaders().getFirst(HttpHeaders.AUTHORIZATION);

      if(authHeader != null){
          if(authHeader.startsWith("Bearer ") || authHeader.startsWith("bearer ")){
              final int TOKEN_POSITION = 1;
              String token = authHeader.split(" ")[TOKEN_POSITION];
              Authentication auth = new UsernamePasswordAuthenticationToken(token, token);

              return this.authenticationManager.authenticate(auth).map(SecurityContextImpl::new);
          }else{
              return Mono.error(new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Not authorized"));
          }
      }else{
          return Mono.error(new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Not authorized"));
      }
  }


}

