package com.sharaf.security.demo.security;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.sharaf.security.demo.error.ConflictException;
import com.sharaf.security.demo.error.NotFoundException;


@Service
public class UserService implements UserDetailsService {

	@Autowired
	private UserRepository userRepository;
	
	@Bean 
	private PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		//return new User("password",passwordEncoder().encode("password"),AuthorityUtils.NO_AUTHORITIES);
		AppUser user = userRepository.findByUsername(username);
		if(user == null) {
			throw new NotFoundException("User not found you must sign");
		}
		return  (UserDetails) user;
	}
	public void save(AppUser user) {
		user.setPassword(passwordEncoder().encode(user.getPassword()));
		if(userRepository.findByEmail(user.getEmail()) != null) {
			throw new ConflictException("Anather row with the same data is exists");
		}
		userRepository.save(user);
	}
	public List<AppUser> findAll(){
		return  (List<AppUser>) userRepository.findAll();
	}

}
