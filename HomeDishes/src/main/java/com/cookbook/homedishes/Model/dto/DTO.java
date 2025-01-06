package com.cookbook.homedishes.model.dto;

import java.util.List;

import com.cookbook.homedishes.model.enums.Difficulty;
import com.cookbook.homedishes.model.enums.DishType;
import com.cookbook.homedishes.model.enums.Flavors;
import com.cookbook.homedishes.model.enums.MealPart;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DTO {
    private String name;
    private int diners;
    private String recipe;
    private List<String> ingredients;
    private int timeInMinutes;
    private DishType type;
    private List<Flavors> flavors;
    private Difficulty difficulty;
    private MealPart mealPart;
    private int rating;
}
