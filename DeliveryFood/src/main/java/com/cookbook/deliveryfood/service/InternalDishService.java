package com.cookbook.deliveryfood.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cookbook.deliveryfood.repository.DeliveryDishRepository;

@Service
public class InternalDishService {
    private final DeliveryDishRepository repo;


    @Autowired
    public InternalDishService(DeliveryDishRepository repository){
        this.repo = repository;
    }

    public boolean isDishExists(int id){
        return repo.existsById(id);
    }

    public List<Integer> checkDishList(List<Integer> ids){
        List<Integer> badIds = new ArrayList<>();

        for(int id : ids){
            if(!repo.existsById(id)){
                badIds.add(id);
            }
        }

        return badIds;
    }



    
}
