package com.cookbook.homedishes.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.cookbook.homedishes.service.DishMealService;

@RestController
@RequestMapping("/home_meal")
public class DishMealController {
    private final DishMealService service;
    
    @Autowired
    public DishMealController(DishMealService service){
        this.service = service;
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/check_dish/{id}")
    public boolean isDishExists(String id){
        return service.isDishExists(id);
    }

    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/check_dish_list")
    public boolean dishesExists(@RequestBody List<String> ids){
        return service.dishesExists(ids);
    }

}
