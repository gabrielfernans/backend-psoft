package com.psoft.project.controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.psoft.project.entities.Campaign;
import com.psoft.project.entities.User;
import com.psoft.project.services.CampaignService;

@RestController
public class CampaignController {
	@Autowired
	private CampaignService campaignService;
	
	
	@PostMapping("/campaigns")
	public ResponseEntity<Campaign> setCampaign(@RequestBody @Valid Campaign campaign) {
		System.out.println(campaign.getOwner().getEmail());
		//Campaign c = new Campaign(1, "aaa", "bbbb", "aaa", "2020-11-20", "aaa",
		//1.1, new User("deb@email","deb", "leda", "123", "123"));
		return new ResponseEntity<Campaign>(campaignService.setCampaign(campaign), HttpStatus.OK);
	}

}
