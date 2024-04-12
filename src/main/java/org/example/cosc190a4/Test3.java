package org.example.cosc190a4;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Test3 {


    static int messageIDCount = 1;
    static Connection connection;
    static String DatabasePath = "data_files/Assignment-4-2024.accdb" ;




    public static void main(String[] args) throws SQLException, ClassNotFoundException {

        connection = DBHelper.connect(DBHelper.DB_TYPE.ACCESS, DatabasePath);
        getMessageIDCount();
//        String query = "SELECT last, first FROM Table1";
//
//
//        ResultSet resultSet = DBHelper.execute(connection, query);

        System.out.println(messageIDCount);
    }

    private static void getMessageIDCount() throws SQLException {
        String countRowQuery = "SELECT COUNT(*) FROM Table1;";
        ResultSet countIDMessageRS = DBHelper.execute(connection, countRowQuery);
        if (countIDMessageRS.next()){
            messageIDCount = countIDMessageRS.getInt(1);
        }
        countIDMessageRS.close();
    }



}
