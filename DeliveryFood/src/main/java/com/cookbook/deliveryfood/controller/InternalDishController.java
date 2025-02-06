package com.cookbook.deliveryfood.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.cookbook.deliveryfood.service.InternalDishService;

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
    public boolean isDishExists(@PathVariable int id){
        return service.isDishExists(id);
    }

    @ResponseStatus(HttpStatus.FOUND)
    @PostMapping("/check_dish_list")
    public List<Integer> dishesExist(@RequestBody List<Integer> ids){
        return service.checkDishList(ids);
    }



    
}
