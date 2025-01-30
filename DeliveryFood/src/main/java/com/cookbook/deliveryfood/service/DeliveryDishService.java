package com.cookbook.deliveryfood.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cookbook.deliveryfood.model.DeliveryDish;
import com.cookbook.deliveryfood.model.exception.DishNotFoundException;
import com.cookbook.deliveryfood.model.exception.InvalidDishException;
import com.cookbook.deliveryfood.repository.DeliveryDishRepository;

@Service
public class DeliveryDishService {

    @Autowired
    private DeliveryDishRepository repo;


    public void addDish(DeliveryDish dish) throws InvalidDishException{
        validateAddDish(dish);

        repo.save(dish);
    }

    public DeliveryDish getDish(int id) throws DishNotFoundException{
        Optional<DeliveryDish> oDish = repo.findById(id);

        if(oDish.isEmpty()){
            throw new DishNotFoundException();
        }

        return oDish.get();
    }





    private void validateAddDish(DeliveryDish dish) throws InvalidDishException{
        if(dish.getName() == null || dish.getName().isBlank()){
            throw new InvalidDishException("name not provided");
        }

        if(dish.getPrice() == null || dish.getPrice() < 0){
            throw new InvalidDishException("empty or negative price");
        }

        if(dish.getRestaurant() == null || dish.getRestaurant().isBlank()){
            throw new InvalidDishException("restaurant not provided");
        }

        if(dish.getDeliveryPlatform() == null || dish.getDeliveryPlatform().isBlank()){
            throw new InvalidDishException("delivery platform not provided");
        }

        if(dish.getRating() == null || dish.getRating() < 0 || dish.getRating() > 5){
            throw new InvalidDishException("invalid rating");
        }

        if(dish.getType() == null){
            throw new InvalidDishException("dish type not provided");
        }

        if(dish.getFlavors() == null){
            throw new InvalidDishException("flavors not provided");
        }

        if(dish.getMealPart() == null){
            throw new InvalidDishException("meal part not provided");
        }

    }

    
}
