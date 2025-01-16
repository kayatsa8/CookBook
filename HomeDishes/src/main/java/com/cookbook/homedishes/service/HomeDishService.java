package com.cookbook.homedishes.service;

import java.util.List;
import java.util.Optional;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cookbook.homedishes.exception.DishExistsException;
import com.cookbook.homedishes.exception.IlligalDishException;
import com.cookbook.homedishes.model.HomeDish;
import com.cookbook.homedishes.model.filter.Filter;
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

    public void deleteDish(String name) throws IlligalDishException{
        if(!isDishExists(name)){
            throw new IlligalDishException("dish \'" + name + "\' not found");
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
            throw new IlligalDishException("no dish named \'" + name + "\'");
        }
    }

    public void updateDish(String originalName, HomeDish updated) throws IlligalDishException{
        if(updated.getRating() > 5 || updated.getRating() < 0){
            throw new IlligalDishException("the rating of a dish must be between 0 to 5");
        }

        Optional<HomeDish> o = repo.findById(originalName);

        if(o.isEmpty()){
            throw new IlligalDishException("the dish \'" + originalName + "\' does not exist");
        }

        HomeDish dish = o.get();
        dish.updateFromOther(updated);

        if(originalName.equals(dish.getName())){
            repo.save(dish);
        }
        else{
            repo.deleteById(originalName);
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

        if(dish.getRecipe() == null){
            throw new IlligalDishException("the recipe cannot be null");
        }

        if(dish.getIngredients() == null){
            throw new IlligalDishException("the ingredients cannot be null");
        }

        if(dish.getTimeInMinutes() < 0){
            throw new IlligalDishException("the time cannot be negative");
        }

        if(dish.getType() == null){
            throw new IlligalDishException("the type of the dish must be specified");
        }

        if(dish.getFlavors() == null){
            throw new IlligalDishException("the flavors list cannot be null");
        }

        if(dish.getRating() < 0 || dish.getRating() > 5){
            throw new IlligalDishException("the rating should be between 0 to 5");
        }
    }

    public List<HomeDish> getByFilter(Filter filter){
        return repo.getByFilter(filter);
    }
    
    public HomeDish getRandomDish() throws Exception{
        List<HomeDish> dishNames = repo.getAllNames();

        if(dishNames.isEmpty()){
            throw new Exception("no dishes in system");
        }

        Random rand = new Random();
        int index = rand.nextInt(dishNames.size());

        Optional<HomeDish> oDish = repo.findById(dishNames.get(index).getName());

        if(oDish.isEmpty()){
            throw new Exception("cannot get random dish");
        }

        return oDish.get();
    }

    public HomeDish getRandomWithFilter(Filter filter) throws Exception{
        List<HomeDish> dishes = getByFilter(filter);

        if(dishes.isEmpty()){
            throw new Exception("no dishes in system");
        }

        Random rand = new Random();
        int index = rand.nextInt(dishes.size());


        return dishes.get(index);
    }

}
