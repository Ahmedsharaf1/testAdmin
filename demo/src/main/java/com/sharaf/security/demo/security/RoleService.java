package com.sharaf.security.demo.security;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sharaf.security.demo.error.NotFoundException;
import com.sharaf.security.demo.error.ConflictException;

@Service
public class RoleService{

	@Autowired
	private RoleRepository roleRepository;
	
	public List<UserRole> findAll(){
		return (List<UserRole>) roleRepository.findAll();
	}
	
	public UserRole getById(int id) {
		try {
		return roleRepository.findById(id).get();
		}
		catch(NoSuchElementException ex) {
			throw new NotFoundException(String.format("No Record With Id [%s]"
					+ " was found in our database",id));
		}
		
	}
	
	public UserRole save(UserRole userRole) {
		if(roleRepository.findByRole(userRole.getRole()) != null ) {
			throw new ConflictException("Anather row with the same data is exists");

		}
		return roleRepository.save(userRole);
	}
	public void delete(int id) {
		roleRepository.deleteById(id);
	}


}
