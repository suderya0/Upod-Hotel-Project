package com.example.upodhotelproject;

import java.util.Date;

public class CustomerTableController {
    private int id;
    private Long identity_number;
    private Long phone_number;
    private Date birth_date;
    private String full_name;
    private String description;

    public CustomerTableController(int id, Long identity_number, Long phone_number, Date birth_date, String full_name, String description) {
        this.id = id;
        this.identity_number = identity_number;
        this.phone_number = phone_number;
        this.birth_date = birth_date;
        this.full_name = full_name;
        this.description = description;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Long getIdentity_number() {
        return this.identity_number;
    }

    public void setIdentity_number(Long identity_number) {
        this.identity_number = identity_number;
    }

    public Long getPhone_number() {
        return this.phone_number;
    }

    public void setPhone_number(Long phone_number) {
        this.phone_number = phone_number;
    }

    public Date getBirth_date() {
        return this.birth_date;
    }

    public void setBirth_date(Date birth_date) {
        this.birth_date = birth_date;
    }

    public String getFull_name() {
        return this.full_name;
    }

    public void setFull_name(String full_name) {
        this.full_name = full_name;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}

