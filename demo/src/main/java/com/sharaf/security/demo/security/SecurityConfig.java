package com.sharaf.security.demo.security;

import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import com.sharaf.security.demo.security.AuthFilter;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter{

	private final String [] PUBLIC_ENDPOINTS = {
			"/auth/**"
	};

	@Bean
	@Override
	protected AuthenticationManager authenticationManager() throws Exception {
		return super.authenticationManager();
	}
	@Bean
	public AuthFilter authFilter() {
		return new AuthFilter();
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
		.cors().and().csrf().disable()
		.sessionManagement()
		     .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
		     .and()
		.authorizeRequests()
		     .antMatchers(PUBLIC_ENDPOINTS).permitAll()
		     .antMatchers("/v1/roles/**").hasRole("ADMIN")
		     .anyRequest().authenticated()
//		     .and()
//             .logout().logoutUrl("/api/v1/auth/logout").logoutSuccessUrl("/api/v1/auth").invalidateHttpSession(true)
             .and()
		.addFilterBefore(authFilter(), UsernamePasswordAuthenticationFilter.class);
	}
	
	
}
