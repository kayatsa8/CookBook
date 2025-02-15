package com.cookbook.meals.model;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.cookbook.meals.model.enums.MealTime;
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
    protected String id;

    protected String name;
    protected Integer diners;
    protected List<String> homeDishesIds;
    protected List<Integer> deliveryDishesIds;
    protected List<String> toBuy;
    protected List<MealTime> mealTime;
    protected Integer userRating; // given by the user
    protected List<SpecialOccation> specialOccations;





    public void updateMeal(Meal other){
        this.name = other.name == null ? this.name : other.name;
        this.diners = other.diners == null ? this.diners : other.diners;
        this.homeDishesIds = other.homeDishesIds == null ? this.homeDishesIds : other.homeDishesIds;
        this.deliveryDishesIds = other.deliveryDishesIds == null ? this.deliveryDishesIds : other.deliveryDishesIds;
        this.toBuy = other.toBuy == null ? this.toBuy : other.toBuy;
        this.mealTime = other.mealTime == null ? this.mealTime : other.mealTime;
        this.userRating = other.userRating == null ? this.userRating : other.userRating;
        this.specialOccations = other.specialOccations == null ? this.specialOccations : other.specialOccations;
    }
    

    

}
