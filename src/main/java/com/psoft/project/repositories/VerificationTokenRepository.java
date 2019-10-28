package com.psoft.project.repositories;

import java.io.Serializable;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.psoft.project.entities.VerificationToken;

@Repository
public interface VerificationTokenRepository <T, Id extends Serializable> extends JpaRepository<VerificationToken, String>{

}
