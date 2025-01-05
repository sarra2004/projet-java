package projet;

import java.sql.*;

public class DatabaseConnection {
    
    private static final String url="jdbc:sqlite:users.db";

    public static Connection connect(){
        try {
            Connection conn = DriverManager.getConnection(url);
            System.out.println("connection established!");
            return conn;
        } catch (SQLException e) {
            System.err.println("Connection error: " + e.getMessage());
            return null;
        }
    }
}
