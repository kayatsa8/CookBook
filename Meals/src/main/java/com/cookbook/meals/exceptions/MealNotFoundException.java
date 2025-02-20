package com.cookbook.meals.exceptions;

public class MealNotFoundException extends Exception {
    
    public MealNotFoundException(){
        super("the meal was not found");
    }


}
