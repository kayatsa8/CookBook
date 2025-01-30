package com.cookbook.deliveryfood.model;

import java.util.List;

import com.cookbook.deliveryfood.model.enums.DishType;
import com.cookbook.deliveryfood.model.enums.Flavors;
import com.cookbook.deliveryfood.model.enums.MealPart;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class DeliveryDish {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private String name;
    private Double price;
    private String Restaurant;
    private String deliveryPlatform;
    private Integer rating;
    private DishType type;
    private List<Flavors> flavors;
    private MealPart mealPart;
    

    
}
