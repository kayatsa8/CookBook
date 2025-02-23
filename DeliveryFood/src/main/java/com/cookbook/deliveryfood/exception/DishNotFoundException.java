package com.cookbook.deliveryfood.exception;

public class DishNotFoundException extends Exception{
    public DishNotFoundException(){
        super("dish not found");
    }
}
