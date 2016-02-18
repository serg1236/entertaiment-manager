package com.epam.spring.core.random.impl;

import com.epam.spring.core.random.LuckGenerator;

/**
 * Created by Sergiy_Dakhniy
 */
public class TrueLuckGenerator implements LuckGenerator {
    public boolean isLucky() {
        return true;
    }
}
