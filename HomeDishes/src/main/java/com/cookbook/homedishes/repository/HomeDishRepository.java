package com.cookbook.homedishes.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.cookbook.homedishes.model.HomeDish;

public interface HomeDishRepository extends MongoRepository<HomeDish, String>{
    
}
