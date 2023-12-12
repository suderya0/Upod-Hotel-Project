package com.example.upodhotelproject;

import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;

public class HomePage implements Initializable {
    @FXML
    public TableView reservation_table;
    @FXML
    public TableColumn id_home_page_res;
    @FXML
    public TableColumn room_home_page_res;
    @FXML
    public TableColumn check_in_Date_home_page_res;
    @FXML
    public TableColumn check_out_date_home_page_res;
    @FXML
    public TableColumn check_in_time_home_page_res;
    @FXML
    public TableColumn check_out_time_home_page_res;
    @FXML
    public TableColumn customer_home_page_res;
    @FXML
    public Button rooms_button;
    @FXML
    public Button customers_button;
    @FXML
    public Button services_button;
    @FXML
    public Button features_button;
    @FXML
    public Button new_res_button;
    @FXML
    public Button delete_button;
    @FXML
    public DatePicker searching_start_date;
    @FXML
    public DatePicker searching_end_date;
    @FXML
    public TextField searching_customer_home_page;
    @FXML
    public Button search_date_button_home_page;
    @FXML
    public TextField delete_this_room;
    @FXML
    public TableColumn home_page_cust_id_column;
    @FXML
    public Button home_page_bills_button;

    public HomePage() {
    }

    public void initialize(URL url, ResourceBundle resourceBundle) {
        Connection connection = null;
        PreparedStatement psInsert = null;
        ResultSet resultSet = null;
        if (this.rooms_button != null) {
            this.rooms_button.setOnAction(new EventHandler<ActionEvent>() {
                public void handle(ActionEvent event) {
                    DataBaseUtils.changeScene(event, "rooms.fxml");
                }
            });
        } else {
            System.out.println("buton bos");
        }

        if (this.customers_button != null) {
            this.customers_button.setOnAction(new EventHandler<ActionEvent>() {
                public void handle(ActionEvent event) {
                    DataBaseUtils.changeScene(event, "customers.fxml");
                }
            });
        } else {
            System.out.println("buton bos");
        }

        if (this.home_page_bills_button != null) {
            this.home_page_bills_button.setOnAction(new EventHandler<ActionEvent>() {
                public void handle(ActionEvent event) {
                    DataBaseUtils.changeScene(event, "Bills.fxml");
                }
            });
        } else {
            System.out.println("buton bos");
        }

        if (this.new_res_button != null) {
            this.new_res_button.setOnAction(new EventHandler<ActionEvent>() {
                public void handle(ActionEvent event) {
                    DataBaseUtils.changeScene(event, "new_reservation.fxml");
                }
            });
        } else {
            System.out.println("buton bos");
        }

        if (this.services_button != null) {
            this.services_button.setOnAction(new EventHandler<ActionEvent>() {
                public void handle(ActionEvent event) {
                    DataBaseUtils.changeScene(event, "services.fxml");
                }
            });
        } else {
            System.out.println("buton bos");
        }

        String sqlCheck = "SELECT * FROM reservation";
        final String sqlDelete = "DELETE FROM reservation WHERE id = ?";
        if (this.delete_button != null) {
            this.delete_button.setOnAction(new EventHandler<ActionEvent>() {
                public void handle(ActionEvent event) {
                    try {
                        UpodHotel.delete(sqlDelete, Integer.valueOf(HomePage.this.delete_this_room.getText()));
                    } catch (SQLException var3) {
                        throw new RuntimeException(var3);
                    }
                }
            });
        }

        if (this.features_button != null) {
            this.features_button.setOnAction(new EventHandler<ActionEvent>() {
                public void handle(ActionEvent event) {
                    DataBaseUtils.changeScene(event, "features.fxml");
                }
            });
        }

        String sql = "SELECT * FROM reservation";

        try {
            this.getValues(this.reservation_table, sql);
        } catch (SQLException var10) {
            throw new RuntimeException(var10);
        }
    }

    public void getValues(TableView<HomePageTableController> table, String sql) throws SQLException {
        Connection connection = null;
        PreparedStatement psInsert = null;
        ResultSet resultSet = null;

        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/upod-hotel", "root", "derya12sude34");
        } catch (SQLException var7) {
            throw new RuntimeException(var7);
        }

        ObservableList<HomePageTableController> reservationListe = FXCollections.observableArrayList();
        psInsert = connection.prepareStatement(sql);
        resultSet = psInsert.executeQuery();

        while(resultSet.next()) {
            reservationListe.add(new HomePageTableController(resultSet.getInt("id"),
                    resultSet.getInt("room_id"), resultSet.getDate("check_in_date"),
                    resultSet.getDate("check_out_date"), resultSet.getString("customer"),
                    resultSet.getInt("cust_id")));
        }

        this.id_home_page_res.setCellValueFactory(new PropertyValueFactory("id"));
        this.room_home_page_res.setCellValueFactory(new PropertyValueFactory("room_id"));
        this.check_in_Date_home_page_res.setCellValueFactory(new PropertyValueFactory("check_in_date"));
        this.check_out_date_home_page_res.setCellValueFactory(new PropertyValueFactory("check_out_date"));
        this.customer_home_page_res.setCellValueFactory(new PropertyValueFactory("customer"));
        this.home_page_cust_id_column.setCellValueFactory(new PropertyValueFactory<>("cust_id"));
        if (table != null) {
            table.setItems(reservationListe);
        } else {
            System.out.println("table null");
        }

    }

    public void searchCustomer(KeyEvent keyEvent) throws SQLException {
        String sql;
        if (this.searching_customer_home_page.getText().equals("")) {
            sql = "SELECT * FROM reservation";
        } else {
            sql = "SELECT * FROM reservation WHERE customer LIKE '%" + this.searching_customer_home_page.getText() + "%'";
        }

        this.getValues(this.reservation_table, sql);
    }

    public void searchDate(ActionEvent event) throws SQLException {
        String var10000 = String.valueOf(this.searching_start_date.getValue());
        String sql = "SELECT * FROM reservation WHERE check_in_date > '" + var10000 + "' and check_out_date < '" + String.valueOf(this.searching_end_date.getValue()) + "'";
        this.getValues(this.reservation_table, sql);
    }
}
