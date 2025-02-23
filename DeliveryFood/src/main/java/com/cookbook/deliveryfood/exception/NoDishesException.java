package com.cookbook.deliveryfood.exception;

public class NoDishesException extends Exception{
    
    public NoDishesException(){
        super("no dishes in the system");
    }

}
