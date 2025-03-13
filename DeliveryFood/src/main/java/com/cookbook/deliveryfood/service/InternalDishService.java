package com.cookbook.deliveryfood.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cookbook.deliveryfood.model.enums.DishType;
import com.cookbook.deliveryfood.model.enums.Flavors;
import com.cookbook.deliveryfood.exception.DishNotFoundException;
import com.cookbook.deliveryfood.repository.DeliveryDishRepository;

@Service
public class InternalDishService {
    private final DeliveryDishRepository repo;
    private Logger logger = LoggerFactory.getLogger(getClass());


    @Autowired
    public InternalDishService(DeliveryDishRepository repository){
        this.repo = repository;
    }

    public boolean isDishExists(int id){
        return repo.existsById(id);
    }

    public List<Integer> checkDishList(List<Integer> ids){
        logger.info("InternalDishService::checkDishList: checking existence of dishes by id");

        List<Integer> badIds = new ArrayList<>();

        for(int id : ids){
            if(!repo.existsById(id)){
                badIds.add(id);
            }
        }

        return badIds;
    }

    public Set<DishType> getTypes(List<Integer> ids) throws DishNotFoundException{
        logger.info("InternalDishService::getTypes: collecting dishes types");

        Set<DishType> types = new HashSet<>();
        DishType type;

        if(ids == null){
            logger.warn("InternalDishService::getTypes: the ids list is null");
            return types;
        }

        for(int id : ids){
            if(!repo.existsById(id)){
                logger.warn("InternalDishService::getTypes: a dish with id {} was not found", id);

                throw new DishNotFoundException();
            }

            type = repo.getDishType(id);
            types.add(type);
        }

        logger.info("InternalDishService::getTypes: returning types");

        return types;
    }

    public int getRatingSum(List<Integer> ids) throws DishNotFoundException{
        logger.info("InternalDishService::getRatingSum: summing ratings");

        int sum = 0;
        int rating;

        if(ids == null){
            return 0;
        }

        for(int id : ids){
            if(!repo.existsById(id)){
                logger.warn("InternalDishService::getRatingSum: a dish with id {id} was not found", id);

                throw new DishNotFoundException();
            }

            rating = repo.getDishRating(id);
            sum += rating;
        }

        logger.info("InternalDishService::getRatingSum: returning the sum of ratings");

        return sum;
    }

    public List<String> getDishNames(List<Integer> ids) throws DishNotFoundException{
        List<String> names = new ArrayList<>(ids.size());
        String name;

        for(int id : ids){
            if(!repo.existsById(id)){
                throw new DishNotFoundException();
            }

            name = repo.getDishName(id);
            names.add(name);
        }

        return names;
    }
    
    public List<Flavors> getDishesFlavors(List<Integer> ids) throws DishNotFoundException{
        Set<Flavors> flavors = new HashSet<>();
        List<Flavors> f;

        for(int id : ids){
            if(!repo.existsById(id)){
                throw new DishNotFoundException();
            }

            f = repo.getDishFlavors(id).get(0);
            flavors.addAll(f);
        }

        return new ArrayList<>(flavors);
    }


}
