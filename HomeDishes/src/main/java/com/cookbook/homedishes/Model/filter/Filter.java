package com.cookbook.homedishes.model.filter;

import java.util.List;

import com.cookbook.homedishes.model.enums.Difficulty;
import com.cookbook.homedishes.model.enums.DishType;
import com.cookbook.homedishes.model.enums.Flavors;
import com.cookbook.homedishes.model.enums.MealPart;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Filter {
   private String nameKeyword;
   private Range diners;
   private List<String> ingredients;
   private Range timeInMinutes;
   private DishType type;
   private List<Flavors> flavors;
   private Difficulty difficulty;
   private MealPart mealPart;
   private Range rating;
}
