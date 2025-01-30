package com.cookbook.homedishes.exception;

public class IlligalDishException extends Exception {
    public IlligalDishException(String message){
        super("illigal dish: " + message);
    }
}
