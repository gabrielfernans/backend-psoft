package com.psoft.project.services;

import java.util.ArrayList;
import java.util.List;

import com.psoft.project.entities.Campaign;
import com.psoft.project.repositories.CampaignRepository;

public class CampaignService {
	
	private CampaignRepository<Campaign, Integer> campaigns;

	public CampaignService(CampaignRepository<Campaign, Integer> campaigns) {
		this.campaigns = campaigns;
	}
	
	//adiciona uma campanha no repositorio
	public Campaign setCampaign(Campaign campaign) {
		this.campaigns.save(campaign);
		return campaign;
	}
	
	//Retorna uma lista de campanhas que contem uma determinada substring.
	public List<Campaign> searchCampaign(String str, String[] status) {
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
	
	
	
	
	
}
