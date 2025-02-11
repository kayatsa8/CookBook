package com.cookbook.meals.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.cookbook.meals.model.DetailedMeal;
import com.cookbook.meals.model.Meal;
import com.cookbook.meals.model.exceptions.IllegalMealException;
import com.cookbook.meals.model.exceptions.MealNotFoundException;
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

    @ResponseStatus(HttpStatus.FOUND)
    @GetMapping("/get/{id}")
    public DetailedMeal getMeal(@PathVariable String id){
        try{
            DetailedMeal meal = service.getMeal(id);
            return meal;
        }
        catch(MealNotFoundException | IllegalMealException e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/all")
    public Map<String, String> getMealsIdAndName(){
        Map<String, String> id_name = service.getMealsIdAndName();

        return id_name;
    }

    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping("/delete/{id}")
    public void deleteMeal(@PathVariable String id){
        try{
            service.deleteMeal(id);
        }
        catch(MealNotFoundException e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @ResponseStatus(HttpStatus.OK)
    @PatchMapping("/update")
    public void updateMeal(@RequestBody Meal meal){
        try{
            service.updateMeal(meal);
        }
        catch(MealNotFoundException | IllegalMealException e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }


    
}
