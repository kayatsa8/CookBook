package com.cookbook.homedishes.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cookbook.homedishes.repository.HomeDishRepository;

@Service
public class DishMealService {
    private final HomeDishRepository repo;


    @Autowired
    public DishMealService(HomeDishRepository repository){
        this.repo = repository;
    }

    public boolean dishesExists(List<String> dishesIds){
        for(String id : dishesIds){
            if(!isDishExists(id)){
                return false;
            }
        }

        return true;
    }

    public boolean isDishExists(String id){
        return repo.existsById(id);
    }




    
}
