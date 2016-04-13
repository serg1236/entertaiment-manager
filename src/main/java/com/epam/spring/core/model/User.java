package com.epam.spring.core.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.*;

@JsonIgnoreProperties(ignoreUnknown=true)
@XmlRootElement(name="user")
@XmlAccessorType(XmlAccessType.NONE)
public class User {
    @JsonIgnore
    private int id;
    @XmlElement(name = "name")
    private String name;
    @XmlElement(name = "email")
    private String email;
    @JsonFormat(shape= JsonFormat.Shape.STRING, pattern= "dd-MM-yyyy")
    @XmlElement(name = "birth-date")
    private Date birthDate;
    private String roles;
    private String password;
    @XmlElement(name="money")
    private double money;
    @JsonIgnore
    private List<Ticket> purchasedTickets = new ArrayList<Ticket>();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public List<Ticket> getPurchasedTickets() {
        return purchasedTickets;
    }

    public void setPurchasedTickets(List<Ticket> purchasedTickets) {
        this.purchasedTickets = purchasedTickets;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRoles() {
        return roles;
    }

    public void setRoles(String roles) {
        this.roles = roles;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public double getMoney() {
        return money;
    }

    public void setMoney(double money) {
        this.money = money;
    }

    public User(String name, String email, Date birthDate) {
        this.name = name;
        this.email = email;
        this.birthDate = birthDate;
    }

    public User(){};
}
