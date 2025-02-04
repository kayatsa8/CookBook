package com.cookbook.meals.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.cookbook.meals.model.Meal;

@Repository
public interface MealRepository extends MongoRepository<Meal, String> {
    
}
