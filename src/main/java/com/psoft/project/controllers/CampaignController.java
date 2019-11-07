package com.psoft.project.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.psoft.project.entities.Campaign;
import com.psoft.project.services.CampaignService;

@RequestMapping("/campaigns")
public class CampaignController {
	@Autowired
	private CampaignService campaignService;
	
	
	@PostMapping()
	//metodo para criar campanhas
	public ResponseEntity<Campaign> setCampaign(@RequestBody @Valid Campaign campaign) {
		return new ResponseEntity<Campaign>(campaignService.setCampaign(campaign), HttpStatus.OK);
	}
	
	@GetMapping()
	//metodo para buscar as campanhas no repositorio que possuem uma substring no nome e status parametrizado pelo usuario
	//caso o usuario nao busque por nenhum status, por default, o metodo busca pelos status ativos
	//isso esta sendo tratado no frontend
	public ResponseEntity<List<Campaign>> getCampaign(@RequestBody @Valid String str, String[] status) {
			return new ResponseEntity<List<Campaign>>(campaignService.searchCampaign(str, status), HttpStatus.OK);
	}

}
