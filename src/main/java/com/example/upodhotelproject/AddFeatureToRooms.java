package com.example.upodhotelproject;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class AddFeatureToRooms extends UpodHotel implements Initializable {

    @FXML
    public TableColumn<FRTableController, Integer> room_id_for_feature;
    @FXML
    public TableColumn<FRTableController, String> feature_name;
    @FXML
    public Button save_button;
    @FXML
    public Button delete_button;
    @FXML
    public TextField room_id_text_field;
    @FXML
    public ComboBox<String> feature_names_combobox;
    @FXML
    public TableView<FRTableController> room_feature_table;
    @FXML
    public Button come_back_rooms_page;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        if(save_button != null){
            this.save_button.setOnAction((event) -> {
                try {
                    saveButton();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            });
        }

        if (this.come_back_rooms_page != null) {
            come_back_home_page(this.come_back_rooms_page);
        }

        try {
            this.getValues(this.room_feature_table);
        } catch (SQLException var4) {
            throw new RuntimeException();
        }




        try {
            loadFeatureNames();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
    String sql;
    public void deleteButton() throws SQLException {
        String selectedFeature = feature_names_combobox.getValue();

        sql = "DELETE FROM room_feature WHERE room_id = ? and feature_name = ?";
        Connection connection;
        PreparedStatement psInsert;

        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/upod-hotel", "root", "derya12sude34");
        } catch (SQLException e) {
            throw new RuntimeException();
        }
        psInsert = connection.prepareStatement(sql);
        psInsert.setInt(1, Integer.parseInt(room_id_text_field.getText()));
        psInsert.setString(2, selectedFeature);
        psInsert.executeUpdate();


    }


    public void saveButton() throws SQLException {
        sql = "INSERT INTO room_feature (room_id, feature_name) VALUES (?, ?)";
        String selectedFeature = feature_names_combobox.getValue();
        Connection connection = null;
        PreparedStatement psInsert = null;
        PreparedStatement psCheckRoomExist = null;
        ResultSet resultSet = null;

        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/upod-hotel", "root", "derya12sude34");
        } catch (SQLException var21) {
            throw new RuntimeException(var21);
        }
        psInsert = connection.prepareStatement(sql);
        psInsert.setInt(1, Integer.parseInt(room_id_text_field.getText()));
        psInsert.setString(2, selectedFeature);
        psInsert.executeUpdate();


    }

    public static void come_back_home_page(Button come_back_home_page) {
        if (come_back_home_page != null) {
            come_back_home_page.setOnAction(event -> DataBaseUtils.changeScene(event, "rooms.fxml"));
        } else {
            System.out.println("buton bos");
        }

    }
    ObservableList<FRTableController> room_feature = FXCollections.observableArrayList();


    public void getValues(TableView<FRTableController> table) throws SQLException {
        Connection connection = null;
        PreparedStatement psInsert = null;
        ResultSet resultSet = null;

        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/upod-hotel", "root", "derya12sude34");
        } catch (SQLException var7) {
            throw new RuntimeException(var7);
        }

        psInsert = connection.prepareStatement("SELECT * FROM room_feature");
        resultSet = psInsert.executeQuery();

        while(resultSet.next()) {
            this.room_feature.add(new FRTableController(resultSet.getInt("room_id"), resultSet.getString("feature_name")));
            this.room_id_for_feature.setCellValueFactory(new PropertyValueFactory("room_id_for_feature"));
            this.feature_name.setCellValueFactory(new PropertyValueFactory("feature_name"));

            if (table != null) {
                table.setItems(this.room_feature);
            } else {
                System.out.println("table null");
            }
        }


    }

    private void loadFeatureNames() throws SQLException {
        Connection connection = null;
        PreparedStatement psSelect = null;
        ResultSet resultSet = null;

        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/upod-hotel", "root", "derya12sude34");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        ObservableList<String> featureNames = FXCollections.observableArrayList();

        psSelect = connection.prepareStatement("SELECT feature_name FROM feature");
        resultSet = psSelect.executeQuery();

        while (resultSet.next()) {
            featureNames.add(resultSet.getString("feature_name"));
        }

        feature_names_combobox.setItems(featureNames);
    }


}
