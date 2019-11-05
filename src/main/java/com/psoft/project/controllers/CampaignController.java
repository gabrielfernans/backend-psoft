package com.psoft.project.controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.psoft.project.entities.Campaign;
import com.psoft.project.services.CampaignService;

public class CampaignController {
	@Autowired
	private CampaignService campaignService;
	
	
	@PostMapping("/campaigns")
	public ResponseEntity<Campaign> setCampaign(@RequestBody @Valid Campaign campaign) {
		return new ResponseEntity<Campaign>(campaignService.setCampaign(campaign), HttpStatus.OK);
	}

}
