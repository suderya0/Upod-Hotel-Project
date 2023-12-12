package com.example.upodhotelproject;

public class ServiceTableController {


    int service_id;
    String service_name;
    int service_price;


    public ServiceTableController(int service_id, String service_name, int service_price) {
        this.service_id = service_id;
        this.service_name = service_name;
        this.service_price = service_price;
    }

    public int getService_price() {
        return service_price;
    }

    public void setService_price(int service_price) {
        this.service_price = service_price;
    }


    public int getService_id() {
        return service_id;
    }

    public void setService_id(int service_id) {
        this.service_id = service_id;
    }

    public String getService_name() {
        return service_name;
    }

    public void setService_name(String service_name) {
        this.service_name = service_name;
    }


}
