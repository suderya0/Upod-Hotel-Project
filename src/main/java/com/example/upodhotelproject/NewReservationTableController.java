package com.example.upodhotelproject;



public class NewReservationTableController {
    int room_id;

    public NewReservationTableController(int room_id) {
        this.room_id = room_id;
    }

    public int getRoom_id() {
        return this.room_id;
    }

    public void setRoom_id(int room_id) {
        this.room_id = room_id;
    }
}
