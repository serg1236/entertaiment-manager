package com.epam.spring.core.model;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlSchema;
import javax.xml.bind.annotation.XmlType;

/**
 * Created by Sergiy_Dakhniy
 */
@XmlType(name = "event-rating")
@XmlEnum
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
