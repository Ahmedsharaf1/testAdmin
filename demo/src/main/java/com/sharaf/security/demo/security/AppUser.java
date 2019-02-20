package com.sharaf.security.demo.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Entity
@Table( name = "users")
public class AppUser implements UserDetails {


/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	//	private final String ROLE_PREFIX = "ROLE_";
	@Id
	@Column(name = "username",unique = true, nullable = false, length = 45)
	@NotNull(message="username is required")
	@Size(min= 8 , message = "username must be at least 8 character")
	private String username;
	
	@Column(name = "password", nullable = false)
	@NotNull(message="password is required")
	@Size(min= 8 , message = "password must be at least 8 character")
	private String password;
	@Column(name = "email",unique = true, nullable = false)
	@Pattern(regexp = ".+@.+\\.[a-z]+",message = "Invalid email address ")
	private String email;
	@Column(name = "enabled", nullable = false)
	private boolean enabled;
	@ManyToMany(fetch = FetchType.EAGER,cascade= {CascadeType.DETACH,CascadeType.MERGE,CascadeType.PERSIST,
			CascadeType.REFRESH})
	@JoinTable(name = "users_roles",joinColumns = @JoinColumn(name= "user_id"),
	inverseJoinColumns =@JoinColumn(name = "roles_id"))
	private List<UserRole> userRole;
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
    
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public boolean isEnabled() {
		return enabled;
	}
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
	public List<UserRole> getUserRole() {
		return userRole;
	}
	public void setUserRole(List<UserRole> userRole) {
		this.userRole = userRole;
	}
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		
		List<GrantedAuthority> authorities = new ArrayList<>();
		for(UserRole role : userRole ) {
			authorities.add(new SimpleGrantedAuthority(role.getRole()));
		}
		return authorities;
	}
	@Override
	public boolean isAccountNonExpired() {
		
		return true;
	}
	@Override
	public boolean isAccountNonLocked() {
		
		return true;
	}
	@Override
	public boolean isCredentialsNonExpired() {
		
		return true;
	}
	

}
