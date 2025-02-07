package com.cookbook.deliveryfood.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cookbook.deliveryfood.model.enums.DishType;
import com.cookbook.deliveryfood.model.exception.DishNotFoundException;
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

    public Set<DishType> getTypes(List<Integer> ids) throws DishNotFoundException{
        Set<DishType> types = new HashSet<>();
        DishType type;

        for(int id : ids){
            if(!repo.existsById(id)){
                throw new DishNotFoundException();
            }

            type = repo.getDishType(id);
            types.add(type);
        }

        return types;
    }


    
}
