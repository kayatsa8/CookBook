package com.cookbook.deliveryfood.exception;

public class InvalidDishException extends Exception{

    public InvalidDishException(String message){
        super("invalid dish: " + message);
    }
    
}
