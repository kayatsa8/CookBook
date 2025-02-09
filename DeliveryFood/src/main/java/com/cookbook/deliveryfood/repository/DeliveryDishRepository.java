package com.cookbook.deliveryfood.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.cookbook.deliveryfood.model.DeliveryDish;
import com.cookbook.deliveryfood.model.enums.DishType;

@Repository
public interface DeliveryDishRepository extends JpaRepository<DeliveryDish, Integer>, QueryRepository{

    @Query("SELECT d.id FROM DeliveryDish d")
    List<Integer> getIds();

    @Query("SELECT d.type FROM DeliveryDish d WHERE d.id = ?1")
    DishType getDishType(int id);

    @Query("SELECT d.rating FROM DeliveryDish d WHERE d.id = ?1")
    int getDishRating(int id);
    
}
