package com.psoft.project.controllers;

import javax.servlet.ServletException;

import java.time.LocalDate;
import java.util.List;


import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.web.bind.annotation.RequestMapping;

import com.psoft.project.entities.Campaign;
import com.psoft.project.entities.User;
import com.psoft.project.services.CampaignService;
import com.psoft.project.services.JWTService;
import com.psoft.project.services.UserService;

@RestController
@RequestMapping("/campaigns")
public class CampaignController {
	@Autowired
	private CampaignService campaignService;
	@Autowired
	private JWTService jwtservice;
	private UserService uservice;
	
	public CampaignController(UserService uservice) {
		super();
		this.uservice = uservice;
	}

	@PostMapping()
	public ResponseEntity<Campaign> setCampaign(@RequestHeader("Authorization") String header, @RequestBody @Valid Campaign campaign) throws ServletException {
		if(jwtservice.userExist(header) == null) {
			return new ResponseEntity<Campaign>(HttpStatus.NOT_FOUND);
		}try {
			User user = jwtservice.userExist(header); 
			if(jwtservice.userHasPermission(header, user.getEmail())) {
				campaign.setOwner(user);
				campaign.setStatus("Ativa");
				return new ResponseEntity<Campaign>(campaignService.setCampaign(campaign), HttpStatus.OK);
			}
		}catch(ServletException s){
			//usuario esta com codigo invalido ou vencido
			return new ResponseEntity<Campaign>(HttpStatus.FORBIDDEN);
		}//usuario nao tem permissao
		return new ResponseEntity<Campaign>(HttpStatus.UNAUTHORIZED);
	}
	
	@GetMapping()
	/**
	 * metodo para buscar as campanhas no repositorio que possuem uma substring no nome e status parametrizado pelo usuario
	 * caso o usuario nao busque por nenhum status, por default, o metodo busca pelos status ativos
	 * isso esta sendo tratado no frontend
	 * 
	 * @param str Substring
	 * @param status Status da campanha
	 * @return
	 */
	public ResponseEntity<List<Campaign>> getCampaignBySubstring(@RequestHeader("Authorization") String header, @RequestBody String str, String[] status) throws ServletException {
		if(jwtservice.userExist(header) == null) {
			return new ResponseEntity<List<Campaign>>(HttpStatus.NOT_FOUND);
		} try {
			User user = jwtservice.userExist(header);
			if(jwtservice.userHasPermission(header, user.getEmail())) {
				return new ResponseEntity<List<Campaign>>(campaignService.getCampaignBySubstring(str, status), HttpStatus.OK);
			}
		}catch(ServletException s) {
			return new ResponseEntity<List<Campaign>>(HttpStatus.FORBIDDEN);
		}
		return new ResponseEntity<List<Campaign>>(HttpStatus.UNAUTHORIZED);
	}
	
	@GetMapping("/{url}")
	public ResponseEntity<Campaign> getCampaignByURL(@RequestHeader("Authorization") String header, @PathVariable("url") String url) throws ServletException {
		if(jwtservice.userExist(header) == null) {
			return new ResponseEntity<Campaign>(HttpStatus.NOT_FOUND);
		}try {
			User user = jwtservice.userExist(header); 
			if(jwtservice.userHasPermission(header, user.getEmail())) {
				Campaign campaign = this.campaignService.findByUrlId(url);
				if(campaign == null) return new ResponseEntity<Campaign>( HttpStatus.NOT_FOUND);
				return new ResponseEntity<Campaign>(campaign, HttpStatus.OK);
			}
		}catch(ServletException s){
			//usuario esta com codigo invalido ou vencido
			return new ResponseEntity<Campaign>(HttpStatus.FORBIDDEN);
		}//usuario nao tem permissao
		return new ResponseEntity<Campaign>(HttpStatus.UNAUTHORIZED);
	}
	
