package com.projectwebflux.security;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import com.fasterxml.jackson.annotation.JsonIgnore;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

//Clase S1
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User implements UserDetails  {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
//
private String username;

	@JsonIgnore
	private String password;
	private Boolean enabled;
	private List<String> roles;

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return this.roles.stream().map(SimpleGrantedAuthority::new).toList();
	}

	@Override
	public boolean isAccountNonExpired() {
		return false;
	}

	@Override
	public boolean isAccountNonLocked() {
		return false;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return false;
	}

	@Override
	public boolean isEnabled() {
		return this.enabled;
	}

}
