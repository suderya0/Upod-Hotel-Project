package com.example.upodhotelproject;


import java.io.IOException;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

public class DataBaseUtils {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/upod-hotel";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "derya12sude34";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
    }

    public static void closeResources(Connection connection, Statement statement, ResultSet resultSet) {
        try {
            if (resultSet != null) resultSet.close();
            if (statement != null) statement.close();
            if (connection != null) connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public DataBaseUtils() {
    }

    public static void changeScene(ActionEvent event, String fxmlFile) {
        Parent root = null;
        FXMLLoader loader = new FXMLLoader(DataBaseUtils.class.getResource(fxmlFile));

        try {
            root = loader.load();
        } catch (IOException var5) {
            System.out.println("hata");
        }

        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root, 800.0, 500.0));
        stage.show();
    }

    public static void create_customer(ActionEvent event, Long identitiy_number, String full_name, Long phone_number, Date birth_date, String description) throws SQLException {
        Connection connection = null;
        PreparedStatement psInsert = null;
        PreparedStatement psCheckCustomerExist = null;
        ResultSet resultSet = null;

        try {
        connection = getConnection();
        psCheckCustomerExist = connection.prepareStatement("SELECT*FROM customer WHERE identitiy_number = ?");
        psCheckCustomerExist.setLong(1, identitiy_number);
        resultSet = psCheckCustomerExist.executeQuery();

        try {
            Alert alert;
            if (resultSet.isBeforeFirst()) {
                System.out.println("Customer already exist!");
                alert = new Alert(AlertType.ERROR);
                alert.setContentText("Customer already exist!");
                alert.show();
            } else {
                psInsert = connection.prepareStatement("INSERT INTO customer ( identitiy_number,full_name, phone_number, birth_date, description) VALUES (?,?,?,?,?)");
                psInsert.setLong(1, identitiy_number);
                psInsert.setString(2, full_name);
                psInsert.setLong(3, phone_number);
                psInsert.setDate(4, birth_date);
                psInsert.setString(5, description);
                psInsert.executeUpdate();
                alert = new Alert(AlertType.INFORMATION);
                alert.setContentText("Created Succesfully!");
                alert.show();
            }
        } catch (SQLException var20) {
            var20.printStackTrace();
        } finally {
            try {
                if (resultSet != null) {
                    resultSet.close();
                }

                if (psInsert != null) {
                    psInsert.close();
                }
            } catch (SQLException var19) {
                var19.printStackTrace();
            }

         }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeResources(connection, psCheckCustomerExist, resultSet);
        }

    }


    public static void createRoom(ActionEvent event, int roomID, String roomName, int capacity, float price, String selectedFeature) throws SQLException {
        Connection connection = null;
        PreparedStatement psInsert = null;
        PreparedStatement psCheckRoomExist = null;
        ResultSet resultSet = null;

        try {
            connection = getConnection();

        psCheckRoomExist = connection.prepareStatement("SELECT*FROM room WHERE id = ?");
        psCheckRoomExist.setInt(1, roomID);
        resultSet = psCheckRoomExist.executeQuery();

        String sqlInsert = "INSERT INTO room_feature (room_id, feature_name) VALUES (?, ?)";
        psInsert = connection.prepareStatement(sqlInsert);
        psInsert.setInt(1, roomID);
        psInsert.setString(2, selectedFeature);
        psInsert.executeUpdate();

        try {
            Alert alert;
            if (resultSet.isBeforeFirst()) {
                System.out.println("Room name already exist!");
                alert = new Alert(AlertType.ERROR);
                alert.setContentText("Room name already exist!");
                alert.show();
            } else {
                psInsert = connection.prepareStatement("INSERT INTO room (id ,room_name, capacity, price) VALUES (?,?,?,?)");
                psInsert.setInt(1, roomID);
                psInsert.setString(2, roomName);
                psInsert.setInt(3, capacity);
                psInsert.setFloat(4, price);
                psInsert.executeUpdate();
                alert = new Alert(AlertType.INFORMATION);
                alert.setContentText("Created Succesfully!");
                alert.show();
            }
        } catch (SQLException var19) {
            var19.printStackTrace();
        } finally {
            try {
                if (resultSet != null) {
                    resultSet.close();
                }
                if (psInsert != null) {
                    psInsert.close();
                }
            } catch (SQLException var18) {
                var18.printStackTrace();
            }

        }} catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeResources(connection, psCheckRoomExist, resultSet);
        }

    }

    public static void createReservation(ActionEvent event, String room_id, String customer, LocalDate checkIn, LocalDate checkOut, int cust_id,  ArrayList avaliableRoomList) throws SQLException {

        Alert alert;
        if (Integer.valueOf(room_id) == 0) {
            alert = new Alert(AlertType.ERROR);
            alert.setContentText("ID can't be 0!");
            alert.show();
        }  else if (!avaliableRoomList.contains(Integer.valueOf(room_id))) {
            alert = new Alert(AlertType.ERROR);
            alert.setContentText("Room is not Avaliable!");
            alert.show();
        } else {

            PreparedStatement psInsert = null;
            ResultSet resultSet = null;

            Connection connection = null;
            try {
                connection = getConnection();


                try {
                    psInsert = connection.prepareStatement("INSERT INTO reservation (room_id, customer,  check_in_date, check_out_date, cust_id) VALUES (?,?,?,?,?)");
                    psInsert.setString(1, room_id);
                    psInsert.setString(2, customer);
                    psInsert.setDate(3, Date.valueOf(checkIn));
                    psInsert.setDate(4, Date.valueOf(checkOut));
                    psInsert.setInt(5, cust_id);
                    psInsert.executeUpdate();


                        try {
                            psInsert = connection.prepareStatement("INSERT INTO bills (cust_id, bill)\n" +
                                    "SELECT\n" +
                                    "    reservation.cust_id,\n" +
                                    "    room.price * DATEDIFF(reservation.check_out_date, reservation.check_in_date) AS total_price\n" +
                                    "FROM\n" +
                                    "    reservation\n" +
                                    "JOIN\n" +
                                    "    room ON reservation.room_id = room.id ;");


                            psInsert.executeUpdate();
                            Alert alert1 = new Alert(AlertType.INFORMATION);
                            alert1.setContentText("Created Succesfully!");
                            alert1.show();
                        } catch (SQLException var19) {
                            var19.printStackTrace();
                        } finally {
                            try {

                                if (psInsert != null) {
                                    psInsert.close();
                                }

                                if (connection != null) {
                                    connection.close();
                                }
                            } catch (SQLException var18) {
                                var18.printStackTrace();
                            }

                        }

                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            } finally {

            }
    }}

    public static void createService(ActionEvent event, int service_id,  String service_name, int price) throws SQLException {
        Connection connection = null;
        PreparedStatement psInsert = null;
        PreparedStatement psCheckFeatureExist = null;
        ResultSet resultSet = null;

        try {
            connection = getConnection();

        psCheckFeatureExist = connection.prepareStatement("SELECT*FROM service WHERE id = ?");
        psCheckFeatureExist.setInt(1, service_id);
        resultSet = psCheckFeatureExist.executeQuery();

        try {
            Alert alert;
            if (resultSet.isBeforeFirst()) {
                System.out.println("Room name already exist!");
                alert = new Alert(AlertType.ERROR);
                alert.setContentText("Room name already exist!");
                alert.show();
            } else {
                psInsert = connection.prepareStatement("INSERT INTO service (id , service_name, price) VALUES (?,?,?)");
                psInsert.setInt(1, service_id);
                psInsert.setString(2, service_name);
                psInsert.setInt(3,price);
                psInsert.executeUpdate();
                alert = new Alert(AlertType.INFORMATION);
                alert.setContentText("Created Succesfully!");
                alert.show();
            }
        } catch (SQLException var17) {
            var17.printStackTrace();
        } finally {
            try {
                if (resultSet != null) {
                    resultSet.close();
                }


                if (psInsert != null) {
                    psInsert.close();
                }

            } catch (SQLException var16) {
                var16.printStackTrace();
            }

        }} catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeResources(connection, psCheckFeatureExist, resultSet);
        }

    }

    public static void addBill(ActionEvent event, int service_id, Long cust_id){

            Connection connection = null;
            PreparedStatement psInsert = null;
            PreparedStatement psCheckFeatureExist = null;
            ResultSet resultSet = null;

            try {
                connection = getConnection();

            Alert alert;

            String sql = "UPDATE bills\n" +
                    "SET bill = bill + (\n" +
                    "    SELECT price\n" +
                    "    FROM service\n" +
                    "    WHERE id = ?\n" +
                    ")\n" +
                    "WHERE cust_id = ?;";

            try {
                psInsert = connection.prepareStatement(sql);
                // Assuming bills_cust_id_field and bills_service_id are text fields

                psInsert.setInt(1, service_id);
                psInsert.setLong(2, cust_id);

                psInsert.executeUpdate();

                alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setContentText("Updated Successfully!");
                alert.show();
            } catch (SQLException | NumberFormatException e) {
                throw new RuntimeException(e);
            } finally {

                try {
                    //if (resultSet != null) resultSet.close();
                    if (psInsert != null) psInsert.close();
                    if (connection != null) connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }} catch (SQLException e) {
                e.printStackTrace();
            } finally {
                closeResources(connection, null/*psCheckFeatureExist*/, null/*resultSet*/);
            }
        }



    public static void createFeature(ActionEvent event, int feature_id, String feature_name) throws SQLException {
        Connection connection = null;
        PreparedStatement psInsert = null;
        PreparedStatement psCheckFeatureExist = null;
        ResultSet resultSet = null;

        try {
            connection = getConnection();

        psCheckFeatureExist = connection.prepareStatement("SELECT*FROM feature WHERE id = ?");
        psCheckFeatureExist.setInt(1, feature_id);
        resultSet = psCheckFeatureExist.executeQuery();

        try {
            Alert alert;
            if (resultSet.isBeforeFirst()) {
                System.out.println("Feature ID already exist!");
                alert = new Alert(AlertType.ERROR);
                alert.setContentText("Feature ID already exist!");
                alert.show();
            } else {
                psInsert = connection.prepareStatement("INSERT INTO feature (id , feature_name) VALUES (?,?)");
                psInsert.setInt(1, feature_id);
                psInsert.setString(2, feature_name);
                psInsert.executeUpdate();
                alert = new Alert(AlertType.INFORMATION);
                alert.setContentText("Created Succesfully!");
                alert.show();
            }
        } catch (SQLException var17) {
            var17.printStackTrace();
        } finally {
            try {
                if (resultSet != null) {
                    resultSet.close();
                }

                if (psInsert != null) {
                    psInsert.close();
                }

            } catch (SQLException var16) {
                var16.printStackTrace();
            }

        }} catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeResources(connection, psCheckFeatureExist, resultSet);
        }

    }



}
