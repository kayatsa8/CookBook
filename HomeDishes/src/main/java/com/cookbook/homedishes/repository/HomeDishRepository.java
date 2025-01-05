package com.cookbook.homedishes.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.cookbook.homedishes.model.HomeDish;

@Repository
public interface HomeDishRepository extends MongoRepository<HomeDish, String>{
    
}
