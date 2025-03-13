package com.cookbook.deliveryfood.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cookbook.deliveryfood.model.DTO;
import com.cookbook.deliveryfood.model.DeliveryDish;
import com.cookbook.deliveryfood.exception.DishNotFoundException;
import com.cookbook.deliveryfood.exception.InvalidDishException;
import com.cookbook.deliveryfood.exception.NoDishesException;
import com.cookbook.deliveryfood.model.filter.Filter;
import com.cookbook.deliveryfood.repository.DeliveryDishRepository;

@Service
public class DeliveryDishService {
    private DeliveryDishRepository repo;
    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    public DeliveryDishService(DeliveryDishRepository repository){
        this.repo = repository;
    }



    public void addDish(DeliveryDish dish) throws InvalidDishException{
        logger.info("DeliveryDishService::addDish: trying to add a dish");

        validateAddDish(dish);

        
        repo.save(dish);
        
        logger.info("DeliveryDishService::addDish: a dish was added successfully");
    }

    public DeliveryDish getDish(int id) throws DishNotFoundException{
        logger.info("DeliveryDishService::getDish: searching for a dish with id " + id);

        Optional<DeliveryDish> oDish = repo.findById(id);

        if(oDish.isEmpty()){
            logger.warn("DeliveryDishService::getDish: a dish with id " + id + " was not found");

            throw new DishNotFoundException();
        }

        logger.info("DeliveryDishService::getDish: returning the dish with id " + id);
        return oDish.get();
    }

    public List<DeliveryDish> getAllDishes(){
        logger.info("DeliveryDishService::getAllDishes: fetching all dishes");

        List<DeliveryDish> dishes = new ArrayList<>();
        Iterable<DeliveryDish> iterable = repo.findAll();

        iterable.forEach(dishes::add);

        logger.info("DeliveryDishService::getAllDishes: returning dishes");

        return dishes;
    }

    public Map<Integer, String> getIdsAndNames(){
        logger.info("DeliveryDishService::getIdsAndNames: fetching all dishes");

        List<DTO> dtos = repo.getIdsAndNames();
        Map<Integer, String> ids_names = new HashMap<>();

        for(DTO dto : dtos){
            ids_names.put(dto.getId(), dto.getName());
        }

        logger.info("DeliveryDishService::getIdsAndNames: returning ids and names");

        return ids_names;
    }

    public void deleteDish(int id) throws DishNotFoundException{
        logger.info("DeliveryDishService::deleteDish:: tring to delete a dish with id " + id);

        if(!repo.existsById(id)){
            logger.warn("DeliveryDishService::deleteDish:: no dish with id " + id);

            throw new DishNotFoundException();
        }

        logger.info("DeliveryDishService::deleteDish:: the dish with id " + id + " was deleted successfully");

        repo.deleteById(id);
    }

    public void updateDish(DeliveryDish updated) throws InvalidDishException{
        logger.info("DeliveryDishService::updateDish: trying to update a dish");

        validateupdateDish(updated);

        Optional<DeliveryDish> oDish = repo.findById(updated.getId());

        if(oDish.isEmpty()){
            logger.warn("DeliveryDishService::updateDish: a dish with id " + updated.getId() + " was not found");

            throw new InvalidDishException("no such dish");
        }

        DeliveryDish dish = oDish.get();

        logger.info("DeliveryDishService::updateDish: updating the dish with id " + updated.getId());
        dish.update(updated);

        repo.save(dish);
        logger.info("DeliveryDishService::updateDish: the dish with id " + updated.getId() + " was updated successfully");
    }

    public Map<Integer, String> getByFilter(Filter filter){
        logger.info("DeliveryDishService::getByFilter: trying to fetch dishes by filter");

        List<DeliveryDish> dishes =  repo.getByFilter(filter);
        Map<Integer, String> ids_names = new HashMap<>(dishes.size());

        logger.info("DeliveryDishService::getByFilter: fetched dishes by filter");

        for(DeliveryDish dish : dishes){
            ids_names.put(dish.getId(), dish.getName());
        }

        logger.info("DeliveryDishService::getByFilter: returning dishes fetched by filter");

        return ids_names;
    }

    public DeliveryDish getRandomDish() throws NoDishesException {
        List<Integer> ids = repo.getIds();

        if(ids.size() == 0){
            throw new NoDishesException();
        }

        Random r = new Random();
        int index = r.nextInt(ids.size());

        return repo.findById(ids.get(index)).get();
    }

    public DeliveryDish getRandomFiltered(Filter filter) throws NoDishesException, DishNotFoundException {
        Map<Integer, String> dishes = getByFilter(filter);

        if(dishes.isEmpty()){
            throw new NoDishesException();
        }

        Random r = new Random();
        List<Integer> ids = new ArrayList<>(dishes.keySet());

        int index = r.nextInt(ids.size());
        DeliveryDish dish = getDish(ids.get(index));

        return dish;
    }





    private void validateAddDish(DeliveryDish dish) throws InvalidDishException{
        if(dish.getId() != null){
            throw new InvalidDishException("the dish has an id");
        }

        if(dish.getName() == null || dish.getName().isBlank()){
            throw new InvalidDishException("name not provided");
        }

        if(dish.getPrice() == null || dish.getPrice() < 0){
            throw new InvalidDishException("empty or negative price");
        }

        if(dish.getRestaurant() == null || dish.getRestaurant().isBlank()){
            throw new InvalidDishException("restaurant not provided");
        }

        if(dish.getDeliveryPlatform() == null || dish.getDeliveryPlatform().isBlank()){
            throw new InvalidDishException("delivery platform not provided");
        }

        if(dish.getRating() == null || dish.getRating() < 0 || dish.getRating() > 5){
            throw new InvalidDishException("invalid rating");
        }

        if(dish.getType() == null){
            throw new InvalidDishException("dish type not provided");
        }

        if(dish.getFlavors() == null){
            throw new InvalidDishException("flavors not provided");
        }

        if(dish.getMealPart() == null){
            throw new InvalidDishException("meal part not provided");
        }

    }

    private void validateupdateDish(DeliveryDish updated) throws InvalidDishException{
        if(updated.getId() == null){
            throw new InvalidDishException("no id");
        }

        if(updated.getName() != null && updated.getName().isBlank()){
            throw new InvalidDishException("invalid name");
        }

        if(updated.getRestaurant() != null && updated.getRestaurant().isBlank()){
            throw new InvalidDishException("invalid restaurant");
        }

        if(updated.getDeliveryPlatform() != null && updated.getDeliveryPlatform().isBlank()){
            throw new InvalidDishException("invalid delivery platform");
        }

        if(updated.getRating() != null && (updated.getRating() < 0 || updated.getRating() > 5)){
            throw new InvalidDishException("invalid rating");
        }

    }


}
