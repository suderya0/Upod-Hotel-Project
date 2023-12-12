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
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

public class Rooms extends UpodHotel implements Initializable {
    @FXML
    public Button come_back_home_page;
    @FXML
    public ComboBox<String> room_feature_combobox;
    @FXML
    public Button add_new_features_button;
    @FXML
    TableColumn<RoomTableController, String> column_room_name = new TableColumn("Room_Name");
    @FXML
    TableColumn<RoomTableController, Integer> column_room_id = new TableColumn("ID");
    @FXML
    TableColumn<RoomTableController, Integer> column_room_capacity = new TableColumn("Capacity");
    @FXML
    TableColumn<RoomTableController, Float> column_room_price = new TableColumn("Price");
    @FXML
    public TextField room_name_field;
    @FXML
    public TextField room_capacity_field;
    @FXML
    public TextField room_price_field;
    @FXML
    public TextField room_id_field;
    @FXML
    public Button room_save_button;
    @FXML
    public Button delete_room_button;
    @FXML
    public TextField room_name_ID;
    @FXML
    public TableView<RoomTableController> rooms_table;
    int index = -1;

    public Rooms() {
    }

    public void initialize(URL url, ResourceBundle resourceBundle) {
        if (this.room_save_button != null) {
            this.room_save_button.setOnAction((event) -> {
                try {
                    String selectedFeature = room_feature_combobox.getValue();
                    DataBaseUtils.createRoom(event, Integer.valueOf(this.room_name_ID.getText()),
                            this.room_name_field.getText(), Integer.valueOf(this.room_capacity_field.getText()),
                            (float)Integer.valueOf(this.room_price_field.getText()), selectedFeature );
                } catch (SQLException var3) {
                    throw new RuntimeException(var3);
                }
                this.reservationListe.clear();

                try {
                    this.getValues(this.rooms_table);
                } catch (SQLException var5) {
                    throw new RuntimeException(var5);
                }

                this.rooms_table.refresh();


            });

            try {
                this.getValues(this.rooms_table);
            } catch (SQLException var5) {
                throw new RuntimeException(var5);
            }
        }



        if (this.come_back_home_page != null) {
            come_back_home_page(this.come_back_home_page);
        }
        if(this.add_new_features_button != null){
            if (this.add_new_features_button != null) {
                this.add_new_features_button.setOnAction(new EventHandler<ActionEvent>() {
                    public void handle(ActionEvent event) {
                        DataBaseUtils.changeScene(event, "add_feature_to_rooms.fxml");
                    }
                });
            } else {
                System.out.println("buton bos");
            }
        }

        if (this.delete_room_button != null) {
            final String sqlDelete = "DELETE FROM room WHERE id = ?";
            this.delete_room_button.setOnAction(new EventHandler<ActionEvent>() {
                public void handle(ActionEvent event) {
                    try {
                        UpodHotel.delete(sqlDelete, Integer.valueOf(Rooms.this.room_id_field.getText()));
                    } catch (SQLException var3) {
                        throw new RuntimeException(var3);
                    }
                }
            });
        }



        try {
            loadFeatureNames();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    ObservableList<RoomTableController> reservationListe = FXCollections.observableArrayList();


    public void getValues(TableView<RoomTableController> table) throws SQLException {
        Connection connection = null;
        PreparedStatement psInsert = null;
        ResultSet resultSet = null;

        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/upod-hotel", "root", "derya12sude34");
        } catch (SQLException var6) {
            throw new RuntimeException(var6);
        }

        psInsert = connection.prepareStatement("SELECT * FROM room");
        resultSet = psInsert.executeQuery();

        while(resultSet.next()) {
            reservationListe.add(new RoomTableController(resultSet.getString("room_name"), resultSet.getInt("id"), resultSet.getInt("capacity"), resultSet.getFloat("price")));
            this.column_room_id.setCellValueFactory(new PropertyValueFactory("id"));
            this.column_room_name.setCellValueFactory(new PropertyValueFactory("name"));
            this.column_room_capacity.setCellValueFactory(new PropertyValueFactory("capacity"));
            this.column_room_price.setCellValueFactory(new PropertyValueFactory("price"));
            if (table != null) {
                table.setItems(reservationListe);
            } else {
                System.out.println("table null");
            }
        }

    }

    public void deleteRoom() {
    }

    public void getSelected(MouseEvent event) {
        this.index = this.rooms_table.getSelectionModel().getSelectedIndex();
        if (this.index > -1) {
            this.room_name_ID.setText(((Integer)this.column_room_id.getCellData(this.index)).toString());
            this.room_name_field.setText((String)this.column_room_name.getCellData(this.index));
            this.room_price_field.setText(((Float)this.column_room_price.getCellData(this.index)).toString());
            this.room_capacity_field.setText(((Integer)this.column_room_capacity.getCellData(this.index)).toString());
        }
    }

    public void Edit() {
        try {
            Connection connection = null;
            PreparedStatement psUpdate = null;

            try {
                connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/upod-hotel", "root", "derya12sude34");
            } catch (SQLException var9) {
                throw new RuntimeException(var9);
            }

            String value1 = this.room_name_field.getText();
            String value2 = this.room_capacity_field.getText();
            String value3 = this.room_price_field.getText();
            String value4 = this.room_name_ID.getText();

            String sql = "UPDATE room SET  room_name = ?, capacity = ?, price = ? WHERE id = ?";
            psUpdate = connection.prepareStatement(sql);
            psUpdate.setString(1, value1);
            psUpdate.setString(2, value2);
            psUpdate.setString(3, value3);
            psUpdate.setString(4, value4);
            int rowsAffected = psUpdate.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Update successful");
            } else {
                System.out.println("No rows affected. Check your WHERE clause.");
            }


            this.reservationListe.clear();

            try {
                this.getValues(this.rooms_table);
            } catch (SQLException var5) {
                throw new RuntimeException(var5);
            }

            this.rooms_table.refresh();

        } catch (SQLException var10) {
            throw new RuntimeException(var10);
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

        room_feature_combobox.setItems(featureNames);
    }
}

