package com.cookbook.homedishes.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cookbook.homedishes.repository.HomeDishRepository;

@Service
public class InternalDishService {
    private final HomeDishRepository repo;


    @Autowired
    public InternalDishService(HomeDishRepository repository){
        this.repo = repository;
    }

    public List<String> dishesExists(List<String> dishesIds){
        List<String> notExists = new ArrayList<>();

        for(String id : dishesIds){
            if(!isDishExists(id)){
                notExists.add(id);
            }
        }

        return notExists;
    }

    public boolean isDishExists(String id){
        return repo.existsById(id);
    }




    
}
