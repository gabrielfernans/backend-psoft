package com.psoft.project.controllers;

import javax.servlet.ServletException;

import java.util.List;


import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.web.bind.annotation.RequestMapping;


import com.psoft.project.entities.Campaign;
import com.psoft.project.entities.User;
import com.psoft.project.services.CampaignService;
import com.psoft.project.services.JWTService;

@RestController
@RequestMapping("/campaigns")
public class CampaignController {
	@Autowired
	private CampaignService campaignService;
	@Autowired
	private JWTService jwtservice;
	
	@PostMapping()
	public ResponseEntity<Campaign> setCampaign(@RequestHeader("Authorization") String header, @RequestBody @Valid Campaign campaign) throws ServletException {
		if(jwtservice.userExist(header) == null) {
			return new ResponseEntity<Campaign>(HttpStatus.NOT_FOUND);
		}try {
			User user = jwtservice.userExist(header); 
			if(jwtservice.userHasPermission(header, user.getEmail())) {
				campaign.setOwner(user);
				campaign.setStatus("Ativa");
				campaign.setDonations(0.0);
				return new ResponseEntity<Campaign>(campaignService.setCampaign(campaign), HttpStatus.OK);
			}
		}catch(ServletException s){
			//usuario esta com codigo invalido ou vencido
			return new ResponseEntity<Campaign>(HttpStatus.FORBIDDEN);
		}//usuario nao tem permissao
		return new ResponseEntity<Campaign>(HttpStatus.UNAUTHORIZED);
	}
	
	@GetMapping()
	//metodo para buscar as campanhas no repositorio que possuem uma substring no nome e status parametrizado pelo usuario
	//caso o usuario nao busque por nenhum status, por default, o metodo busca pelos status ativos
	//isso esta sendo tratado no frontend
	public ResponseEntity<List<Campaign>> getCampaign(@RequestBody @Valid String str, String[] status) {
			return new ResponseEntity<List<Campaign>>(campaignService.searchCampaign(str, status), HttpStatus.OK);
	}
	
	@GetMapping("/{url}")
	public ResponseEntity<Campaign> getCampaignByURL(@RequestHeader("Authorization") String header, @PathVariable("url") String url) {
		Campaign campaign = this.campaignService.findByUrlId(url);
		if(campaign == null) return new ResponseEntity<Campaign>( HttpStatus.NOT_FOUND);
		return new ResponseEntity<Campaign>(this.campaignService.findByUrlId(url), HttpStatus.OK);
	}

}
