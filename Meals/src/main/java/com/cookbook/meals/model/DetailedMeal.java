package com.cookbook.meals.model;

import java.util.List;

import com.cookbook.meals.model.enums.Flavors;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public class DetailedMeal extends Meal{
    private List<String> homeDishesNames;
    private List<String> deliveryDishesNames;
    private List<String> ingredients;
    private List<Flavors> flavors;

}
