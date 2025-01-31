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
import org.springframework.web.server.ResponseStatusException;

import com.cookbook.deliveryfood.model.DeliveryDish;
import com.cookbook.deliveryfood.model.exception.DishNotFoundException;
import com.cookbook.deliveryfood.model.exception.InvalidDishException;
import com.cookbook.deliveryfood.service.DeliveryDishService;

import jakarta.websocket.server.PathParam;

@RestController
@RequestMapping("api/delivery_dish")
public class DeliveryDishController {

    @Autowired
    private DeliveryDishService service;



    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/")
    public String home(){
        return "hi home dishes";
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/add")
    public void addDeliveryDish(@RequestBody DeliveryDish dish){
        try{
            service.addDish(dish);
        }
        catch(InvalidDishException e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/get/{id}")
    public DeliveryDish getDish(@PathVariable int id){
        try{
            DeliveryDish dish = service.getDish(id);
            return dish;
        }
        catch(DishNotFoundException e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/get/all")
    public List<DeliveryDish> getAllDishes(){
        return service.getAllDishes();
    }





    
    
}
