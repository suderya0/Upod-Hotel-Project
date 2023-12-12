package com.example.upodhotelproject;

import java.util.Date;

public class HomePageTableController {
    private int id;
    private int room_id;
    private Date check_in_date;
    private Date check_out_date;
    private String customer;
    private int cust_id;

    public int getCust_id() {
        return cust_id;
    }

    public void setCust_id(int cust_id) {
        this.cust_id = cust_id;
    }


    public String getCustomer() {
        return this.customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public HomePageTableController(int id, int room_id, Date check_in_date, Date check_out_date, String customer, int cust_id) {
        this.id = id;
        this.room_id = room_id;
        this.check_in_date = check_in_date;
        this.check_out_date = check_out_date;
        this.customer = customer;
        this.cust_id = cust_id;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getRoom_id() {
        return this.room_id;
    }

    public void setRoom_id(int room_id) {
        this.room_id = room_id;
    }

    public Date getCheck_in_date() {
        return this.check_in_date;
    }

    public void setCheck_in_date(Date check_in_date) {
        this.check_in_date = check_in_date;
    }

    public Date getCheck_out_date() {
        return this.check_out_date;
    }

    public void setCheck_out_date(Date check_out_date) {
        this.check_out_date = check_out_date;
    }
}
