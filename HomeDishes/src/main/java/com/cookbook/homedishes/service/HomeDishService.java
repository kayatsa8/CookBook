package com.cookbook.homedishes.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cookbook.homedishes.exception.DishExistsException;
import com.cookbook.homedishes.exception.IlligalDishException;
import com.cookbook.homedishes.model.HomeDish;
import com.cookbook.homedishes.repository.HomeDishRepository;

@Service
public class HomeDishService {

    @Autowired
    private HomeDishRepository repo;



    public void addDish(HomeDish dish) throws DishExistsException, IlligalDishException{        
        if(isDishExists(dish.getName())){
            throw new DishExistsException(dish.getName());
        }

        validateDishData(dish);

        repo.insert(dish);
    }




    private boolean isDishExists(String name){
        return repo.existsById(name);
    }

    private void validateDishData(HomeDish dish) throws IlligalDishException{
        if(dish.getName() == null || dish.getName().isEmpty()){
            throw new IlligalDishException("the dish name is empty");
        }

        if(dish.getType() == null){
            throw new IlligalDishException("the type of the dish must be specified");
        }
    }

    
}
