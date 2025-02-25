package com.cookbook.meals.service;

import java.time.Duration;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.cookbook.meals.exceptions.IllegalMealException;
import com.cookbook.meals.exceptions.MealNotFoundException;
import com.cookbook.meals.model.DetailedMeal;
import com.cookbook.meals.model.Meal;
import com.cookbook.meals.model.enums.Difficulty;
import com.cookbook.meals.model.enums.Flavors;
import com.cookbook.meals.model.enums.MealType;
import com.cookbook.meals.model.filter.Filter;
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

        repo.insert(meal);
    }

    public DetailedMeal getMeal(String id) throws MealNotFoundException, IllegalMealException{
        Optional<Meal> oMeal = repo.findById(id);

        if(oMeal.isEmpty()){
            throw new MealNotFoundException();
        }

        Meal meal = oMeal.get();
        DetailedMeal detailed = new DetailedMeal(meal);

        putTypesInMeal(detailed);
        putMealDifficulty(detailed);
        putMealRating(detailed);
        putDishesNames(detailed);
        putMealIngredients(detailed);
        putMealFlavors(detailed);

        return detailed;
    }

    public Map<String, String> getMealsIdAndName(){
        List<Meal> meals = repo.getIdsAndNames();
        Map<String, String> id_name = new HashMap<>();

        for(Meal meal : meals){
            id_name.put(meal.getId(), meal.getName());
        }

        return id_name;
    }

    public void deleteMeal(String id) throws MealNotFoundException{
        if(!repo.existsById(id)){
            throw new MealNotFoundException();
        }

        repo.deleteById(id);
    }

    public void updateMeal(Meal updated) throws MealNotFoundException, IllegalMealException{
        Optional<Meal> oMeal = repo.findById(updated.getId());

        if(oMeal.isEmpty()){
            throw new MealNotFoundException();
        }

        validateUpdateMeal(updated);

        Meal meal = oMeal.get();
        
        meal.updateMeal(updated);
        repo.save(meal);
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

        if(meal.getUserRating() == null && (meal.getUserRating() < 0 || meal.getUserRating() > 5)){
            throw new IllegalMealException("rating must be between 0 to 5");
        }

        if(meal.getSpecialOccations() == null){
            throw new IllegalMealException("special occations cannot be null");
        }

        List<String> badHomeIds = validateHomeDishes(meal);

        if(!badHomeIds.isEmpty()){
            StringBuilder sb = new StringBuilder();

            for(String id : badHomeIds){
                sb.append(id).append(", ");
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

        Set<MealType> types = getMealTypes(meal);

        if(types.contains(MealType.MEAT) && types.contains(MealType.DAIRY)){
            throw new IllegalMealException("a meal cannot contain both dairy and meat");
        }
    }

    private List<String> validateHomeDishes(Meal meal) throws IllegalMealException{
        if(meal.getHomeDishesIds() == null){
            throw new IllegalMealException("null homedish list");
        }

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

    private List<Integer> validateDeliveryDish(Meal meal) throws IllegalMealException{
        if(meal.getDeliveryDishesIds() == null){
            throw new IllegalMealException("null delivery dish list");
        }

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

    private Set<MealType> getMealTypes(Meal meal){
        Set<MealType> types = webClient.post()
                                       .uri("http://localhost:8080/internal/types")
                                       .bodyValue(meal.getHomeDishesIds())
                                       .retrieve()
                                       .bodyToMono(new ParameterizedTypeReference<Set<MealType>>() {})
                                       .timeout(Duration.ofSeconds(10))
                                       .onErrorMap(TimeoutException.class, throwable -> new RuntimeException("request timed out", throwable))
                                       .onErrorMap(e -> new Exception("error fetching home dishes", e))
                                       .block();

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

        return types;
    }
    
    private void putTypesInMeal(DetailedMeal meal) throws IllegalMealException{
        Set<MealType> types = getMealTypes(meal);

        meal.setType(new ArrayList<>(types));
    }

    private void putMealDifficulty(DetailedMeal meal){
        Difficulty difficulty = webClient.post()
                                         .uri("http://localhost:8080/internal/difficulty")
                                         .bodyValue(meal.getHomeDishesIds())
                                         .retrieve()
                                         .bodyToMono(new ParameterizedTypeReference<Difficulty>() {})
                                         .timeout(Duration.ofSeconds(10))
                                         .onErrorMap(TimeoutException.class, throwable -> new RuntimeException("request timed out", throwable))
                                         .onErrorMap(e -> new Exception("error fetching home dishes", e))
                                         .block();

        meal.setDifficulty(difficulty);
    }

    private void putMealRating(DetailedMeal meal){
        int sum = webClient.post()
                           .uri("http://localhost:8080/internal/rating_sum")
                           .bodyValue(meal.getHomeDishesIds())
                           .retrieve()
                           .bodyToMono(new ParameterizedTypeReference<Integer>() {})
                           .timeout(Duration.ofSeconds(10))
                           .onErrorMap(TimeoutException.class, throwable -> new RuntimeException("request timed out", throwable))
                           .onErrorMap(e -> new Exception("error fetching home dishes", e))
                           .block();

        sum += webClient.post()
                        .uri("http://localhost:8081/internal/rating_sum")
                        .bodyValue(meal.getDeliveryDishesIds())
                        .retrieve()
                        .bodyToMono(new ParameterizedTypeReference<Integer>() {})
                        .timeout(Duration.ofSeconds(10))
                        .onErrorMap(TimeoutException.class, throwable -> new RuntimeException("request timed out", throwable))
                        .onErrorMap(e -> new Exception("error fetching delivery dishes", e))
                        .block();

        int avg = sum / Math.max((meal.getHomeDishesIds().size() + meal.getDeliveryDishesIds().size()), 1);

        meal.setAverageDishRating(avg);
    }

    private void putDishesNames(DetailedMeal meal){
        List<String> homeDishes = webClient.post()
                                           .uri("http://localhost:8080/internal/names")
                                           .bodyValue(meal.getHomeDishesIds())
                                           .retrieve()
                                           .bodyToMono(new ParameterizedTypeReference<List<String>>() {})
                                           .timeout(Duration.ofSeconds(10))
                                           .onErrorMap(TimeoutException.class, throwable -> new RuntimeException("request timed out", throwable))
                                           .onErrorMap(e -> new Exception("error fetching home dishes", e))
                                           .block();

        List<String> deliveryDishes = webClient.post()
                                               .uri("http://localhost:8081/internal/names")
                                               .bodyValue(meal.getDeliveryDishesIds())
                                               .retrieve()
                                               .bodyToMono(new ParameterizedTypeReference<List<String>>() {})
                                               .timeout(Duration.ofSeconds(10))
                                               .onErrorMap(TimeoutException.class, throwable -> new RuntimeException("request timed out", throwable))
                                               .onErrorMap(e -> new Exception("error fetching delivery dishes", e))
                                               .block();

        meal.setHomeDishesNames(homeDishes);
        meal.setDeliveryDishesNames(deliveryDishes);
    }

    private void putMealIngredients(DetailedMeal meal){
        List<String> ingredients = webClient.post()
                                           .uri("http://localhost:8080/internal/ingredients")
                                           .bodyValue(meal.getHomeDishesIds())
                                           .retrieve()
                                           .bodyToMono(new ParameterizedTypeReference<List<String>>() {})
                                           .timeout(Duration.ofSeconds(10))
                                           .onErrorMap(TimeoutException.class, throwable -> new RuntimeException("request timed out", throwable))
                                           .onErrorMap(e -> new Exception("error fetching home dishes", e))
                                           .block();

        meal.setIngredients(ingredients);
    }

    private void putMealFlavors(DetailedMeal meal){
        List<Flavors> homeFlavors = webClient.post()
                                           .uri("http://localhost:8080/internal/flavors")
                                           .bodyValue(meal.getHomeDishesIds())
                                           .retrieve()
                                           .bodyToMono(new ParameterizedTypeReference<List<Flavors>>() {})
                                           .timeout(Duration.ofSeconds(10))
                                           .onErrorMap(TimeoutException.class, throwable -> new RuntimeException("request timed out", throwable))
                                           .onErrorMap(e -> new Exception("error fetching home dishes", e))
                                           .block();

        List<Flavors> deliveryFlavors = webClient.post()
                                           .uri("http://localhost:8081/internal/flavors")
                                           .bodyValue(meal.getDeliveryDishesIds())
                                           .retrieve()
                                           .bodyToMono(new ParameterizedTypeReference<List<Flavors>>() {})
                                           .timeout(Duration.ofSeconds(10))
                                           .onErrorMap(TimeoutException.class, throwable -> new RuntimeException("request timed out", throwable))
                                           .onErrorMap(e -> new Exception("error fetching delivery dishes", e))
                                           .block();

        Set<Flavors> flavorSet = new HashSet<>();
        flavorSet.addAll(homeFlavors);
        flavorSet.addAll(deliveryFlavors);

        meal.setFlavors(new ArrayList<>(flavorSet));
    }

    private void validateUpdateMeal(Meal meal) throws IllegalMealException{
        if(meal.getName() != null && meal.getName().isBlank()){
            throw new IllegalMealException("name cannot be empty");
        }

        if(meal.getDiners() != null && meal.getDiners() < 0){
            throw new IllegalMealException("diners cannot be a negative number");
        }

        if(meal.getDiners() != null && meal.getDiners() < 0){
            throw new IllegalMealException("diners cannot be a negative number");
        }

        List<String> badHomeIds = new ArrayList<>();

        if(meal.getHomeDishesIds() != null){
            badHomeIds = validateHomeDishes(meal);
        }

        if(!badHomeIds.isEmpty()){
            StringBuilder sb = new StringBuilder();

            for(String id : badHomeIds){
                sb.append(id).append(", ");
            }

            sb.delete(sb.length() - 2, sb.length());

            throw new IllegalMealException("bad home dishes ids: " + sb.toString());
        }

        List<Integer> badDeliveryIds = new ArrayList<>();

        if(meal.getDeliveryDishesIds() != null){
            badDeliveryIds = validateDeliveryDish(meal);
        }

        if(!badDeliveryIds.isEmpty()){
            StringBuilder sb = new StringBuilder();

            for(int id : badDeliveryIds){
                sb.append(id).append(", ");
            }

            sb.delete(sb.length() - 2, sb.length());

            throw new IllegalMealException("bad delivery dishes ids: " + sb.toString());
        }

        Set<MealType> types = getMealTypes(meal);

        if(types.contains(MealType.MEAT) && types.contains(MealType.DAIRY)){
            throw new IllegalMealException("a meal cannot contain both dairy and meat");
        }

        if(meal.getUserRating() != null && (meal.getUserRating() < 0 || meal.getUserRating() > 5)){
            throw new IllegalMealException("rating must be between 0 to 5");            
        }

    }

    public DetailedMeal getRandom() throws Exception{
        List<Meal> ids = repo.getIds();

        if(ids.isEmpty()){
            throw new Exception("no meals in the system");
        }

        Random r = new Random();
        int index = r.nextInt(ids.size());
        String id = ids.get(index).getId();

        return getMeal(id);
    }

    public Map<String, String> getFilteredMeal(Filter filter) throws IllegalMealException{
        List<Meal> firstFilter = repo.findByFilter(filter);
        Map<String, String> id_name = new HashMap<>();
        DetailedMeal detailed;
        boolean toInsert;

        for(Meal meal : firstFilter){
            detailed = new DetailedMeal(meal);
            toInsert = true;

            if(filter.getIngredients() != null){
                putMealIngredients(detailed);
                toInsert &= detailed.getIngredients().containsAll(filter.getIngredients());
            }

            if(toInsert && filter.getFlavors() != null){
                putMealFlavors(detailed);
                toInsert &= detailed.getFlavors().containsAll(filter.getFlavors());
            }

            if(toInsert && filter.getType() != null){
                putTypesInMeal(detailed);
                toInsert &= detailed.getType().containsAll(filter.getType());
            }

            if(toInsert && filter.getDifficulty() != null){
                putMealDifficulty(detailed);
                toInsert &= detailed.getDifficulty() == filter.getDifficulty();
            }

            if(toInsert && filter.getAverageDishRating() != null){
                putMealRating(detailed);

                if(filter.getAverageDishRating().getLow() != null){
                    toInsert &= detailed.getAverageDishRating() >= filter.getAverageDishRating().getLow();
                }
                if(filter.getAverageDishRating().getHigh() != null){
                    toInsert &= detailed.getAverageDishRating() <= filter.getAverageDishRating().getHigh(); 
                }
            }

            if(toInsert){
                id_name.put(meal.getId(), meal.getName());
            }
        }

        return id_name;
    }

    public DetailedMeal getRandomFilteredMeal(Filter filter) throws IllegalMealException, MealNotFoundException{
        List<DetailedMeal> meals = getFilteredMeal(filter);

        if(meals.isEmpty()){
            throw new MealNotFoundException();
        }

        Random r = new Random();
        int index = r.nextInt(meals.size());

        return meals.get(index);
    }


}
