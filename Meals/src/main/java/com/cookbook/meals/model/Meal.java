package com.cookbook.meals.model;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.cookbook.meals.model.enums.Difficulty;
import com.cookbook.meals.model.enums.MealTime;
import com.cookbook.meals.model.enums.MealType;
import com.cookbook.meals.model.enums.SpecialOccation;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Document("Meal")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Meal {
    @Id
    private String id;

    private String name;
    private Integer diners;
    private List<String> homeDishesIds;
    private List<Integer> deliveryDishesIds;
    private List<String> toBuy;
    private List<MealTime> mealTime;
    private Integer userRating; // given by the user
    private List<SpecialOccation> specialOccations;

    // TODO: move type, difficulty and rating to DetailedMeal, and their functions in service as well.
    private List<MealType> type; // all types of components, K
    private Difficulty difficulty; // the maximum difficulty of the home dishes
    private Integer averageDishRating; // the avg of the rating of all dishes
    

    

}
