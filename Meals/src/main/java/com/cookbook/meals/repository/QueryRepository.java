package com.cookbook.meals.repository;

import java.util.List;
import java.util.logging.Filter;

import com.cookbook.meals.model.Meal;

public interface QueryRepository {
    List<Meal> findByFilter(Filter filter);
}
