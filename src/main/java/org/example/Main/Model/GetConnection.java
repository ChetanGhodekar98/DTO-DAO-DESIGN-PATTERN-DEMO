package org.example.Main.Model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class GetConnection {
    static Connection con;
    static Connection GetConnection(){
        try {
            con= DriverManager.getConnection("jdbc:mysql://localhost:3306/1ejm10","root","sql123");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return con;

    }
}
