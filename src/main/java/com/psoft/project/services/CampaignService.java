package com.psoft.project.services;

import org.springframework.stereotype.Service;

import com.psoft.project.entities.Campaign;
import com.psoft.project.entities.User;
import com.psoft.project.repositories.CampaignRepository;

@Service
public class CampaignService {
	
	private CampaignRepository<Campaign, Integer> campaigns;

	public CampaignService(CampaignRepository<Campaign, Integer> campaigns) {
		super();
		this.campaigns = campaigns;
	}

	/*public CampaignService() {
		super();
	}*/

	public Campaign setCampaign(Campaign campaign) {
		this.campaigns.save(campaign);
		return campaign;
	}
	
	

}
