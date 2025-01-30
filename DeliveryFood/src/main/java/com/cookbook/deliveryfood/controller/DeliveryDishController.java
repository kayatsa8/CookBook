package com.cookbook.deliveryfood.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.cookbook.deliveryfood.service.DeliveryDishService;

@Controller
public class DeliveryDishController {

    @Autowired
    private DeliveryDishService service;

    
    
}
