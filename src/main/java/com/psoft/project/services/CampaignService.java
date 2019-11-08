package com.psoft.project.services;

import java.time.LocalDate;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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
	public List<Campaign> searchCampaign(String str) {
		List<Campaign> resp = new ArrayList<Campaign>();
		List<Campaign> tempList = campaigns.findAll();
		
		for(int i = 0;i<tempList.size();i++) {
			String tempStr = tempList.get(i).getName().toUpperCase();
			if(tempStr.contains(str.toUpperCase())) 
				resp.add(tempList.get(i));
		}
		return resp;
	}
	
	
	
	
	
}
