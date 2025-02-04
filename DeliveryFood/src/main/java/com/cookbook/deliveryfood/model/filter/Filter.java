package com.cookbook.deliveryfood.model.filter;

import java.util.List;

import com.cookbook.deliveryfood.model.enums.DishType;
import com.cookbook.deliveryfood.model.enums.Flavors;
import com.cookbook.deliveryfood.model.enums.MealPart;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Filter {
    private String name;
    private Range price;
    private String restaurant;
    private String deliveryPlatform;
    private Range rating;
    private DishType type;
    private List<Flavors> flavors;
    private MealPart mealPart;
}
