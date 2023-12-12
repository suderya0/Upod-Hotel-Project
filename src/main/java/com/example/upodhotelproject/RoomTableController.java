package com.example.upodhotelproject;

public class RoomTableController {
    String name;
    int id;
    int capacity;
    float price;

    public RoomTableController(String name, int id, int capacity, float price) {
        this.name = name;
        this.id = id;
        this.capacity = capacity;
        this.price = price;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCapacity() {
        return this.capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public float getPrice() {
        return this.price;
    }

    public void setPrice(float price) {
        this.price = price;
    }
}