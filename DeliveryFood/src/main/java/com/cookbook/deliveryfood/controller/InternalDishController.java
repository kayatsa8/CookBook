package com.cookbook.deliveryfood.controller;

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

import com.cookbook.deliveryfood.model.enums.DishType;
import com.cookbook.deliveryfood.model.enums.Flavors;
import com.cookbook.deliveryfood.exception.DishNotFoundException;
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

    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/types")
    public Set<DishType> getDishTypes(@RequestBody List<Integer> ids){
        try{
            Set<DishType> types = service.getTypes(ids);
            return types;
        }
        catch(DishNotFoundException e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/rating_sum")
    public int getRatingSum(@RequestBody List<Integer> ids){
        try{
            int sum = service.getRatingSum(ids);

            return sum;
        }
        catch(DishNotFoundException e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/names")
    public List<String> getDishNames(@RequestBody List<Integer> ids){
        try{
            List<String> names = service.getDishNames(ids);
            return names;
        }
        catch(DishNotFoundException e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/flavors")
    public List<Flavors> getDishesFlavors(@RequestBody List<Integer> ids){
        try{
            List<Flavors> flavros = service.getDishesFlavors(ids);
            return flavros;
        }
        catch(DishNotFoundException e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    
}
