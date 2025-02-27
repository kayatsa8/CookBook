package com.cookbook.mealgenerator.model.filters;

import java.util.List;

import com.cookbook.mealgenerator.model.enums.Difficulty;
import com.cookbook.mealgenerator.model.enums.MealPart;
import com.cookbook.mealgenerator.model.enums.MealType;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class HomeDishFilter {

    private Range diners;
    private List<String> ingredients;
    private Range timeInMinutes;
    private MealType type;
    private Difficulty difficulty;
    private MealPart mealPart;
    private Range rating;    

}
