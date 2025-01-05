package com.cookbook.homedishes.exception;

public class DishExistsException extends Exception{
    public DishExistsException(String dishName){
        super("a dish named \"" + dishName + "\" already exists");
    }
}
