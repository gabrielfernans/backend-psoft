package com.psoft.project.repositories;

import java.io.Serializable;

import org.springframework.data.jpa.repository.JpaRepository;

import com.psoft.project.entities.Like;
import com.psoft.project.entities.User;

public interface LikeRepository<T, ID extends Serializable> extends JpaRepository<Like, User> {

}
