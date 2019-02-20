package com.sharaf.security.demo.security;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sharaf.security.demo.BaseController;

@RestController
@RequestMapping("/v1/roles")
public class RoleController extends BaseController {
	
	@Autowired
	private RoleService roleService;
	@GetMapping(value = {"","/"})
	public  ResponseEntity<List<UserRole>> getAllRole() {
	//	List<UserRole> result =  roleService.findByUser(getCurrentUser().getId());
		List<UserRole> result =  roleService.findAll();
		return new ResponseEntity<>(result , HttpStatus.OK);

	}
	@GetMapping("{id}")
	public  ResponseEntity<UserRole> getById(@PathVariable int id) {
		UserRole result = roleService.getById(id);
		return new ResponseEntity<>(result,HttpStatus.OK);
	}
	
	@PostMapping(value = {"","/"})
	public ResponseEntity<UserRole> createTodo( @Valid @RequestBody UserRole userRole) {
	//	userRole.setUserId(getCurrentUser().getId());
		UserRole result=roleService.save(userRole);
		return new ResponseEntity<>(result,HttpStatus.CREATED);
	}
	@PutMapping(value = {"","/"})
	public ResponseEntity<UserRole> updateTodo( @RequestBody UserRole userRole) {
	//	userRole.setUserId(getCurrentUser().getId());
		UserRole result=roleService.save(userRole);
		return new ResponseEntity<>(result,HttpStatus.CREATED);
	} 
	
	@DeleteMapping("{id}")
	public ResponseEntity<Void> deleteTodo(@PathVariable int id) {
		roleService.delete(id);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
	
}
