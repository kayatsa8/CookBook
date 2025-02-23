package com.cookbook.homedishes.controller;

import java.util.List;
import java.util.Map;

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

import com.cookbook.homedishes.exception.DishNotFoundException;
import com.cookbook.homedishes.exception.IllegalFilterException;
import com.cookbook.homedishes.model.HomeDish;
import com.cookbook.homedishes.model.filter.Filter;
import com.cookbook.homedishes.service.HomeDishService;

@RestController
@RequestMapping("/api/home_dish")
public class HomeDishController {
    private HomeDishService service;


    @Autowired
    public HomeDishController(HomeDishService service){
        this.service = service;
    }


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
    @DeleteMapping("/delete_dish/{id}")
    public void deleteDish(@PathVariable String id){
        try{
            service.deleteDish(id);
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
    @GetMapping("/all/id_name")
    public Map<String, String> getDishIdsAndNames(){
        return service.getDishIdsAndNames();
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/dish/{id}")
    public HomeDish getDish(@PathVariable String id){
        try{
            HomeDish dish = service.getDish(id);
            return dish;
        }
        catch(Exception e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @ResponseStatus(HttpStatus.OK)
    @PatchMapping("/update/{id}")
    public void updateDish(@PathVariable String id, @RequestBody HomeDish updated){
        try{
            service.updateDish(id, updated);
        }
        catch(Exception e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/filter")
    public Map<String, String> getByFilter(@RequestBody Filter filter){
        try{
            Map<String, String> ids_names = service.getByFilter(filter);
            return ids_names;
        }
        catch(IllegalFilterException e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/random")
    public HomeDish getRandomDish(){
        try{
            HomeDish dish = service.getRandomDish();
            return dish;
        }
        catch(DishNotFoundException e){
            throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE, e.getMessage());
        }
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/random/filter")
    public Map<String, String> getRandomFilteredDish(@RequestBody Filter filter){
        try{
            Map<String, String> id_name = service.getRandomWithFilter(filter);
            return id_name;
        }
        catch(IllegalFilterException | DishNotFoundException e){
            throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE, e.getMessage());
        }
    }


}









