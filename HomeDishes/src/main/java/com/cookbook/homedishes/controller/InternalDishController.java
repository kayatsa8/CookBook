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

import com.cookbook.homedishes.exception.IllegalDishException;
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
            Set<DishType> types = service.getDisheTypes(ids);
            return types;
        }
        catch(IllegalDishException e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

}
