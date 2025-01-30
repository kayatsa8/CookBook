package com.cookbook.homedishes.repository;

import java.util.List;

import com.cookbook.homedishes.model.HomeDish;
import com.cookbook.homedishes.model.filter.Filter;

public interface TemplateRepository {
    List<HomeDish> getByFilter(Filter filter);
}
