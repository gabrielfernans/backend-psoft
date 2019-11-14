package com.psoft.project.controllers;

import javax.servlet.ServletException;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.psoft.project.entities.User;
import com.psoft.project.services.JWTService;
import com.psoft.project.services.UserService;

@RequestMapping("/users")
@RestController
public class UsersController {
	
	@Autowired
	private UserService userService;
	@Autowired
	private JWTService jwtservice;
	
	//cria usuario, mas antes checa se o email ja existe. Caso não exista o usuario é criado.
	@PostMapping()
	public ResponseEntity<User> setUser(@RequestBody @Valid User user) {
		User tempUser = userService.getUser(user.getEmail());
		if(tempUser == null)
			return new ResponseEntity<User>(userService.setUser(user), HttpStatus.OK);
		else
			return new ResponseEntity<User>(HttpStatus.CONFLICT);
	}
	
	//Procura por um usuário no repositório a partir de um email fornecido.
	//retorna 404 se não encontrar, retorna o usuário e 200 se encontrar.
	@GetMapping()
	public ResponseEntity<User> getUser(@RequestBody @Valid String email) {
		User tempUser = userService.getUser(email);
		if(tempUser == null)
			return new ResponseEntity<User>(HttpStatus.NOT_FOUND);
		else
			return new ResponseEntity<User>(tempUser, HttpStatus.OK);
	}
	
	@PostMapping("/password")
	public ResponseEntity<User> forgotPassword(@RequestBody String email) throws ServletException {
		try{
			userService.forgotPassword(email);
		}catch(ServletException s) {
			return new ResponseEntity<User>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<User>(HttpStatus.OK);
	}
	
	@PutMapping("/password")
	public ResponseEntity<User> newPassword(@RequestParam("token") String token, @RequestBody String newPassword) throws ServletException {
		try{
			User u = userService.setPassword(token, newPassword);
			if(u == null) return new ResponseEntity<User>(HttpStatus.NOT_FOUND);
			else return new ResponseEntity<User>(u, HttpStatus.OK); 
		}catch(ServletException s) {
			//usuario esta com codigo invalido ou vencido
			return new ResponseEntity<User>(HttpStatus.FORBIDDEN);
		}
	}
	
	@PutMapping("{email}/password")
	public ResponseEntity<User> modifyPassword(@RequestHeader("Authorization") String header, @RequestBody String newPassword) throws ServletException {
		if(jwtservice.userExist(header) == null) {
			return new ResponseEntity<User>(HttpStatus.NOT_FOUND);
		}try {
			User user = jwtservice.userExist(header); 
			if(jwtservice.userHasPermission(header, user.getEmail())) {
				return new ResponseEntity<User>(userService.modifyPassword(user.getEmail(), newPassword), HttpStatus.OK);
			}
		}catch(ServletException s){
			//usuario esta com codigo invalido ou vencido
			return new ResponseEntity<User>(HttpStatus.FORBIDDEN);
		}//usuario nao tem permissao
		return new ResponseEntity<User>(HttpStatus.UNAUTHORIZED);
	}
	

}
