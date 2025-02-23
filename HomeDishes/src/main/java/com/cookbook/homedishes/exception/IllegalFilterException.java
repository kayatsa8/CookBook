package com.cookbook.homedishes.exception;

public class IllegalFilterException extends Exception {
    
    public IllegalFilterException(String message){
        super("illegal filter: " + message);
    }


}
