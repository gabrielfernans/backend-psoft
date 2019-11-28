package com.psoft.project.services;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.psoft.project.entities.Campaign;
import com.psoft.project.entities.Comment;
import com.psoft.project.entities.User;
import com.psoft.project.repositories.CampaignRepository;
import com.psoft.project.repositories.CommentRepository;

@Service
public class CommentService {
	
	@Autowired
	private CommentRepository<Comment, Long> comments;
	
	public CommentService() {
		
	}
	
	public CommentService(CommentRepository<Comment, Long> comments) {
		this.comments = comments;
	}
	
	 public Comment create(Campaign campaign, User user, String text, long idComment) {
		 	
	        if (text == null) throw new IllegalArgumentException("O comentário não pode ser Null");
	        if (text.trim().equals(""))
	            throw new IllegalArgumentException("O comentário não pode ser vazio, insira um comentário valido");
	        if (user == null) throw new IllegalArgumentException("O usuário não pode ser Null");
	        if (campaign == null) throw new IllegalArgumentException("A campanha não pode ser Null");
	        
	        LocalDate data = LocalDate.now();
	        Comment com = new Comment(campaign, user, text, data, new ArrayList<Comment>());
	        if (idComment == 0) {
	            campaign.addComment(com);
	        } else {
	            Comment comentarioPai = this.comments.findById(idComment);
	            comentarioPai.addReply(com);
	        }

	        return this.comments.save(com);
	    }
	 
	 public Comment findById(long id) {
	        Comment com = this.comments.findById(id);
	        if (com == null) {
	            throw new IllegalArgumentException("Comentário não existe!");
	        }
	        if (com.isDeleted()) {
	            throw new IllegalArgumentException("Comentário apagado!");
	        }
	        return com;
	    }
	 
	 public List<Comment> findAll() {
	        List<Comment> comments = this.comments.findAll();
	        if (comments.isEmpty()) {
	            throw new IllegalArgumentException("Não há disciplinas cadastradas");
	        }
	        return comments;
	    }
	 
	 public Comment deleteUpdate(long idComment, String userMail) {
	        Comment com = this.comments.findById(idComment);
	        if (com == null) {
	            throw new IllegalArgumentException("Comentário não encontrado!!");
	        }
	        if (!(com.getUser().getEmail().equals(userMail))) {
	            throw new IllegalArgumentException("Esse comentário não pertece ao usuário passado no token");
	        }
	        com.deleteComment();
	        return this.comments.save(com);
	    }

}
