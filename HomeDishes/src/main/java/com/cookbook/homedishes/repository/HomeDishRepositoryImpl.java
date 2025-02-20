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
            Criteria criteria = Criteria.where("diners");

            if(filter.getDiners().getLow() != null){
                criteria = criteria.gte(filter.getDiners().getLow());
            }
            if(filter.getDiners().getHigh() != null){
                criteria = criteria.lte(filter.getDiners().getHigh());
            }

            query.addCriteria(criteria);
        }

        if(filter.getIngredients() != null){
            query.addCriteria(Criteria.where("ingredients").all(filter.getIngredients()));
        }

        if(filter.getTimeInMinutes() != null){
            Criteria criteria = Criteria.where("timeInMinutes");

            if(filter.getTimeInMinutes().getLow() != null){
                criteria = criteria.gte(filter.getTimeInMinutes().getLow());
            }
            if(filter.getTimeInMinutes().getHigh() != null){
                criteria = criteria.lte(filter.getTimeInMinutes().getHigh());
            }

            query.addCriteria(criteria);
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
            Criteria criteria = Criteria.where("rating");

            if(filter.getRating().getLow() != null){
                criteria = criteria.gte(filter.getRating().getLow());
            }
            if(filter.getRating().getHigh() != null){
                criteria = criteria.lte(filter.getRating().getHigh());
            }

            query.addCriteria(criteria);
        }

        query.fields().include("id", "name");

        dishes = template.find(query, HomeDish.class);

        return dishes;
    }

    


}
