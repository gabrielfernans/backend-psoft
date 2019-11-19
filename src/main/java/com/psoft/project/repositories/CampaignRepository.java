package com.psoft.project.repositories;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.psoft.project.entities.Campaign;
import com.psoft.project.entities.User;

@Repository
public interface CampaignRepository<T, ID extends Serializable> extends JpaRepository<Campaign, Integer> {
	
	//@Query(value = "select c from Campaign where c.urlid = url")
	Campaign findByUrlId(String url);
	
	//retorna as campanhas ordenadas pelos likes 
	@Query(value = "select u from Campaign u where u.status = 'Ativa' order by u.likes DESC, u.id ASC")
	public List<Campaign> findAllByStatus();
}
