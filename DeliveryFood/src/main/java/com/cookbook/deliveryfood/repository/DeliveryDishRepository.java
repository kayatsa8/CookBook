package com.cookbook.deliveryfood.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.cookbook.deliveryfood.model.DTO;
import com.cookbook.deliveryfood.model.DeliveryDish;
import com.cookbook.deliveryfood.model.enums.DishType;
import com.cookbook.deliveryfood.model.enums.Flavors;

@Repository
public interface DeliveryDishRepository extends JpaRepository<DeliveryDish, Integer>, QueryRepository{

    @Query("SELECT d.id FROM DeliveryDish d")
    List<Integer> getIds();

    @Query("SELECT d.type FROM DeliveryDish d WHERE d.id = ?1")
    DishType getDishType(int id);

    @Query("SELECT d.rating FROM DeliveryDish d WHERE d.id = ?1")
    int getDishRating(int id);

    @Query("SELECT d.name FROM DeliveryDish d WHERE d.id = ?1")
    String getDishName(int id);

    @Query("SELECT d.flavors FROM DeliveryDish d WHERE d.id = ?1")
    List<List<Flavors>> getDishFlavors(int id);

    @Query("SELECT new com.cookbook.deliveryfood.model.DTO(d.id, d.name) FROM DeliveryDish d")
    List<DTO> getIdsAndNames();
    
}
