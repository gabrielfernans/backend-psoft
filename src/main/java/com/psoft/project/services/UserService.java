package com.psoft.project.services;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.psoft.project.entities.User;
import com.psoft.project.entities.VerificationToken;
import com.psoft.project.repositories.UserRepository;
import com.psoft.project.repositories.VerificationTokenRepository;


@Service
public class UserService {
	
	private UserRepository<User, String> users;
	private VerificationTokenRepository<User, String> tokens;
	@Autowired
	private JavaMailSender emailSender;
	
	public UserService(UserRepository<User, String> users, VerificationTokenRepository<User, String> tokens) {
		super();
		this.users = users;
		this.tokens = tokens;
	}
	
	public void setUser(User user){
		users.save(user);
		this.sendEmail(user.getEmail());
	}
	
	public User getUser(String email) {
		return users.findByEmail(email);
	}
	
	private void createVerificationToken(User user, String token) {
		VerificationToken newUserToken = new VerificationToken(user, token);
		tokens.save(newUserToken);
	}
	
	public void forgotPassword(String email) {
		User user = users.findByEmail(email);
		String token = UUID.randomUUID().toString();
		this.createVerificationToken(user, token);
		sendEmailVerificationToken(email, token);
	}
	
	private void sendEmailVerificationToken(String email, String token) {
		SimpleMailMessage message = new SimpleMailMessage();
		String url = "/v1/api/setPassword?token=" + token;
		String m = "Para redefinir a senha acesse o link abaixo";
		message.setText(m + "http://loaclhost:8080" + url);
		message.setTo(email);
		emailSender.send(message);	
	}

	private void sendEmail(String email) {
		SimpleMailMessage message = new SimpleMailMessage();
		message.setText("Seja bem-vindo ao AJUDE! <LINK> para primeiro acesso");
		message.setTo(email);
		emailSender.send(message);	
	}
	
	
}
