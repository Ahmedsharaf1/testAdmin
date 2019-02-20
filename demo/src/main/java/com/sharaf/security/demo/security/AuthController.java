package com.sharaf.security.demo.security;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sharaf.security.demo.BaseController;

@RestController
@RequestMapping("/auth")
public class AuthController extends BaseController{
	private final  Log logger = LogFactory.getLog(AuthController.class);
	@Autowired
	private UserService userService;
	@Autowired
	private TokenUtil tokenUtil;
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@PostMapping(value = {"","/"})
	public JwtResponse signIn(@RequestBody SignRequest signRequest) {
		logger.info("****************************************************"+signRequest.getUserName()+"*********"+signRequest.getPassword());
		final Authentication authentication= authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(signRequest.getUserName(), signRequest.getPassword()));
	logger.info("*****"+authentication  );
	SecurityContextHolder.getContext().setAuthentication(authentication);
	logger.info("usernam*****************************e"+signRequest.getUserName());
	logger.warn("username"+signRequest.getUserName());
	UserDetails userDetails= userService.loadUserByUsername(signRequest.getUserName());
	
	String token = tokenUtil.generatedToken(userDetails);
	JwtResponse response = new JwtResponse(token);
	return response;
	}
	
	@PostMapping(value = "/regestration")
	public ResponseEntity<Boolean> singUp(@Valid @RequestBody AppUser appuser){
		userService.save(appuser);
		return new ResponseEntity<>(true,HttpStatus.CREATED);	
	}
	
	@GetMapping(value = "/logout")
	public Boolean getLogout(HttpServletRequest request, HttpServletResponse response) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth != null){
		new SecurityContextLogoutHandler().logout(request, response, auth);
		return false;
		}
		return true;
	}
	
	@GetMapping(value = "/users")
	public ResponseEntity<List<AppUser>> getAllUser(){
		System.out.println(getCurrentUserAuth().getAuthorities());
		if(getCurrentUserAuth().equals("ADMIN")) {
			List<AppUser> result=userService.findAll();
			return new ResponseEntity<>(result,HttpStatus.OK);
		}
		return null;
	}

}
