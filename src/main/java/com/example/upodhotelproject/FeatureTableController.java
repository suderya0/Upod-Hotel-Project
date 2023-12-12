package com.example.upodhotelproject;


public class FeatureTableController {
    int id;
    String feature_name;

    public FeatureTableController(int id, String feature_name) {
        this.id = id;
        this.feature_name = feature_name;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFeature_name() {
        return this.feature_name;
    }

    public void setFeature_name(String feature_name) {
        this.feature_name = feature_name;
    }
}

