package com.cookbook.deliveryfood.model.exception;

public class NoDishesException extends Exception{
    
    public NoDishesException(){
        super("no dishes in the system");
    }

}
