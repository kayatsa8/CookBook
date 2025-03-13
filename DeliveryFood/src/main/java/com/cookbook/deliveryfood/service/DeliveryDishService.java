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
        logger.info("trying to add a dish");

        validateAddDish(dish);

        
        repo.save(dish);
        
        logger.info("a dish was added successfully");
    }

    public DeliveryDish getDish(int id) throws DishNotFoundException{
        logger.info("searching for a dish with id {}", id);

        Optional<DeliveryDish> oDish = repo.findById(id);

        if(oDish.isEmpty()){
            logger.warn("a dish with id {} was not found", id);

            throw new DishNotFoundException();
        }

        logger.info("returning the dish with id {}", id);
        return oDish.get();
    }

    public List<DeliveryDish> getAllDishes(){
        logger.info("fetching all dishes");

        List<DeliveryDish> dishes = new ArrayList<>();
        Iterable<DeliveryDish> iterable = repo.findAll();

        iterable.forEach(dishes::add);

        logger.info("returning dishes");

        return dishes;
    }

    public Map<Integer, String> getIdsAndNames(){
        logger.info("fetching all dishes for ids and names");

        List<DTO> dtos = repo.getIdsAndNames();
        Map<Integer, String> ids_names = new HashMap<>();

        for(DTO dto : dtos){
            ids_names.put(dto.getId(), dto.getName());
        }

        logger.info("returning ids and names");

        return ids_names;
    }

    public void deleteDish(int id) throws DishNotFoundException{
        logger.info("tring to delete a dish with id {}", id);

        if(!repo.existsById(id)){
            logger.warn("no dish with id {}", id);

            throw new DishNotFoundException();
        }

        logger.info("the dish with id {} was deleted successfully", id);

        repo.deleteById(id);
    }

    public void updateDish(DeliveryDish updated) throws InvalidDishException{
        logger.info("trying to update a dish");

        validateUpdateDish(updated);

        Optional<DeliveryDish> oDish = repo.findById(updated.getId());

        if(oDish.isEmpty()){
            logger.warn("a dish with id {} was not found", updated.getId());

            throw new InvalidDishException("no such dish");
        }

        DeliveryDish dish = oDish.get();

        logger.info("updating the dish with id {}", updated.getId());
        dish.update(updated);

        repo.save(dish);
        logger.info("DeliveryDishService::updateDish: the dish with id {} was updated successfully", updated.getId());
    }

    public Map<Integer, String> getByFilter(Filter filter){
        logger.info("trying to fetch dishes by filter");

        List<DeliveryDish> dishes =  repo.getByFilter(filter);
        Map<Integer, String> ids_names = new HashMap<>(dishes.size());

        logger.info("fetched dishes by filter");

        for(DeliveryDish dish : dishes){
            ids_names.put(dish.getId(), dish.getName());
        }

        logger.info("returning dishes fetched by filter");

        return ids_names;
    }

    public DeliveryDish getRandomDish() throws NoDishesException {
        logger.info("trying to get a random dish");

        logger.info("fetching dishes ids");
        List<Integer> ids = repo.getIds();
        logger.info("dishes ids fetched");

        if(ids.size() == 0){
            logger.warn("no dishes in the system");

            throw new NoDishesException();
        }

        Random r = new Random();
        int index = r.nextInt(ids.size());

        logger.info("returning a dish with id {}", ids.get(index));

        return repo.findById(ids.get(index)).get();
    }

    public DeliveryDish getRandomFiltered(Filter filter) throws NoDishesException, DishNotFoundException {
        logger.info("trying to fetch a dish");

        logger.info("fetching dishes by filter");
        Map<Integer, String> dishes = getByFilter(filter);
        logger.info("dishes were fetched according to filter");

        if(dishes.isEmpty()){
            logger.warn("no dishes fitting to the filter were found");

            throw new NoDishesException();
        }

        Random r = new Random();
        List<Integer> ids = new ArrayList<>(dishes.keySet());

        int index = r.nextInt(ids.size());
        DeliveryDish dish = getDish(ids.get(index));

        logger.info("returning the dish with id {}", ids.get(index));
        
        return dish;
    }





    private void validateAddDish(DeliveryDish dish) throws InvalidDishException{
        logger.info("validating addDish");

        if(dish.getId() != null){
            logger.error("the given dish has the id + " + dish.getId());

            throw new InvalidDishException("the dish has an id");
        }

        if(dish.getName() == null || dish.getName().isBlank()){
            logger.error("name was not provided");

            throw new InvalidDishException("name not provided");
        }

        if(dish.getPrice() == null || dish.getPrice() < 0){
            logger.error("empty or negative price");

            throw new InvalidDishException("empty or negative price");
        }

        if(dish.getRestaurant() == null || dish.getRestaurant().isBlank()){
            logger.error("restaurant was not provided");

            throw new InvalidDishException("restaurant not provided");
        }

        if(dish.getDeliveryPlatform() == null || dish.getDeliveryPlatform().isBlank()){
            logger.error("delivery platform was not provided");

            throw new InvalidDishException("delivery platform not provided");
        }

        if(dish.getRating() == null || dish.getRating() < 0 || dish.getRating() > 5){
            logger.error("invalid rating - " + dish.getRating());

            throw new InvalidDishException("invalid rating");
        }

        if(dish.getType() == null){
            logger.error("dish type was not provided");

            throw new InvalidDishException("dish type not provided");
        }

        if(dish.getFlavors() == null){
            logger.error("flavors were not provided");

            throw new InvalidDishException("flavors not provided");
        }

        if(dish.getMealPart() == null){
            logger.error("meal part was not provided");

            throw new InvalidDishException("meal part not provided");
        }

        logger.info("the dish was validated successfully");
    }

    private void validateUpdateDish(DeliveryDish updated) throws InvalidDishException{
        logger.info("validating updateDish");

        if(updated.getId() == null){
            logger.error("no id was provided");

            throw new InvalidDishException("no id");
        }

        if(updated.getName() != null && updated.getName().isBlank()){
            logger.error("invalid name");

            throw new InvalidDishException("invalid name");
        }

        if(updated.getRestaurant() != null && updated.getRestaurant().isBlank()){
            logger.error("invalid restaurant");

            throw new InvalidDishException("invalid restaurant");
        }

        if(updated.getDeliveryPlatform() != null && updated.getDeliveryPlatform().isBlank()){
            logger.error("invalid deliveryplatform");

            throw new InvalidDishException("invalid delivery platform");
        }

        if(updated.getRating() != null && (updated.getRating() < 0 || updated.getRating() > 5)){
            logger.error("invalid rating - " + updated.getRating());

            throw new InvalidDishException("invalid rating");
        }

        logger.info("the dish was validated");
    }


}
