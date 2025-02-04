package com.cookbook.deliveryfood.repository;

import java.util.List;

import com.cookbook.deliveryfood.model.DeliveryDish;
import com.cookbook.deliveryfood.model.filter.Filter;

public interface QueryRepository {
    List<DeliveryDish> getByFilter(Filter filter);
}
