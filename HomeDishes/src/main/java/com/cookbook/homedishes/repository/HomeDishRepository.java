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
    
    @Query(value="{'id' : ?0}", fields="{'difficulty' : 1}")
    HomeDish getDifficulty(String id);

    @Query(value="{'id' : ?0}", fields="{'rating' : 1}")
    HomeDish getRating(String id);

    @Query(value="{'id' : ?0}", fields="{'name' : 1}")
    HomeDish getDishName(String id);

    @Query(value="{'id' : ?0}", fields="{'ingredients' : 1}")
    HomeDish getDishIngredients(String id);

    @Query(value="{'id' : ?0}", fields="{'flavors' : 1}")
    HomeDish getDishFlavors(String id);

    @Query(value = "{}", fields = "{'name' : 1}")
    List<HomeDish> getAllDishNames();
}
