package com.cookbook.homedishes.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cookbook.homedishes.exception.DishExistsException;
import com.cookbook.homedishes.exception.DishNotFoundException;
import com.cookbook.homedishes.exception.IllegalDishException;
import com.cookbook.homedishes.exception.IllegalFilterException;
import com.cookbook.homedishes.model.HomeDish;
import com.cookbook.homedishes.model.filter.Filter;
import com.cookbook.homedishes.repository.HomeDishRepository;

@Service
public class HomeDishService {
    private HomeDishRepository repo;

    @Autowired
    public HomeDishService(HomeDishRepository repository){
        this.repo = repository;
    }



    public void addDish(HomeDish dish) throws DishExistsException, IllegalDishException{        
        if(isDishExists(dish.getName())){
            throw new DishExistsException(dish.getName());
        }

        validateDishData(dish);

        repo.insert(dish);
    }

    public void deleteDish(String id) throws IllegalDishException{
        if(!isDishExists(id)){
            throw new IllegalDishException("dish not found");
        }

        repo.deleteById(id);
    }

    public List<HomeDish> getAll(){
        return repo.findAll();
    }

    // <id, name>
    public Map<String, String> getDishIdsAndNames(){
        List<HomeDish> dishes = repo.getAllDishIdsAndNames();
        Map<String, String> ids_names = new HashMap<>(dishes.size());

        for(HomeDish dish : dishes){
            ids_names.put(dish.getId(), dish.getName());
        }

        return ids_names;
    }
    
    public HomeDish getDish(String id) throws IllegalDishException{
        Optional<HomeDish> dish = repo.findById(id);

        if(dish.isPresent()){
            return dish.get();
        }
        else{
            throw new IllegalDishException("dish was not found");
        }
    }

    public void updateDish(String id, HomeDish updated) throws IllegalDishException{
        validateUpdateDish(updated);

        Optional<HomeDish> o = repo.findById(id);

        if(o.isEmpty()){
            throw new IllegalDishException("the dish does not exist");
        }

        HomeDish dish = o.get();
        dish.updateFromOther(updated);

        repo.save(dish);
    }

    private void validateUpdateDish(HomeDish updated) throws IllegalDishException{
        if(updated.getRating() != null && (updated.getRating() > 5 || updated.getRating() < 0)){
            throw new IllegalDishException("the rating of a dish must be between 0 to 5");
        }

        if(updated.getDiners() != null && updated.getDiners() < 0){
            throw new IllegalDishException("the number of diners must be a non-negative number");
        }

        if(updated.getTimeInMinutes() != null && updated.getTimeInMinutes() < 0){
            throw new IllegalDishException("the time must be a non-negative number");
        }
    }

    public boolean isDishExists(String id){
        return repo.existsById(id);
    }

    private void validateDishData(HomeDish dish) throws IllegalDishException{
        if(dish.getName() == null || dish.getName().isEmpty()){
            throw new IllegalDishException("the dish name is empty");
        }

        if(dish.getDiners() == null || dish.getDiners() < 0){
            throw new IllegalDishException("the number of diners must be specified and non-negative");
        }
        
        if(dish.getRecipe() == null){
            throw new IllegalDishException("the recipe cannot be null");
        }

        if(dish.getIngredients() == null){
            throw new IllegalDishException("the ingredients cannot be null");
        }

        if(dish.getTimeInMinutes() == null || dish.getTimeInMinutes() < 0){
            throw new IllegalDishException("the time cannot be negative and must be specified");
        }

        if(dish.getType() == null){
            throw new IllegalDishException("the type of the dish must be specified");
        }

        if(dish.getFlavors() == null){
            throw new IllegalDishException("the flavors list cannot be null");
        }

        if(dish.getDifficulty() == null){
            throw new IllegalDishException("the difficulty must be specified");
        }

        if(dish.getMealPart() == null){
            throw new IllegalDishException("the meal part must be specified");
        }

        if(dish.getRating() == null || (dish.getRating() < 0 || dish.getRating() > 5)){
            throw new IllegalDishException("the rating should be between 0 to 5 and must be specified");
        }
    }

    public Map<String, String> getByFilter(Filter filter) throws IllegalFilterException{
        if(filter == null){
            throw new IllegalFilterException("filter cannot be null");
        }

        Map<String, String> id_name = new HashMap<>();

        List<HomeDish> dishes = repo.getByFilter(filter);

        for(HomeDish dish : dishes){
            id_name.put(dish.getId(), dish.getName());
        }

        return id_name;
    }
    
    public HomeDish getRandomDish() throws DishNotFoundException{
        List<HomeDish> ids = repo.getAllIds();

        if(ids.isEmpty()){
            throw new DishNotFoundException();
        }

        Random rand = new Random();
        int index = rand.nextInt(ids.size());

        Optional<HomeDish> oDish = repo.findById(ids.get(index).getId());

        if(oDish.isEmpty()){
            throw new DishNotFoundException();
        }

        HomeDish dish = oDish.get();

        return dish;
    }

    public Map<String, String> getRandomWithFilter(Filter filter) throws IllegalFilterException, DishNotFoundException{
        if(filter == null){
            throw new IllegalFilterException("filter cannot be null");
        }

        Map<String, String> ids_names = getByFilter(filter);

        if(ids_names.isEmpty()){
            throw new DishNotFoundException();
        }

        List<String> ids = new ArrayList<>(ids_names.keySet());

        Random rand = new Random();
        int index = rand.nextInt(ids.size());

        String id = ids.get(index);

        return Map.of(id, ids_names.get(id));
    }

}
