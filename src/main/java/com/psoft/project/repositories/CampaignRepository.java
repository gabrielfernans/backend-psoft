package com.psoft.project.repositories;

import java.io.Serializable;

import org.springframework.data.jpa.repository.JpaRepository;

import com.psoft.project.entities.Campaign;

public interface CampaignRepository<T, ID extends Serializable> extends JpaRepository<Campaign, Integer> {

}
