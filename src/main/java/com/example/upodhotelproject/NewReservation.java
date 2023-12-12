package com.example.upodhotelproject;


import java.net.URL;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class NewReservation extends UpodHotel implements Initializable {

    ArrayList<Integer> avaliableRoomID = new ArrayList();
    @FXML
    public TableView new_res_room_table;

    @FXML
    public TableColumn column_avaliablerooms;
    @FXML
    public TextField new_res_room_id_field;
    @FXML
    public TextField home_page_cust_id_field;
    @FXML
    public TextField new_res_customer_field;
    @FXML
    public Button new_res_save_button;
    @FXML
    public Button new_res_search_button;
    @FXML
    public Button come_back_home_page;
    @FXML
    private DatePicker new_res_check_in;
    @FXML
    private DatePicker new_res_check_out;

    public NewReservation() {
    }

    public void initialize(URL url, ResourceBundle resourceBundle) {
        if (this.new_res_save_button != null) {
            this.new_res_save_button.setOnAction((event) -> {
                LocalDate check_in_date = (LocalDate)this.new_res_check_in.getValue();
                LocalDate check_out_date = (LocalDate)this.new_res_check_out.getValue();
                if (check_in_date != null && check_out_date != null && !check_in_date.equals(check_out_date)) {
                    Date sql_check_in = Date.valueOf(check_in_date);
                    Date sql_check_out = Date.valueOf(check_out_date);

                    try {
                        DataBaseUtils.createReservation(event, this.new_res_room_id_field.getText(), this.new_res_customer_field.getText(), check_in_date, check_out_date, Integer.parseInt(this.home_page_cust_id_field.getText()), this.avaliableRoomID);
                    } catch (SQLException var7) {
                        throw new RuntimeException(var7);
                    }
                } else {
                    System.out.println("same date");
                }


                String sql = "SELECT * FROM reservation;";


            });
        }

        if (this.come_back_home_page != null) {
            come_back_home_page(this.come_back_home_page);
        }

        System.out.println("save button null");
    }

    public void searchAvaliableRoom(ActionEvent event) throws SQLException {
        this.avaliableRoomID.add(0);
        String var10000 = String.valueOf(this.new_res_check_in.getValue());
        String sql = "SELECT id FROM room WHERE id NOT IN (SELECT room_id FROM reservation WHERE (\n    ('" + var10000 + "' >= check_in_date AND '" + String.valueOf(this.new_res_check_in.getValue()) + "' < check_out_date)\n    OR\n    ('" + String.valueOf(this.new_res_check_out.getValue()) + "' > check_in_date AND '" + String.valueOf(this.new_res_check_out.getValue()) + "' < check_out_date)\n    OR\n    ('" + String.valueOf(this.new_res_check_in.getValue()) + "' <= check_in_date AND '" + String.valueOf(this.new_res_check_out.getValue()) + "' >= check_out_date)\n));";
        this.getValues(this.new_res_room_table, sql);
    }

    public void getValues(TableView<NewReservationTableController> table, String sql) throws SQLException {
        Connection connection = null;
        PreparedStatement psInsert = null;
        ResultSet resultSet = null;
        ObservableList<NewReservationTableController> avaliableRoomList = FXCollections.observableArrayList();

        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/upod-hotel", "root", "derya12sude34");
        } catch (SQLException var9) {
            throw new RuntimeException(var9);
        }

        psInsert = connection.prepareStatement(sql);
        resultSet = psInsert.executeQuery();

        while(resultSet.next()) {
            avaliableRoomList.add(new NewReservationTableController(resultSet.getInt("id")));
        }

        this.column_avaliablerooms.setCellValueFactory(new PropertyValueFactory("room_id"));
        if (table != null) {
            table.setItems(avaliableRoomList);
        } else {
            System.out.println("table null");
        }

        Iterator var7 = avaliableRoomList.iterator();

        while(var7.hasNext()) {
            NewReservationTableController item = (NewReservationTableController)var7.next();
            this.avaliableRoomID.add(item.getRoom_id());
            System.out.println(item.getRoom_id());
        }

    }
}
