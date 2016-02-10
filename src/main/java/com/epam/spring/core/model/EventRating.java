package com.epam.spring.core.model;

/**
 * Created by Sergiy_Dakhniy
 */
public enum EventRating {
    LOW {
        @Override
        public double getPriceMultiplier() {
            return 1;
        }
    }, MEDIUM {
        @Override
        public double getPriceMultiplier() {
            return 1.1;
        }
    }, HIGH {
        @Override
        public double getPriceMultiplier() {
            return 1.2;
        }
    };
    public abstract double getPriceMultiplier();
}
