package com.psoft.project.services;

import javax.servlet.ServletException;

import org.springframework.stereotype.Service;

import com.psoft.project.entities.User;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureException;

@Service
public class JWTService {
	private UserService uservice;

	public JWTService(UserService uservice) {
		super();
		this.uservice = uservice;
	}
	
	public User userExist(String authorizationHeader) throws ServletException {
		String usuario = getSujeitoDoToken(authorizationHeader);
		return uservice.getUser(usuario);
	}
	
	public boolean userHasPermission(String authorizationHeader, String email) throws ServletException {
		String sujeito = getSujeitoDoToken(authorizationHeader);
		User user = uservice.getUser(sujeito);
		
		return user != null && user.getEmail().equals(email);
	}
	
	//authorizationHeader contem o token do usuario passado no http, retorna o email dele
	private String getSujeitoDoToken(String authorizationHeader) throws ServletException {
		if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
			throw new ServletException("Token inexistente ou mal formatado!");
		}

		// Extraindo apenas o token do cabecalho.
		String token = authorizationHeader.substring(7);

		String subject = null;
		try {
			subject = Jwts.parser().setSigningKey(com.psoft.project.controllers.LoginController.TOKEN_KEY).parseClaimsJws(token).getBody().getSubject();
		} catch (SignatureException e) {
			throw new ServletException("Token invalido ou expirado!");
		}
		return subject;
	}
	
	
}
