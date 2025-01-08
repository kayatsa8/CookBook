package com.cookbook.homedishes.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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
import org.springframework.http.HttpStatus;

import com.cookbook.homedishes.model.HomeDish;
import com.cookbook.homedishes.service.HomeDishService;

@RestController
@RequestMapping("/api/home_dish")
public class HomeDishController {
    
    @Autowired
    private HomeDishService service;


    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/")
    public String home(){
        return "hi home dishes";
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/add_dish")
    public void addDish(@RequestBody HomeDish dish){
        try{
            service.addDish(dish);
        }
        catch(Exception e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping("/delete_dish/{dishName}")
    public void deleteDish(@PathVariable String dishName){
        try{
            service.deleteDish(dishName);
        }
        catch(Exception e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/all")
    public List<HomeDish> getAll(){
        return service.getAll();
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/dish/{dishName}")
    public HomeDish getDish(@PathVariable String dishName){
        try{
            HomeDish dish = service.getDish(dishName);
            return dish;
        }
        catch(Exception e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @ResponseStatus(HttpStatus.OK)
    @PatchMapping("/update/{originalName}")
    public void updateDish(@PathVariable String originalName, @RequestBody HomeDish updated){
        // TODO: validate the data
        try{
            service.updateDish(originalName, updated);
        }
        catch(Exception e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }




}









