package com.epam.spring.core.random.impl;

import com.epam.spring.core.random.LuckGenerator;

/**
 * Created by Sergiy_Dakhniy
 */
public class FalseLuckGenerator implements LuckGenerator {
    public boolean isLucky() {
        return false;
    }
}
