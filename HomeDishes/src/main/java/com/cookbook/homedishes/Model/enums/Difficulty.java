package com.cookbook.homedishes.model.enums;

public enum Difficulty {
    EASY(0), NORMAL(1), HARD(2);


    private final int value;

    Difficulty(int value){
        this.value = value;
    }

    public int getValue(){
        return value;
    }
}
