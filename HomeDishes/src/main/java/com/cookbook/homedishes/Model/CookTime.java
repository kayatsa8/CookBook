package com.cookbook.homedishes.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CookTime {
    private Integer days;
    private Integer hours;
    private Integer minutes;
    private Integer seconds;
}
