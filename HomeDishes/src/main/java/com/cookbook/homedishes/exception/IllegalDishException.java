package com.cookbook.homedishes.exception;

public class IllegalDishException extends Exception {
    public IllegalDishException(String message){
        super("illigal dish: " + message);
    }
}
