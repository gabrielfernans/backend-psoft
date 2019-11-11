package com.psoft.project.services;

import java.sql.Date;
import java.util.UUID;

import javax.servlet.ServletException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.psoft.project.entities.User;
import com.psoft.project.entities.VerificationToken;
import com.psoft.project.repositories.UserRepository;
import com.psoft.project.repositories.VerificationTokenRepository;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;


@Service
public class UserService {
	
	private UserRepository<User, String> users;
	private VerificationTokenRepository<VerificationToken, String> tokens;
	@Autowired
	private JavaMailSender emailSender;
	
	public UserService(UserRepository<User, String> users, VerificationTokenRepository<VerificationToken, String> tokens) {
		super();
		this.users = users;
		this.tokens = tokens;
	}
	
	public User setUser(User user){
		users.save(user);
		this.sendEmail(user.getEmail());
		return user;
	}
	
	public User getUser(String email) {
		return users.findByEmail(email);
	}
	
	private void createVerificationToken(User user, String token) {
		VerificationToken newUserToken = new VerificationToken(user, token);
		tokens.save(newUserToken);
	}
	
	public void forgotPassword(String email) throws ServletException {
		User user = users.findByEmail(email);
		if(user == null) {
			System.out.println(email);
			throw new ServletException("Usuario invalido");
		}
		String token =Jwts.builder().setSubject(UUID.randomUUID().toString()).signWith(SignatureAlgorithm.HS512, "valid token")
				.setExpiration(new Date(System.currentTimeMillis() + 1 * 60 * 10000)).compact();
		this.createVerificationToken(user, token);
		sendEmailVerificationToken(email, token);
	}
	
	private void sendEmailVerificationToken(String email, String token) {
		SimpleMailMessage message = new SimpleMailMessage();
		String url = "/views/new-password.html?token=" + token;
		String m = "Para redefinir a senha acesse o link abaixo ";
		message.setText(m + "http://localhost:5500" + url);
		message.setTo(email);
		emailSender.send(message);	
	}

	private void sendEmail(String email) {
		SimpleMailMessage message = new SimpleMailMessage();
		message.setText("Seja bem-vindo ao AJUDE! <LINK> para primeiro acesso");
		message.setTo(email);
		emailSender.send(message);	
	}
	
	//redefinir senha esquecida
	public User setPassword(String token, String newPassword) throws ServletException {
		try {
			Jwts.parser().setSigningKey("valid token").parseClaimsJws(token).getBody().getSubject();
		} catch (SignatureException e) {
			throw new ServletException("Token invalido ou expirado!");
		}
		
		VerificationToken vToken = tokens.findByToken(token);
		if(vToken == null) return null;
		String email = vToken.getUser().getEmail();
		users.findByEmail(email).setPassword(newPassword);
		users.save(users.findByEmail(email));
		tokens.deleteById(token);
		return users.getOne(email);
	}
	
	//apenas modificar a senha
	public User modifyPassword(String email, String newPassword) {
		User u = users.getOne(email);
		u.setPassword(newPassword);
		users.save(users.findByEmail(email));
		return users.getOne(email);
		
	}

	
	
	
	
	
}
