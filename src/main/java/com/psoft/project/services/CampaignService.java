package com.psoft.project.services;

import com.psoft.project.entities.Campaign;
import com.psoft.project.repositories.CampaignRepository;

public class CampaignService {
	
	private CampaignRepository<Campaign, Integer> campaigns;

	public Campaign setCampaign(Campaign campaign) {
		this.campaigns.save(campaign);
		return campaign;
	}
	
	

}
