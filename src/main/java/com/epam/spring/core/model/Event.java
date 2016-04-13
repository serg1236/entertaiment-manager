package com.epam.spring.core.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.xml.bind.annotation.*;

@JsonIgnoreProperties(ignoreUnknown=true)
@XmlRootElement(name = "event")
@XmlAccessorType(XmlAccessType.NONE)
public class Event {
    @JsonIgnore
    private int id;
    @XmlElement(name="name", required = true)
    private String name;
    @XmlElement(name="base-price", required = true)
    private double basePrice;
    @XmlElement(name="rating", required = true)
    private EventRating rating;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getBasePrice() {
        return basePrice;
    }

    public void setBasePrice(double basePrice) {
        this.basePrice = basePrice;
    }

    public EventRating getRating() {
        return rating;
    }

    public void setRating(EventRating rating) {
        this.rating = rating;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

}
