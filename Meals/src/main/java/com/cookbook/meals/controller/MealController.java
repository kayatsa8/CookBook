package com.cookbook.meals.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.cookbook.meals.model.Meal;
import com.cookbook.meals.model.exceptions.IllegalMealException;
import com.cookbook.meals.service.MealService;

@RestController
@RequestMapping("/api/meal")
public class MealController {
    private final MealService service;


    @Autowired
    public MealController(MealService service){
        this.service = service;
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/add")
    public void addMeal(@RequestBody Meal meal){
        try{
            service.addMeal(meal);
        }
        catch(IllegalMealException e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }



    
}
