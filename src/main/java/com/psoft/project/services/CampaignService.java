package com.psoft.project.services;

import java.time.LocalDate;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import com.psoft.project.entities.Campaign;
import com.psoft.project.entities.Comment;
import com.psoft.project.entities.User;
import com.psoft.project.exceptions.InvalidDateException;
import com.psoft.project.repositories.CampaignRepository;
import com.psoft.project.repositories.CommentRepository;

@Service
public class CampaignService {
	
	private CampaignRepository<Campaign, Integer> campaigns;
	private CommentRepository<Comment, Integer> comments;
	
	public CampaignService(CampaignRepository<Campaign, Integer> campaigns, CommentRepository<Comment, Integer> comments) {
		super();
		this.campaigns = campaigns;
		this.comments = comments;
	}
	

	public Campaign setCampaign(Campaign campaign) {
		
		if(campaign.getDeadLine().isBefore(LocalDate.now()))
			throw new InvalidDateException("deadLine deve ser valido");
		this.campaigns.save(campaign);
		return campaign;
	}
	
	//Retorna uma lista de campanhas que contem uma determinada substring.
	public List<Campaign> getCampaignBySubstring(String newDesc) {
		return this.campaigns.findBySubString(newDesc);
	}
	
	public Campaign findByUrlId(String url) {
		Campaign c =  this.campaigns.findByUrlId(url);
		c.checkConcluded();
		c.checkExpired();
		return c;
	}
	
	public Campaign finishCampaignByURL(User user, String url){
		Campaign c = this.campaigns.findByUrlId(url);
		if(c!= null && c.getOwner().getEmail().equals(user.getEmail())) {
			c.setStatus("Encerrada");
			this.campaigns.save(c);
		}
		return c;
	}
	
	public Campaign addLikeByURL(User user, String url){
		Campaign c = this.campaigns.findByUrlId(url);
		if(c != null) {
			c.addLike(user);
			this.campaigns.save(c);
		}
		return c;
	}
	
	public Campaign addDislikeByURL(User user, String url){
		Campaign c = this.campaigns.findByUrlId(url);
		if(c != null) {
			c.addDislike(user);
			this.campaigns.save(c);
		}
		return c;
	}
	
	public Campaign addDonation(User user, String url, Double value){
		Campaign c = this.campaigns.findByUrlId(url);
		if(c != null) {
			if(c.addDonation(user, value) == null ) return null;
			this.campaigns.save(c);
		}
		return c;
	}
	

	public List<Campaign> getActivesCampaigns(){
		return campaigns.findAllCampaignsByStatus();
	}
	
	public List<Campaign> getCampaignsByDonor(User user){
		return campaigns.findAllCampaignsByDonations_Donor(user);
	}


	//método para mudar a deadline da campanha apenas se a nova data estiver no futuro.
	public Campaign updateDeadline(User user, String url, LocalDate newDate) {
		Campaign c = this.campaigns.findByUrlId(url);
		if(c != null && !newDate.isBefore(LocalDate.now()) && c.getOwner().getEmail().equals(user.getEmail())) {
			c.setDeadLine(newDate);
			this.campaigns.save(c);
		}
		return c;
	}

	//método para alterar a meta de uma campanha, apenas se a data estiver no futuro e o usuário for o dono da campanha.
	public Campaign updateGoal(User user, String url, Double newGoal) {
		Campaign c = this.campaigns.findByUrlId(url);
		if(c != null && !c.getDeadLine().isBefore(LocalDate.now()) && c.getOwner().getEmail().equals(user.getEmail())) {
			c.setGoal(newGoal);
			this.campaigns.save(c);
		}
		return c;
	}

	public List<Campaign> getCampaignsByOwner(String email){
		return campaigns.findAllCampaignsByOwner(email);
	}

	public Campaign addComment(User user, String url, Comment comment) {
		Campaign c = this.campaigns.findByUrlId(url);

		if (c != null) {
			comment.setCampaign(c);;
			comment.setUser(user);
			c.getComments().add(comment);
			this.comments.save(comment);
			this.campaigns.save(c);
		} 
		return c;
	}
	
	public Campaign replyComment(User user, String url, Comment reply, String idComment) {
		Campaign c = this.campaigns.findByUrlId(url);
		if(c != null) {
			Comment com = this.comments.getOne(Integer.parseInt(idComment));
			reply.setCampaign(c);
			reply.setUser(user);
			com.getReplies().add(reply);
			this.comments.save(reply);
			this.campaigns.save(c);
		}
		return c;
	}
	
	public Campaign deleteComment(User user, String url, String idComment) {
		Campaign c = this.campaigns.findByUrlId(url);
		Comment com = this.comments.getOne(Integer.parseInt(idComment));
		if(c != null && !com.isDeleted() && com.getUser().getEmail().equals(user.getEmail())) {
			com.deleteComment();
			this.comments.save(com);
			this.campaigns.save(c);
		}
		return c;
	}
	
	public Campaign setDescription(User user, String url, String description) {
		Campaign c = this.campaigns.findByUrlId(url);
		if(c != null && c.getOwner().getEmail().equals(user.getEmail())) {
			c.setDescription(description);
			this.campaigns.save(c);
		}
		return c;
	}

}
