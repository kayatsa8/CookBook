package com.cookbook.deliveryfood.model.exception;

public class DishNotFoundException extends Exception{
    public DishNotFoundException(){
        super("dish not found");
    }
}
