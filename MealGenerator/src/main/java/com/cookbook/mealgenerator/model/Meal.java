package com.cookbook.mealgenerator.model;

import java.util.List;

import com.cookbook.mealgenerator.model.enums.Difficulty;
import com.cookbook.mealgenerator.model.enums.Flavors;
import com.cookbook.mealgenerator.model.enums.MealTime;
import com.cookbook.mealgenerator.model.enums.MealType;
import com.cookbook.mealgenerator.model.enums.SpecialOccation;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Meal {
    
    private String name; // generate by comining dishes names
    private List<String> homeDishesIds; // get random (filtered)
    private List<Integer> deliveryDishesIds; // get random (filtered)
    private List<MealTime> mealTime; // random
    private List<SpecialOccation> specialOccations; // random
    private List<String> homeDishesNames; // gets it with the id
    private List<String> deliveryDishesNames; // gets it with the id
    private List<String> ingredients; // get from HomeDish
    private List<Flavors> flavors; // get from HomeDish and DeliveryFood
    private List<MealType> type; // get from HomeDish and DeliveryFood
    private Difficulty difficulty; // get from HomeDish
    private Integer averageDishRating; // the avg of the rating of all dishes, get from HomeDish and DeliveryFood


}
