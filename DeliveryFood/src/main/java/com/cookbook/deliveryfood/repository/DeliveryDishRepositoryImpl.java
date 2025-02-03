package com.cookbook.deliveryfood.repository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.cookbook.deliveryfood.model.DeliveryDish;
import com.cookbook.deliveryfood.model.enums.Flavors;
import com.cookbook.deliveryfood.model.filter.Filter;
import com.cookbook.deliveryfood.model.filter.Range;

import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

public class DeliveryDishRepositoryImpl implements QueryRepository{

    @Autowired
    private EntityManager em;


    @Override
    public List<DeliveryDish> getByFilter(Filter filter) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<DeliveryDish> query = cb.createQuery(DeliveryDish.class);        
        Root<DeliveryDish> dish = query.from(DeliveryDish.class);
        
        List<Predicate> predicates = new ArrayList<>();


        if(filter.getName() != null){
            predicates.add(cb.like(dish.get("name"), "%" + filter.getName() + "%"));
        }

        if(filter.getPrice() != null){
            Range price = filter.getPrice();

            if(price.getLow() != null && price.getHigh() != null){
                predicates.add(cb.between(dish.get("price"), price.getLow(), price.getHigh()));
            }
            else if(price.getLow() != null){
                predicates.add(cb.ge(dish.get("price"), price.getLow()));
            }
            else if(price.getHigh() != null){
                predicates.add(cb.le(dish.get("price"), price.getHigh()));
            }
        }

        if(filter.getRestaurant() != null){
            predicates.add(cb.like(dish.get("restaurant"), "%" + filter.getRestaurant() + "%"));
        }

        if(filter.getDeliveryPlatform() != null){
            predicates.add(cb.like(dish.get("deliveryPlatform"), "%" + filter.getDeliveryPlatform() + "%"));
        }

        if(filter.getRating() != null){
            Range rating = filter.getRating();

            if(rating.getLow() != null && rating.getHigh() != null){
                predicates.add(cb.between(dish.get("rating"), rating.getLow(), rating.getHigh()));
            }
            else if(rating.getLow() != null){
                predicates.add(cb.ge(dish.get("rating"), rating.getLow()));
            }
            else if(rating.getHigh() != null){
                predicates.add(cb.le(dish.get("rating"), rating.getHigh()));
            }
        }

        if(filter.getType() != null){
            predicates.add(cb.equal(dish.get("type"), filter.getType()));
        }

        if(filter.getFlavors() != null && !filter.getFlavors().isEmpty()){
            predicates.add(cb.isNotNull(dish.get("flavors")));
        }

        if(filter.getMealPart() != null){
            predicates.add(cb.equal(dish.get("mealPart"), filter.getMealPart()));
        }


        query.where(predicates.toArray(new Predicate[0]));

        List<DeliveryDish> dishes = em.createQuery(query).getResultList();
        
        if(filter.getFlavors() != null && !filter.getFlavors().isEmpty()){
            dishes = filterFlavors(dishes, filter.getFlavors());
        }

        return dishes;
    }

    private List<DeliveryDish> filterFlavors(List<DeliveryDish> all, List<Flavors> filter){
        List<DeliveryDish> dishes = new ArrayList<>();
        List<Flavors> flavors;

        for(DeliveryDish dish : all){
            flavors = dish.getFlavors();
            
            if(flavors.containsAll(filter)){
                dishes.add(dish);
            }
            
        }

        return dishes;
    }
    
}
