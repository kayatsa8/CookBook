package com.cookbook.meals.model;

import java.util.List;

import com.cookbook.meals.model.enums.Flavors;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class DetailedMeal extends Meal{
    private List<String> homeDishesNames;
    private List<String> deliveryDishesNames;
    private List<String> ingredients;
    private List<Flavors> flavors;

}
