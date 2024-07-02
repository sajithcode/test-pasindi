package testLMS;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class DatabaseConnection {
    private static final String url= "jdbc:mysql://localhost:3306/library_db";
    private static final String user="root";
    private static final String password="Enter Your Password"; //Enter your password

    public static Connection getConnection(){
        try {
            return DriverManager.getConnection("jdbc:mysql://localhost:3306/library_db", "root", "Enter Your Password");  //Enter your password
        } catch (SQLException var1) {
            SQLException e = var1;
            System.out.println("Failed to connect to the database. Error: " + e.getMessage());
            return null;
        }
    }
    
//Check database is connected or not
    public static boolean isDBConnected() {
        Connection connection = getConnection();
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException var2) {
                SQLException e = var2;
                System.out.println("Failed to close the database connection. Error: " + e.getMessage());
            }

            return true;
        } else {
            return false;
        }
    }

    public static void executeUpdate(String sql, Object... args) throws SQLException {
        try (Connection connection = getConnection()) {
            assert connection != null;
            try (PreparedStatement stmt = connection.prepareStatement(sql)) {
                for (int i = 0; i < args.length; i++) {
                    stmt.setObject(i + 1, args[i]);
                }
                stmt.executeUpdate();
            }
        }
    }
    public static ResultSet executeQuery(String sql, Object... args) throws SQLException {
        Connection connection = getConnection();
        if (connection != null) {
            PreparedStatement stmt = connection.prepareStatement(sql);
            for (int i = 0; i < args.length; i++) {
                stmt.setObject(i + 1, args[i]);
            }
            return stmt.executeQuery();
        } else {
            throw new SQLException("Failed to connect to the database.");
        }
    }
}
