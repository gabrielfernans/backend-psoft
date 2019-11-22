package com.psoft.project.services;

import java.time.LocalDate;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;

import com.psoft.project.entities.Campaign;
import com.psoft.project.entities.User;
import com.psoft.project.exceptions.InvalidDateException;
import com.psoft.project.repositories.CampaignRepository;

@Service
public class CampaignService {
	
	private CampaignRepository<Campaign, Integer> campaigns;

	public CampaignService(CampaignRepository<Campaign, Integer> campaigns) {
		super();
		this.campaigns = campaigns;
	}

	public Campaign setCampaign(Campaign campaign) {
		
		if(campaign.getDeadLine().isBefore(LocalDate.now()))
			throw new InvalidDateException("deadLine deve ser valido");
		this.campaigns.save(campaign);
		return campaign;
	}
	
	//Retorna uma lista de campanhas que contem uma determinada substring.
	public List<Campaign> getCampaignBySubstring(String str, String[] status) {
		List<Campaign> resp = new ArrayList<Campaign>();
		List<Campaign> tempList = campaigns.findAll();
		
		//for para percorrer a lista de campanhas
		for(int i = 0;i<tempList.size();i++) {
			String tempStr = tempList.get(i).getName().toUpperCase();
			Campaign tempCamp = tempList.get(i);
			
			if(tempStr.contains(str.toUpperCase())){
				//for para percorrer a lista de possiveis status
				// por default, busca por status ativos
				for (int j = 0; j < status.length; j++) {
					if(status[j] == tempCamp.getStatus())
						resp.add(tempList.get(i));
				}
			}
		}
		return resp;
	}
	
	public Campaign findByUrlId(String url) {
		return this.campaigns.findByUrlId(url);
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
		if(c != null && newDate.isBefore(LocalDate.now()) && c.getOwner().getEmail().equals(user.getEmail())) {
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


}
