package com.example.upodhotelproject;
import java.sql.*;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Alert.AlertType;

public class UpodHotel {
    public UpodHotel() {
    }

    public static void come_back_home_page(Button come_back_home_page) {
        if (come_back_home_page != null) {
            come_back_home_page.setOnAction(new EventHandler<ActionEvent>() {
                public void handle(ActionEvent event) {
                    DataBaseUtils.changeScene(event, "home_page.fxml");
                }
            });
        } else {
            System.out.println("buton bos");
        }

    }

    public static void delete(String sqlDelete, long id) throws SQLException {
        Connection connection = null;
        PreparedStatement psDelete = null;

        try {
            try {
                connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/upod-hotel", "root", "derya12sude34");
                psDelete = connection.prepareStatement(sqlDelete);
                psDelete.setLong(1, id);
                int affectedRows = psDelete.executeUpdate();
                Alert alert;
                if (affectedRows > 0) {
                    alert = new Alert(AlertType.INFORMATION);
                    alert.setContentText("Deleted Successfully!");
                    alert.show();
                } else {
                    System.out.println("No such id!");
                    alert = new Alert(AlertType.ERROR);
                    alert.setContentText("No such id!");
                    alert.show();
                }
            } catch (SQLException var9) {
                var9.printStackTrace();
            }

        } finally {
            ;
        }
    }

    public static void getConnection(){
        Connection connection = null;
        PreparedStatement psInsert = null;
        PreparedStatement psCheckCustomerExist = null;
        ResultSet resultSet = null;

        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/upod-hotel", "root", "derya12sude34");
        } catch (SQLException var22) {
            throw new RuntimeException(var22);
        }
    }
}