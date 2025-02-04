package com.cookbook.deliveryfood.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.cookbook.deliveryfood.model.DeliveryDish;

@Repository
public interface DeliveryDishRepository extends JpaRepository<DeliveryDish, Integer>, QueryRepository{

    @Query("SELECT d.id FROM DeliveryDish d")
    List<Integer> getIds();
    
}
