package com.cookbook.homedishes.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cookbook.homedishes.service.HomeDishService;

@RestController
@RequestMapping("/api/home_dish")
public class HomeDishController {
    
    @Autowired
    private HomeDishService service;


    @GetMapping("/")
    public String home(){
        return "hi home dishes";
    }

}









