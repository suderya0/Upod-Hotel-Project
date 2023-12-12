package com.example.upodhotelproject;

public class BillsTableController {

    int bills_service_id;
    Long bills_cust_id_field;

    public BillsTableController(int bills_service_id, Long bills_cust_id_field) {
        this.bills_service_id = bills_service_id;
        this.bills_cust_id_field = bills_cust_id_field;
    }

    public int getBills_service_id() {
        return bills_service_id;
    }

    public void setBills_service_id(int bills_service_id) {
        this.bills_service_id = bills_service_id;
    }

    public Long getBills_cust_id_field() {
        return bills_cust_id_field;
    }

    public void setBills_cust_id_field(Long bills_cust_id_field) {
        this.bills_cust_id_field = bills_cust_id_field;
    }




}
