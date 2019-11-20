package com.psoft.project.repositories;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.psoft.project.entities.Campaign;
import com.psoft.project.entities.User;

@Repository
public interface CampaignRepository<T, ID extends Serializable> extends JpaRepository<Campaign, Integer> {
	
	//@Query(value = "select c from Campaign where c.urlid = url")
	Campaign findByUrlId(String url);
	
	//retorna as campanhas ordenadas pelos likes 
	@Query(value = "SELECT u FROM Campaign u WHERE u.status = 'Ativa'")
	public List<Campaign> findAllCampaignsByStatus();
	
	//retorna as campanhas ordenadas pelos likes 
	//@Query(value = "SELECT u FROM Campaign u WHERE Donation.DONOR_EMAIL = :email")
	public List<Campaign> findAllCampaignsByDonations_Donor(@Param("email") User email);
	
	@Query(value = "select * from Campaign as u where OWNER_EMAIL like :email", nativeQuery = true)
	public List<Campaign> findAllCampaignsByOwner(@Param("email") String email);
}
