package com.cookbook.homedishes.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cookbook.homedishes.exception.DishExistsException;
import com.cookbook.homedishes.exception.IlligalDishException;
import com.cookbook.homedishes.model.HomeDish;
import com.cookbook.homedishes.model.dto.DTO;
import com.cookbook.homedishes.repository.HomeDishRepository;

@Service
public class HomeDishService {

    @Autowired
    private HomeDishRepository repo;



    public void addDish(HomeDish dish) throws DishExistsException, IlligalDishException{        
        if(isDishExists(dish.getName())){
            throw new DishExistsException(dish.getName());
        }

        validateDishData(dish);

        repo.insert(dish);
    }

    public void delteDish(String name) throws IlligalDishException{
        if(!isDishExists(name)){
            throw new IlligalDishException("dish \"" + name + "\" not found");
        }

        repo.deleteById(name);
    }

    public List<HomeDish> getAll(){
        return repo.findAll();
    }

    public HomeDish getDish(String name) throws IlligalDishException{
        Optional<HomeDish> dish = repo.findById(name);

        if(dish.isPresent()){
            return dish.get();
        }
        else{
            throw new IlligalDishException("no dish named \"" + name + "\"");
        }
    }

    public void updateDish(String name, DTO dto) throws IlligalDishException{
        Optional<HomeDish> o = repo.findById(name);

        if(o.isEmpty()){
            throw new IlligalDishException("the dish \"" + name + "\" does not exist");
        }

        HomeDish dish = o.get();
        dish.updateFromDTO(dto);

        if(name.equals(dish.getName())){
            repo.save(dish);
        }
        else{
            repo.deleteById(name);
            repo.insert(dish);
        }
    }

    private boolean isDishExists(String name){
        return repo.existsById(name);
    }

    private void validateDishData(HomeDish dish) throws IlligalDishException{
        if(dish.getName() == null || dish.getName().isEmpty()){
            throw new IlligalDishException("the dish name is empty");
        }

        if(dish.getType() == null){
            throw new IlligalDishException("the type of the dish must be specified");
        }
    }

    
}
