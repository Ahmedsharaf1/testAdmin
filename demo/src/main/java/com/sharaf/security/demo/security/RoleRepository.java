package com.sharaf.security.demo.security;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface RoleRepository extends JpaRepository<UserRole, Integer> {
	
	UserRole findByRole(String role);
	
}