	@PutMapping("/{url}")
	public ResponseEntity<Campaign> finishCampaignByURL(@RequestHeader("Authorization") String header, @PathVariable("url") String url) throws ServletException {
		if(jwtservice.userExist(header) == null) {
			return new ResponseEntity<Campaign>(HttpStatus.NOT_FOUND);
		}try {
			User user = jwtservice.userExist(header); 
			if(jwtservice.userHasPermission(header, user.getEmail())) {
				Campaign campaign = this.campaignService.finishCampaignByURL(user, url);
				if(campaign == null) return new ResponseEntity<Campaign>( HttpStatus.NOT_FOUND);
				return new ResponseEntity<Campaign>(campaign, HttpStatus.OK);
			}
		}catch(ServletException s){
			//usuario esta com codigo invalido ou vencido
			return new ResponseEntity<Campaign>(HttpStatus.FORBIDDEN);
		}//usuario nao tem permissao
		return new ResponseEntity<Campaign>(HttpStatus.UNAUTHORIZED);
	}

	@PutMapping("/like/{url}")
	public ResponseEntity<Campaign> addLikeByURL(@RequestHeader("Authorization") String header, @PathVariable("url") String url) throws ServletException {
		if(jwtservice.userExist(header) == null) {
			return new ResponseEntity<Campaign>(HttpStatus.NOT_FOUND);
		}try {
			User user = jwtservice.userExist(header); 
			if(jwtservice.userHasPermission(header, user.getEmail())) {
				Campaign campaign = this.campaignService.addLikeByURL(user, url);
				if(campaign == null) return new ResponseEntity<Campaign>( HttpStatus.NOT_FOUND);
				return new ResponseEntity<Campaign>(campaign, HttpStatus.OK);
			}
		}catch(ServletException s){
			//usuario esta com codigo invalido ou vencido
			return new ResponseEntity<Campaign>(HttpStatus.FORBIDDEN);
		}//usuario nao tem permissao
		return new ResponseEntity<Campaign>(HttpStatus.UNAUTHORIZED);
	}
	
	@PutMapping("/dislike/{url}")
	public ResponseEntity<Campaign> addDislikeByURL(@RequestHeader("Authorization") String header, @PathVariable("url") String url) throws ServletException {
		if(jwtservice.userExist(header) == null) {
			return new ResponseEntity<Campaign>(HttpStatus.NOT_FOUND);
		}try {
			User user = jwtservice.userExist(header); 
			if(jwtservice.userHasPermission(header, user.getEmail())) {
				Campaign campaign = this.campaignService.addDislikeByURL(user, url);
				if(campaign == null) return new ResponseEntity<Campaign>( HttpStatus.NOT_FOUND);
				return new ResponseEntity<Campaign>(campaign, HttpStatus.OK);
			}
		}catch(ServletException s){
			//usuario esta com codigo invalido ou vencido
			return new ResponseEntity<Campaign>(HttpStatus.FORBIDDEN);
		}//usuario nao tem permissao
		return new ResponseEntity<Campaign>(HttpStatus.UNAUTHORIZED);
	}
	
	@PutMapping("/donation/{url}")
	public ResponseEntity<Campaign> addDonation(@RequestHeader("Authorization") String header, @PathVariable("url") String url, @RequestBody String value) throws ServletException {
		if(jwtservice.userExist(header) == null) {
			return new ResponseEntity<Campaign>(HttpStatus.NOT_FOUND);
		}try {
			User user = jwtservice.userExist(header); 
			if(jwtservice.userHasPermission(header, user.getEmail())) {
				Campaign campaign = this.campaignService.addDonation(user, url, Double.valueOf(value));
				if(campaign == null) return new ResponseEntity<Campaign>( HttpStatus.NOT_FOUND);
				return new ResponseEntity<Campaign>(campaign, HttpStatus.OK);
			}
		}catch(ServletException s){
			//usuario esta com codigo invalido ou vencido
			return new ResponseEntity<Campaign>(HttpStatus.FORBIDDEN);
		}//usuario nao tem permissao
		return new ResponseEntity<Campaign>(HttpStatus.UNAUTHORIZED);
	}
	
