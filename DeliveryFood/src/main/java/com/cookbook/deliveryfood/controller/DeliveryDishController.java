package com.cookbook.deliveryfood.controller;

import java.util.List;
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

import com.cookbook.deliveryfood.model.DeliveryDish;
import com.cookbook.deliveryfood.exception.DishNotFoundException;
import com.cookbook.deliveryfood.exception.InvalidDishException;
import com.cookbook.deliveryfood.exception.NoDishesException;
import com.cookbook.deliveryfood.model.filter.Filter;
import com.cookbook.deliveryfood.service.DeliveryDishService;


@RestController
@RequestMapping("api/delivery_dish")
public class DeliveryDishController {
    private DeliveryDishService service;

    @Autowired
    public DeliveryDishController(DeliveryDishService service){
        this.service = service;
    }



    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/")
    public String home(){
        return "hi delivery dishes";
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

    @ResponseStatus(HttpStatus.FOUND)
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

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/all/id_name")
    public Map<Integer, String> getIdsAndNames(){
        return service.getIdsAndNames();
    }
    
    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping("/delete/{id}")
    public void deleteDish(@PathVariable int id){
        try{
            service.deleteDish(id);
        }
        catch(DishNotFoundException e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @ResponseStatus(HttpStatus.OK)
    @PatchMapping("/update")
    public void updateDish(@RequestBody DeliveryDish dish){
        try{
            service.updateDish(dish);
        }
        catch(InvalidDishException e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/get/filter")
    public Map<Integer, String> getByFilter(@RequestBody Filter filter){
        return service.getByFilter(filter);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/get/random")
    public DeliveryDish getRandomDish(){
        try{
            DeliveryDish dish = service.getRandomDish();
            return dish;
        }
        catch(NoDishesException e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/get/random/filter")
    public DeliveryDish getRandomFiltered(@RequestBody Filter filter){
        try{
            DeliveryDish dish = service.getRandomFiltered(filter);
            return dish;
        }
        catch(NoDishesException | DishNotFoundException e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    
    
}
