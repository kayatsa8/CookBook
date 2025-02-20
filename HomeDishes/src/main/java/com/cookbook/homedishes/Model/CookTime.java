package com.cookbook.homedishes.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CookTime implements Comparable<CookTime>{
    private Integer days;
    private Integer hours;
    private Integer minutes;
    private Integer seconds;



    @Override
    public int compareTo(CookTime other) {
        if(this.days - other.days != 0){
            return this.days - other.days;
        }

        if(this.hours - other.hours != 0){
            return this.hours - other.hours;
        }

        if(this.minutes - other.minutes != 0){
            return this.minutes - other.minutes;
        }

        return this.seconds - other.seconds;
    }
}
