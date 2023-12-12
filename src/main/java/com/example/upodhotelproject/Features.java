package com.example.upodhotelproject;


import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.ResourceBundle;
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

public class Features extends UpodHotel implements Initializable {
    @FXML
    public TableView feature_table;
    @FXML
    public TableColumn column_feature_id;
    @FXML
    public TableColumn column_feature_name;
    @FXML
    public Button feature_save_button;
    @FXML
    public Button feature_delete_button;
    @FXML
    public TextField feature_name_field;
    @FXML
    public TextField feature_id_field;
    @FXML
    public TextField feature_id_for_save;
    @FXML
    public Button come_back_home_page;
    ObservableList<FeatureTableController> featureList = FXCollections.observableArrayList();

    public Features() {
    }

    public void initialize(URL url, ResourceBundle resourceBundle) {
        if (this.feature_save_button != null) {
            this.feature_save_button.setOnAction((event) -> {
                try {
                    DataBaseUtils.createFeature(event, Integer.valueOf(this.feature_id_for_save.getText()), this.feature_name_field.getText());
                } catch (SQLException var3) {
                    throw new RuntimeException(var3);
                }
            });
        }

        if (this.come_back_home_page != null) {
            come_back_home_page(this.come_back_home_page);
        }

        if (this.feature_delete_button != null) {
            final String sqlDelete = "DELETE FROM feature WHERE id = ?";
            this.feature_delete_button.setOnAction(new EventHandler<ActionEvent>() {
                public void handle(ActionEvent event) {
                    try {
                        UpodHotel.delete(sqlDelete, Integer.valueOf(Features.this.feature_id_field.getText()));
                    } catch (SQLException var3) {
                        throw new RuntimeException(var3);
                    }
                }
            });
        }

        try {
            this.getValues(this.feature_table);
        } catch (SQLException var4) {
            throw new RuntimeException(var4);
        }

    }

    public void getValues(TableView<FeatureTableController> table) throws SQLException {
        Connection connection = null;
        PreparedStatement psInsert = null;
        ResultSet resultSet = null;

        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/upod-hotel", "root", "derya12sude34");
        } catch (SQLException var6) {
            throw new RuntimeException(var6);
        }

        psInsert = connection.prepareStatement("SELECT * FROM feature");
        resultSet = psInsert.executeQuery();

        while(resultSet.next()) {
            this.featureList.add(new FeatureTableController(resultSet.getInt("id"), resultSet.getString("feature_name")));
            this.column_feature_id.setCellValueFactory(new PropertyValueFactory("id"));
            this.column_feature_name.setCellValueFactory(new PropertyValueFactory("feature_name"));
            if (table != null) {
                table.setItems(this.featureList);
            } else {
                System.out.println("table null");
            }
        }

        Iterator var5 = this.featureList.iterator();

        while(var5.hasNext()) {
            FeatureTableController item = (FeatureTableController) var5.next();
            System.out.println(item.getFeature_name());
        }

    }
}