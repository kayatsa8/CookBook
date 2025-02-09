package com.cookbook.meals.model;

import java.util.List;

import com.cookbook.meals.model.enums.Difficulty;
import com.cookbook.meals.model.enums.Flavors;
import com.cookbook.meals.model.enums.MealType;

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
    private List<MealType> type; // all types of components, K
    private Difficulty difficulty; // the maximum difficulty of the home dishes
    private Integer averageDishRating; // the avg of the rating of all dishes

}
