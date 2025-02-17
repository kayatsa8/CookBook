package com.cookbook.meals.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import com.cookbook.meals.model.Meal;
import com.cookbook.meals.model.filter.Filter;

public class MealRepositoryImpl implements QueryRepository{
    @Autowired
    private MongoTemplate template;


    @Override
    public List<Meal> findByFilter(Filter filter) {
        List<Meal> meals;
        Query query = new Query();

        if(filter.getName() != null){
            query.addCriteria(Criteria.where("name").regex(filter.getName(), "i"));
        }

        if(filter.getDiners() != null){
            Criteria criteria = Criteria.where("diners");

            if(filter.getDiners().getLow() != null){
                criteria = criteria.gte(filter.getDiners().getLow());
            }
            if(filter.getDiners().getHigh() != null){
                criteria = criteria.lte(filter.getDiners().getHigh());
            }

            query.addCriteria(criteria);
        }

        if(filter.getHomeDishesIds() != null){
            query.addCriteria(Criteria.where("homeDishesIds").all(filter.getHomeDishesIds()));
        }

        if(filter.getDeliveryDishesIds() != null){
            query.addCriteria(Criteria.where("deliveryDishesIds").all(filter.getDeliveryDishesIds()));
        }

        if(filter.getToBuy() != null){
            query.addCriteria(Criteria.where("toBuy").all(filter.getToBuy()));
        }

        if(filter.getMealTime() != null){
            query.addCriteria(Criteria.where("mealTime").all(filter.getMealTime()));
        }

        if(filter.getUserRating() != null){
            Criteria criteria = Criteria.where("userRating");

            if(filter.getUserRating().getLow() != null){
                criteria = criteria.gte(filter.getUserRating().getLow());
            }
            if(filter.getUserRating().getHigh() != null){
                criteria = criteria.lte(filter.getUserRating().getHigh());
            }

            query.addCriteria(criteria);
        }

        if(filter.getSpecialOccations() != null){
            query.addCriteria(Criteria.where("specialOccations").all(filter.getSpecialOccations()));
        }

        meals = template.find(query, Meal.class);

        return meals;
    }
    
}
