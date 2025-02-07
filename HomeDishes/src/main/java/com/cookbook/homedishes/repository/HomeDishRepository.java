package com.cookbook.homedishes.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import com.cookbook.homedishes.model.HomeDish;

@Repository
public interface HomeDishRepository extends MongoRepository<HomeDish, String>, TemplateRepository{

    @Query(value="{}", fields="{'id' : 1}")
    List<HomeDish> getAllIds();

    @Query(value="{'id' : ?0}", fields="{'type' : 1}")
    HomeDish getType(String id);
    
}
