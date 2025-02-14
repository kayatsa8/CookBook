package com.cookbook.meals.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import com.cookbook.meals.model.Meal;

@Repository
public interface MealRepository extends MongoRepository<Meal, String> {
    
    @Query(value = "{}", fields = "{'id': 1, 'name': 1}")
    List<Meal> getIdsAndNames();

}
