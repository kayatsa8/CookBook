package com.cookbook.homedishes.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cookbook.homedishes.exception.IllegalDishException;
import com.cookbook.homedishes.model.HomeDish;
import com.cookbook.homedishes.model.enums.Difficulty;
import com.cookbook.homedishes.model.enums.DishType;
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
                System.out.println(id);
            }
        }

        return notExists;
    }

    public boolean isDishExists(String id){
        return repo.existsById(id);
    }

    public Set<DishType> getDishesTypes(List<String> ids) throws IllegalDishException{
        Set<DishType> types = new HashSet<>();
        HomeDish dish;

        for(String id : ids){
            dish = repo.getType(id);

            if(dish == null){
                throw new IllegalDishException("a dish with id '" + id + "' was not found");
            }

            types.add(dish.getType());
        }

        return types;
    }

    public Difficulty getMaxDishDifficulty(List<String> ids) throws IllegalDishException{
        Difficulty max = Difficulty.EASY;
        HomeDish dish;

        for(String id : ids){
            if(!repo.existsById(id)){
                throw new IllegalDishException("a dish with id: '" + id + "' was not found");
            }

            dish = repo.getDifficulty(id);

            if(dish.getDifficulty() != null && dish.getDifficulty().getValue() > max.getValue()){
                max = dish.getDifficulty();
            }
        }

        return max;
    }

    public int getRatingSum(List<String> ids) throws IllegalDishException{
        int sum = 0;
        HomeDish dish;

        for(String id : ids){
            if(!repo.existsById(id)){
                throw new IllegalDishException("a dish with id: '" + id + "' was not found");
            }

            dish = repo.getRating(id);

            sum += dish.getRating();
        }

        return sum;
    }



    
}