	//pega o top 5 de campanhas ativas
	@GetMapping("/actives")
	public ResponseEntity<List<Campaign>> getCampaignByDonations() throws ServletException {
		return new ResponseEntity<List<Campaign>>(campaignService.getActivesCampaigns(), HttpStatus.OK);
	}
	

	//get todas as campanhas que um usuario realizou alguma doacao
	@GetMapping("/donations/{email}")
	public ResponseEntity<List<Campaign>> getCampaignByDonor(@PathVariable("email") String email) throws ServletException {		
		User u = uservice.getUser(email);
		if(u != null) {
			return new ResponseEntity<List<Campaign>>(campaignService.getCampaignsByDonor(u), HttpStatus.OK);
		}
		return new ResponseEntity<List<Campaign>>( HttpStatus.NOT_FOUND);
	}
	
	//get todas as campanhas que um usuario realizou alguma doacao
		@GetMapping("/campaign/{email}")
		public ResponseEntity<List<Campaign>> getCampaignByOwner(@PathVariable("email") String email) throws ServletException {		
			User u = uservice.getUser(email);
			if(u != null) {
				return new ResponseEntity<List<Campaign>>(campaignService.getCampaignsByOwner(email), HttpStatus.OK);
			}
			return new ResponseEntity<List<Campaign>>( HttpStatus.NOT_FOUND);
		}

	//método para atualizar a deadline de uma campanha, faz checagem se o usuario esta logado e devidamente autorizado.
	@PutMapping("/deadline/{url}")
	public ResponseEntity<Campaign> updateDeadline(@RequestHeader("Authorization") String header, @PathVariable("url") String url, @RequestBody LocalDate newDate) throws ServletException{
			
		if(jwtservice.userExist(header) == null)
			return new ResponseEntity<Campaign>(HttpStatus.NOT_FOUND);
		try {
			User user = jwtservice.userExist(header);
			if(jwtservice.userHasPermission(header, user.getEmail())) {
				Campaign campaign = this.campaignService.updateDeadline(user, url, newDate);
				if(campaign == null) return new ResponseEntity<Campaign>(HttpStatus.BAD_REQUEST);
				return new ResponseEntity<Campaign>(campaign, HttpStatus.OK);
			}
		} catch(ServletException s) {
			return new ResponseEntity<Campaign>(HttpStatus.FORBIDDEN);
		}
		return new ResponseEntity<Campaign>(HttpStatus.UNAUTHORIZED);
	}
	
	//método para alterar a meta da campanha, checando se o usuário está logado e devidamente autorizado.
	@PutMapping("/goal/{url}")
	public ResponseEntity<Campaign> updateGoal(@RequestHeader("Authorization") String header, @PathVariable("url") String url, @RequestBody Double newGoal) throws ServletException {
		if(jwtservice.userExist(header) == null)
			return new ResponseEntity<Campaign>(HttpStatus.NOT_FOUND);//usuário nao encontrado
		try {
			User user = jwtservice.userExist(header);
			if(jwtservice.userHasPermission(header, user.getEmail())) {
				Campaign campaign = this.campaignService.updateGoal(user, url, newGoal);
				if(campaign == null) return new ResponseEntity<Campaign>(HttpStatus.BAD_REQUEST);//dados de parâmetro errados
				return new ResponseEntity<Campaign>(campaign, HttpStatus.OK);//retorna a campanha com a nova meta
			}
		} catch (ServletException s) {
			return new ResponseEntity<Campaign>(HttpStatus.FORBIDDEN);//token do usuario invalido ou vencido
		}
		return new ResponseEntity<Campaign>(HttpStatus.UNAUTHORIZED);//usuario sem permissao
	}

	
}
