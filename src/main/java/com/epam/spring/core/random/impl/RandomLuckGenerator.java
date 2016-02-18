package com.epam.spring.core.random.impl;

import com.epam.spring.core.random.LuckGenerator;

import java.util.Random;

/**
 * Created by Sergiy_Dakhniy
 */
public class RandomLuckGenerator implements LuckGenerator{

    public boolean isLucky() {
        int random = new Random(System.currentTimeMillis()).nextInt() % 2;
        return random == 0;
    }
}
