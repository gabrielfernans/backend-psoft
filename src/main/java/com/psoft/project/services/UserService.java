package com.psoft.project.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.psoft.project.entities.User;
import com.psoft.project.repositories.UserRepository;

@Service
public class UserService {
	
	private UserRepository<User, String> users;
	@Autowired
	private JavaMailSender emailSender;
	
	public UserService(UserRepository<User, String> users) {
		super();
		this.users = users;
	}
	
	public void setUser(User user){
		users.save(user);
	}
	
	public User getUser(String email) {
		return users.findByEmail(email);
	}

	public void sendEmail(String email) {
		SimpleMailMessage message = new SimpleMailMessage();
		message.setText("Email send from AJUDA-psoft");
		message.setTo(email);
		emailSender.send(message);
		
	}
}
