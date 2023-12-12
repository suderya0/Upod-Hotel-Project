package com.example.upodhotelproject;


import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.Iterator;
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
import javafx.scene.input.MouseEvent;

public class Customers extends UpodHotel implements Initializable {
    @FXML
    public TableColumn<CustomerTableController, Integer> column_customer_id;
    @FXML
    public TableColumn<CustomerTableController, String> column_customer_fullname;
    @FXML
    public TableColumn<CustomerTableController, Long> column_identity_number;
    @FXML
    public TableColumn<CustomerTableController, Long> column_customer_phone_number;
    @FXML
    public TableColumn<CustomerTableController, Date> column_customer_birthdate;
    @FXML
    public TableColumn<CustomerTableController, String> column_customer_description;
    @FXML
    public TableView<CustomerTableController> customer_table;
    @FXML
    public TextField customer_full_name;
    @FXML
    public TextField customer_identitiy_number;
    @FXML
    public TextField customer_phone_number;
    @FXML
    public TextField customer_Description;
    @FXML
    public DatePicker customer_birth_Date;
    @FXML
    public TextField customer_id;
    @FXML
    public Button customer_Save_button;
    @FXML
    public Button customer_delete_button;
    @FXML
    public Button customer_table_refresh;
    @FXML
    public Button come_back_home_page;
    @FXML
    public Button customer_update_button;
    ObservableList<CustomerTableController> customerListe = FXCollections.observableArrayList();
    int index = -1;

    public Customers() {
    }

    public void initialize(URL url, ResourceBundle resourceBundle) {
        if (this.customer_Save_button != null) {
            this.customer_Save_button.setOnAction((event) -> {
                LocalDate selectedDate = (LocalDate)this.customer_birth_Date.getValue();
                if (selectedDate != null) {
                    java.sql.Date sqlDate = java.sql.Date.valueOf(selectedDate);

                    try {
                        DataBaseUtils.create_customer(event, Long.valueOf(this.customer_identitiy_number.getText()), this.customer_full_name.getText(), Long.valueOf(this.customer_phone_number.getText()), sqlDate, this.customer_Description.getText());
                    } catch (SQLException var6) {
                        throw new RuntimeException(var6);
                    }
                } else {
                    System.out.println("selectedDate is null");
                }

                this.customerListe.clear();

                try {
                    this.getValues(this.customer_table);
                } catch (SQLException var5) {
                    throw new RuntimeException(var5);
                }

                this.customer_table.refresh();
            });
        } else {
            System.out.println("save_button is null");
        }

        if (this.come_back_home_page != null) {
            come_back_home_page(this.come_back_home_page);
        }

        if (this.customer_delete_button != null) {
            final String sqlDelete = "DELETE FROM customer WHERE identitiy_number = ?";
            this.customer_delete_button.setOnAction(new EventHandler<ActionEvent>() {
                public void handle(ActionEvent event) {
                    try {
                        UpodHotel.delete(sqlDelete, Long.valueOf(Customers.this.customer_identitiy_number.getText()));
                    } catch (SQLException var3) {
                        throw new RuntimeException(var3);
                    }
                }
            });
        }

        try {
            this.getValues(this.customer_table);
        } catch (SQLException var4) {
            throw new RuntimeException(var4);
        }
    }

    public void getValues(TableView<CustomerTableController> table) throws SQLException {
        Connection connection = null;
        PreparedStatement psInsert = null;
        ResultSet resultSet = null;

        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/upod-hotel", "root", "derya12sude34");
        } catch (SQLException var7) {
            throw new RuntimeException(var7);
        }

        psInsert = connection.prepareStatement("SELECT * FROM customer");
        resultSet = psInsert.executeQuery();

        while(resultSet.next()) {
            this.customerListe.add(new CustomerTableController(resultSet.getInt("id"), resultSet.getLong("identitiy_number"), resultSet.getLong("phone_number"), resultSet.getDate("birth_date"), resultSet.getString("full_name"), resultSet.getString("description")));
            this.column_customer_id.setCellValueFactory(new PropertyValueFactory("id"));
            this.column_customer_fullname.setCellValueFactory(new PropertyValueFactory("full_name"));
            this.column_customer_phone_number.setCellValueFactory(new PropertyValueFactory("phone_number"));
            this.column_customer_birthdate.setCellValueFactory(new PropertyValueFactory("birth_date"));
            this.column_identity_number.setCellValueFactory(new PropertyValueFactory("identity_number"));
            this.column_customer_description.setCellValueFactory(new PropertyValueFactory("description"));
            if (table != null) {
                table.setItems(this.customerListe);
            } else {
                System.out.println("table null");
            }
        }

        Iterator var5 = this.customerListe.iterator();

        while(var5.hasNext()) {
            CustomerTableController item = (CustomerTableController)var5.next();
            System.out.println(item.getFull_name());
        }

    }

    @FXML
    private void displayBirthday(ActionEvent event) {
        LocalDate today = LocalDate.now();
        LocalDate bDate = (LocalDate)this.customer_birth_Date.getValue();
        if (bDate != null && !bDate.isAfter(today)) {
            System.out.println("finally");
        } else {
            System.out.println("hi");
        }

    }

    public void getSelected(MouseEvent event) {
        this.index = this.customer_table.getSelectionModel().getSelectedIndex();
        if (this.index > -1) {
            this.customer_id.setText(((Integer)this.column_customer_id.getCellData(this.index)).toString());
            this.customer_identitiy_number.setText(String.valueOf((Long.parseLong(this.column_identity_number.getCellData(this.index).toString()))));
            this.customer_Description.setText(((String)this.column_customer_description.getCellData(this.index)));
            this.customer_phone_number.setText(((Long)this.column_customer_phone_number.getCellData(this.index)).toString());
            this.customer_full_name.setText(((String)this.column_customer_fullname.getCellData(this.index)));
            this.customer_birth_Date.setValue((new Date(((Date)this.column_customer_birthdate.getCellData(this.index)).getTime())).toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
        }
    }

    public void Edit() {
        try {
            Connection connection = null;
            PreparedStatement psUpdate = null;

            try {
                connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/upod-hotel", "root", "derya12sude34");
            } catch (SQLException var11) {
                throw new RuntimeException(var11);
            }

            String value1 = this.customer_id.getText();
            String value2 = this.customer_full_name.getText();
            String value3 = this.customer_Description.getText();
            String value4 = this.customer_phone_number.getText();
            String value6 = String.valueOf(this.customer_birth_Date.getValue());
            String value7 = this.customer_identitiy_number.getText();
            String sql = "UPDATE customer SET full_name = ?, phone_number = ?, description = ?, birth_date = ? , id = ? WHERE identitiy_number = ?";
            psUpdate = connection.prepareStatement(sql);
            psUpdate.setString(1, value2);
            psUpdate.setString(2, value4);
            psUpdate.setString(3, value3);
            psUpdate.setString(4, value6);
            psUpdate.setString(5, value1);
            psUpdate.setString(6, value7);
            int rowsAffected = psUpdate.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Update successful");
            } else {
                System.out.println("No rows affected. Check your WHERE clause.");
            }

            this.customerListe.clear();

            try {
                this.getValues(this.customer_table);
            } catch (SQLException var5) {
                throw new RuntimeException(var5);
            }

            this.customer_table.refresh();

        } catch (SQLException var12) {
            throw new RuntimeException(var12);
        }
    }
}
