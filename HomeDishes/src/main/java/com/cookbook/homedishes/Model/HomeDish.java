package com.cookbook.homedishes.model;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.cookbook.homedishes.model.enums.Difficulty;
import com.cookbook.homedishes.model.enums.DishType;
import com.cookbook.homedishes.model.enums.Flavors;
import com.cookbook.homedishes.model.enums.MealPart;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Document("HomeDish")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class HomeDish {
    @Id
    private String id;
    private String name;
    private Integer diners;
    private String[] recipe;
    private List<String> ingredients;
    private Integer timeInMinutes;
    private DishType type;
    private List<Flavors> flavors;
    private Difficulty difficulty;
    private MealPart mealPart;
    private Integer rating;


    public void updateFromOther(HomeDish other){
        name = (other.getName() == null || other.getName().isEmpty()) ? name : other.getName();
        diners = other.getDiners() == null ? diners : other.getDiners();
        recipe = other.getRecipe() == null ? recipe : other.getRecipe();
        ingredients = other.getIngredients() == null ? ingredients : other.getIngredients();
        timeInMinutes = other.getTimeInMinutes() == null ? timeInMinutes : other.getTimeInMinutes();
        type = other.getType() == null ? type : other.getType();
        flavors = other.getFlavors() == null ? flavors : other.getFlavors();
        difficulty = other.getDifficulty() == null ? difficulty : other.getDifficulty();
        mealPart = other.getMealPart() == null ? mealPart : other.getMealPart();
        rating = other.getRating() == null ? rating : other.getRating();
    }


}
