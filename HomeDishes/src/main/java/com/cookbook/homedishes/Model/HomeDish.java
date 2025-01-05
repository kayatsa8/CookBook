package com.cookbook.homedishes.model;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.cookbook.homedishes.model.enums.Difficulty;
import com.cookbook.homedishes.model.enums.DishType;
import com.cookbook.homedishes.model.enums.Flavors;
import com.cookbook.homedishes.model.enums.MealPart;

import lombok.Getter;
import lombok.Setter;

@Document("HomeDish")
@Getter
@Setter
public class HomeDish {
    @Id
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


    public HomeDish(String name, int diners, String recipe, List<String> ingredients,
                    int timeInMinutes, DishType type, List<Flavors> flavors,
                    Difficulty difficulty, MealPart mealPart, int rating){

        this.name = name;
        this.diners = diners;
        this.recipe = recipe;
        this.ingredients = ingredients;
        this.timeInMinutes = timeInMinutes;
        this.type = type;
        this.flavors = flavors;
        this.difficulty = difficulty;
        this.mealPart = mealPart;
        this.rating = rating;
    }


}
