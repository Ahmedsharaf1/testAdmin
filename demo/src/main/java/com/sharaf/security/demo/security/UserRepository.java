package com.sharaf.security.demo.security;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<AppUser, String>  {

	AppUser findByEmail(String email);
	AppUser findByUsername(String username);
}
