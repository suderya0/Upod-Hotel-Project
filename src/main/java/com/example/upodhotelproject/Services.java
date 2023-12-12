package com.example.upodhotelproject;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class Services extends UpodHotel implements Initializable {

    @FXML
    public TableView<ServiceTableController> service_table;
    @FXML
    public TableColumn<ServiceTableController, Integer> service_id_column;
    @FXML
    public TableColumn<ServiceTableController, Integer> service_name_column;
    @FXML
    public Button save_button;
    @FXML
    public TextField service_name_field;
    @FXML
    public TextField service_id_field;
    @FXML
    public Button come_back_home_page;
    @FXML
    public Button delete_service_button;
    @FXML
    public TextField delete_service;
    @FXML
    public TextField service_price_field;
    @FXML
    public TableColumn<ServiceTableController, Integer> service_price_column;

    public Services() {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if (this.save_button != null) {
            this.save_button.setOnAction((event) -> {
                try {
                    DataBaseUtils.createService(event, Integer.valueOf(service_id_field.getText()), service_name_field.getText(), Integer.valueOf(service_price_field.getText()));
                } catch (SQLException var3) {
                    throw new RuntimeException(var3);
                }
            });
        }
        if (this.come_back_home_page != null) {
            come_back_home_page(this.come_back_home_page);
        }

        if (this.delete_service_button != null) {
            final String sqlDelete = "DELETE FROM service WHERE id = ?";
            this.delete_service_button.setOnAction(new EventHandler<ActionEvent>() {
                public void handle(ActionEvent event) {
                    try {
                        UpodHotel.delete(sqlDelete, Integer.valueOf(delete_service.getText()));
                    } catch (SQLException var3) {
                        throw new RuntimeException(var3);
                    }
                }
            });
        }

        try {
            this.getValues(this.service_table);
        } catch (SQLException var4) {
            throw new RuntimeException(var4);
        }



    }
    ObservableList<ServiceTableController> serviceList = FXCollections.observableArrayList();

    public void getValues(TableView<ServiceTableController> table) throws SQLException {
        Connection connection = null;
        PreparedStatement psInsert = null;
        ResultSet resultSet = null;

        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/upod-hotel", "root", "derya12sude34");
        } catch (SQLException var6) {
            throw new RuntimeException(var6);
        }

        psInsert = connection.prepareStatement("SELECT * FROM service");
        resultSet = psInsert.executeQuery();

        while(resultSet.next()) {
            this.serviceList.add(new ServiceTableController(resultSet.getInt("id"), resultSet.getString("service_name"), resultSet.getInt("price")));
            this.service_id_column.setCellValueFactory(new PropertyValueFactory("service_id"));
            this.service_name_column.setCellValueFactory(new PropertyValueFactory("service_name"));
            this.service_price_column.setCellValueFactory(new PropertyValueFactory("service_price"));

            if (table != null) {
                table.setItems(this.serviceList);
            } else {
                System.out.println("table null");
            }
        }


}
}

