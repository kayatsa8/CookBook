package com.cookbook.homedishes.controller;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.cookbook.homedishes.exception.DishNotFoundException;
import com.cookbook.homedishes.exception.IllegalDishException;
import com.cookbook.homedishes.model.enums.Difficulty;
import com.cookbook.homedishes.model.enums.DishType;
import com.cookbook.homedishes.service.InternalDishService;

@RestController
@RequestMapping("/internal")
public class InternalDishController {
    private final InternalDishService service;
    
    @Autowired
    public InternalDishController(InternalDishService service){
        this.service = service;
    }

    @ResponseStatus(HttpStatus.FOUND)
    @GetMapping("/check_dish/{id}")
    public boolean isDishExists(@PathVariable String id){
        return service.isDishExists(id);
    }

    @ResponseStatus(HttpStatus.FOUND)
    @PostMapping("/check_dish_list")
    public List<String> dishesExists(@RequestBody List<String> ids){
        return service.dishesExists(ids);
    }

    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/types")
    public Set<DishType> getDishesTypes(@RequestBody List<String> ids){
        try{
            Set<DishType> types = service.getDishesTypes(ids);
            return types;
        }
        catch(IllegalDishException e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/difficulty")
    public Difficulty getMaxDishDifficulty(@RequestBody List<String> ids){
        try{
            Difficulty max = service.getMaxDishDifficulty(ids);
            return max;
        }
        catch(IllegalDishException e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/rating_sum")
    public int getRatingSum(@RequestBody List<String> ids){
        try{
            int sum = service.getRatingSum(ids);
            return sum;
        }
        catch(IllegalDishException e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/names")
    public List<String> getDishNames(@RequestBody List<String> ids){
        try{
            List<String> names = service.getDishNames(ids);
            return names;
        }
        catch(DishNotFoundException e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/ingredients")
    public List<String> getDishesIngredients(@RequestBody List<String> ids){
        try{
            List<String> ingredients = service.getDishesIngredients(ids);
            return ingredients;
        }
        catch(DishNotFoundException e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

}
