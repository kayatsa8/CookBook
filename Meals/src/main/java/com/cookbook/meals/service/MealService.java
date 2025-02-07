package com.cookbook.meals.service;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.cookbook.meals.model.Meal;
import com.cookbook.meals.model.enums.MealType;
import com.cookbook.meals.model.exceptions.IllegalMealException;
import com.cookbook.meals.repository.MealRepository;

import io.netty.handler.timeout.TimeoutException;

@Service
public class MealService {
    private final MealRepository repo;
    private final WebClient webClient;


    @Autowired
    public MealService(MealRepository mealRepository, WebClient webClient){
        this.repo = mealRepository;
        this.webClient = webClient;
    }




    public void addMeal(Meal meal) throws IllegalMealException{
        validateAddMeal(meal);

        putTypesInMeal(meal);

        repo.insert(meal);
    }

    private void validateAddMeal(Meal meal) throws IllegalMealException{
        if(meal.getName() == null || meal.getName().isBlank()){
            throw new IllegalMealException("the name is null or empty");
        }

        if(meal.getToBuy() == null){
            throw new IllegalMealException("the 'to buy' list is null");
        }

        if(meal.getMealTime() == null){
            throw new IllegalMealException("'meal time' cannot be null");
        }

        if(meal.getRating() != null && (meal.getRating() < 0 || meal.getRating() > 5)){
            throw new IllegalMealException("rating must be between 0 to 5");
        }

        if(meal.getSpecialOccations() == null){
            throw new IllegalMealException("special occations cannot be null");
        }

        List<String> badHomeIds = validateHomeDishes(meal);

        if(!badHomeIds.isEmpty()){
            System.out.println(badHomeIds.size());
            StringBuilder sb = new StringBuilder();

            for(String id : badHomeIds){
                sb.append(id).append(", ");
                System.out.println(id);
            }

            sb.delete(sb.length() - 2, sb.length());

            throw new IllegalMealException("bad home dishes ids: " + sb.toString());
        }

        List<Integer> badDeliveryIds = validateDeliveryDish(meal);

        if(!badDeliveryIds.isEmpty()){
            StringBuilder sb = new StringBuilder();

            for(int id : badDeliveryIds){
                sb.append(id).append(", ");
            }

            sb.delete(sb.length() - 2, sb.length());

            throw new IllegalMealException("bad delivery dishes ids: " + sb.toString());
        }
    }

    private List<String> validateHomeDishes(Meal meal){
        List<String> missing = webClient.post()
                                        .uri("http://localhost:8080/internal/check_dish_list")
                                        .bodyValue(meal.getHomeDishesIds())
                                        .retrieve()
                                        .bodyToMono(new ParameterizedTypeReference<List<String>>() {})
                                        .timeout(Duration.ofSeconds(10))
                                        .onErrorMap(TimeoutException.class, throwable -> new RuntimeException("request timed out", throwable))
                                        .onErrorMap(e -> new Exception("error fetching home dishes", e))
                                        .block();

        return missing;
    }

    private List<Integer> validateDeliveryDish(Meal meal){
        List<Integer> missing = webClient.post()
                                        .uri("http://localhost:8081/internal/check_dish_list")
                                        .bodyValue(meal.getDeliveryDishesIds())
                                        .retrieve()
                                        .bodyToMono(new ParameterizedTypeReference<List<Integer>>() {})
                                        .timeout(Duration.ofSeconds(10))
                                        .onErrorMap(TimeoutException.class, throwable -> new RuntimeException("request timed out", throwable))
                                        .onErrorMap(e -> new Exception("error fetching delivery dishes", e))
                                        .block();

        return missing;
    }

    private void putTypesInMeal(Meal meal) throws IllegalMealException{
        Set<MealType> types = webClient.post()
                                       .uri("http://localhost:8080/internal/types")
                                       .bodyValue(meal.getHomeDishesIds())
                                       .retrieve()
                                       .bodyToMono(new ParameterizedTypeReference<Set<MealType>>() {})
                                       .timeout(Duration.ofSeconds(10))
                                       .onErrorMap(TimeoutException.class, throwable -> new RuntimeException("request timed out", throwable))
                                       .onErrorMap(e -> new Exception("error fetching home dishes", e))
                                       .block();

        if(types.contains(MealType.MEAT) && types.contains(MealType.MILK)){
            throw new IllegalMealException("meal cannot contain both meat and milk");
        }

        Set<MealType> temp = webClient.post()
                                      .uri("http://localhost:8081/internal/types")
                                      .bodyValue(meal.getDeliveryDishesIds())
                                      .retrieve()
                                      .bodyToMono(new ParameterizedTypeReference<Set<MealType>>() {})
                                      .timeout(Duration.ofSeconds(10))
                                      .onErrorMap(TimeoutException.class, throwable -> new RuntimeException("request timed out", throwable))
                                      .onErrorMap(e -> new Exception("error fetching delivery dishes", e))
                                      .block();

        types.addAll(temp);

        meal.setType(new ArrayList<>(types));
    }




}
