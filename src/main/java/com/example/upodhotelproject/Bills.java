package com.example.upodhotelproject;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class Bills extends UpodHotel implements Initializable {

    @FXML
    public TableView<BillsTableController> bill_table;
    @FXML
    public TableColumn<BillsTableController, Integer> bilss_cust_id_column;
    @FXML
    public TableColumn<BillsTableController, Integer> bills_bill_column;
    @FXML
    public TextField bills_service_id;
    @FXML
    public TextField bills_cust_id_field;
    @FXML
    public Button add_service_button;
    @FXML
    public Button come_back_home_page;


    ObservableList<BillsTableController> billList = FXCollections.observableArrayList();


    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            getValues(bill_table);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        if(add_service_button != null){
             this.add_service_button.setOnAction((event) -> {
                 DataBaseUtils.addBill(event, Integer.valueOf(this.bills_service_id.getText()), Long.parseLong(this.bills_cust_id_field.getText()));


                 try {
                     this.getValues(this.bill_table);
                 } catch (SQLException var5) {
                     throw new RuntimeException(var5);
                 }


             });

         }


        if (this.come_back_home_page != null) {
            come_back_home_page(this.come_back_home_page);
        }


        try {
            this.getValues(this.bill_table);
        } catch (SQLException var4) {
            throw new RuntimeException(var4);
        }

    }


    public void getValues(TableView<BillsTableController> table) throws SQLException {
        Connection connection = null;
        PreparedStatement psInsert = null;
        ResultSet resultSet = null;

        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/upod-hotel", "root", "derya12sude34");
        } catch (SQLException var6) {
            throw new RuntimeException(var6);
        }

        psInsert = connection.prepareStatement("SELECT * FROM bills");
        resultSet = psInsert.executeQuery();

        while(resultSet.next()) {
            this.billList.add(new BillsTableController(resultSet.getInt("cust_id"), resultSet.getLong("bill")));
            this.bilss_cust_id_column.setCellValueFactory(new PropertyValueFactory("bills_service_id"));
            this.bills_bill_column.setCellValueFactory(new PropertyValueFactory("bills_cust_id_field"));

            if (table != null) {
                table.setItems(this.billList);
            } else {
                System.out.println("table null");
            }
        }


    }
}
