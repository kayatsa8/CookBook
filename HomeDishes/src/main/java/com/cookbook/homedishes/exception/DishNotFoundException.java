package com.cookbook.homedishes.exception;

public class DishNotFoundException extends Exception {
    
    public DishNotFoundException(){
        super("dish not found");
    }


}
