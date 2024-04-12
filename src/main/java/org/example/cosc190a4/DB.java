package org.example.cosc190a4;

import java.sql.*;

public class DB {
    public static void main(String[] args) {
        try {
            Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");

            System.out.println("Driver loaded");

            Connection connection = DriverManager.getConnection("jdbc:ucanaccess://data_files/Assignment-4-2024.accdb");

            System.out.println("DB Connected");

            Statement statement = connection.createStatement();


            String query = "SELECT last, first FROM Table1";

            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()){
                System.out.println(resultSet.getString(1));
            }


        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        System.out.println("Driver loaded");
    }
}
