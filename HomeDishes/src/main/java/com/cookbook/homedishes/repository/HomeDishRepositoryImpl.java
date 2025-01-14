package com.cookbook.homedishes.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import com.cookbook.homedishes.model.HomeDish;
import com.cookbook.homedishes.model.filter.Filter;

public class HomeDishRepositoryImpl implements TemplateRepository{
    @Autowired
    private MongoTemplate template;

    @Override
    public List<HomeDish> getByFilter(Filter filter) {
        List<HomeDish> dishes;
        Query query = new Query();

        if(filter.getNameKeyword() != null){
            query.addCriteria(Criteria.where("name").regex(filter.getNameKeyword(), "i"));
        }

        if(filter.getDiners() != null){
            if(filter.getDiners().getLow() != null){
                query.addCriteria(Criteria.where("diners").gte(filter.getDiners().getLow()));
            }
            if(filter.getDiners().getHigh() != null){
                query.addCriteria(Criteria.where("diners").lte(filter.getDiners().getHigh()));
            }
        }

        if(filter.getIngredients() != null){
            query.addCriteria(Criteria.where("ingredients").all(filter.getIngredients()));
        }

        if(filter.getTimeInMinutes() != null){
            if(filter.getTimeInMinutes().getLow() != null){
                query.addCriteria(Criteria.where("timeInMinutes").gte(filter.getTimeInMinutes().getLow()));
            }
            if(filter.getTimeInMinutes().getHigh() != null){
                query.addCriteria(Criteria.where("timeInMinutes").lte(filter.getTimeInMinutes().getHigh()));
            }
        }

        if(filter.getType() != null){
            query.addCriteria(Criteria.where("type").is(filter.getType()));
        }

        if(filter.getFlavors() != null){
            query.addCriteria(Criteria.where("flavors").all(filter.getFlavors()));
        }

        if(filter.getDifficulty() != null){
            query.addCriteria(Criteria.where("difficulty").is(filter.getDifficulty()));
        }

        if(filter.getMealPart() != null){
            query.addCriteria(Criteria.where("mealPart").is(filter.getMealPart()));
        }

        if(filter.getRating() != null){
            if(filter.getRating().getLow() != null){
                query.addCriteria(Criteria.where("rating").gte(filter.getRating().getLow()));
            }
            if(filter.getRating().getHigh() != null){
                query.addCriteria(Criteria.where("rating").lte(filter.getRating().getHigh()));
            }
        }


        dishes = template.find(query, HomeDish.class);

        return dishes;
    }

    


}
