package com.cookbook.deliveryfood.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.cookbook.deliveryfood.model.DeliveryDish;

@Repository
public interface DeliveryDishRepository extends CrudRepository<DeliveryDish, Integer>, QueryRepository{
    
}
