package com.example.upodhotelproject;

public class FRTableController {

    int room_id_for_feature;
    String feature_name;

    public FRTableController(int room_id_for_feature, String feature_name) {
        this.room_id_for_feature = room_id_for_feature;
        this.feature_name = feature_name;
    }

    public int getRoom_id_for_feature() {
        return room_id_for_feature;
    }

    public void setRoom_id_for_feature(int room_id_for_feature) {
        this.room_id_for_feature = room_id_for_feature;
    }

    public String getFeature_name() {
        return feature_name;
    }

    public void setFeature_name(String feature_name) {
        this.feature_name = feature_name;
    }
}
