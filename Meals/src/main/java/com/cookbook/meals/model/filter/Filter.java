package com.cookbook.meals.model.filter;

import java.util.List;

import com.cookbook.meals.model.enums.Difficulty;
import com.cookbook.meals.model.enums.Flavors;
import com.cookbook.meals.model.enums.MealTime;
import com.cookbook.meals.model.enums.MealType;
import com.cookbook.meals.model.enums.SpecialOccation;

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
    private Integer diners;
    private Range diner;
    private List<String> homeDishesIds;
    private List<Integer> deliveryDishesIds;
    private List<String> toBuy;
    private List<MealTime> mealTime;
    private Range userRating;
    private List<SpecialOccation> specialOccations;
    private List<String> ingredients;
    private List<Flavors> flavors;
    private List<MealType> type;
    private Difficulty difficulty;
    private Range averageDishRating;

}
