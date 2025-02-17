package com.cookbook.meals.repository;

import java.util.List;

import com.cookbook.meals.model.Meal;
import com.cookbook.meals.model.filter.Filter;

public interface QueryRepository {
    List<Meal> findByFilter(Filter filter);
}
