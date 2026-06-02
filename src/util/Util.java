package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Util {
        private static final String DB_NAME = "DB2026Team10";      // change to your team DB name
    private static final String URL = "jdbc:mysql://localhost:3306/" + DB_NAME + "?serverTimezone=Asia/Seoul";
    private static final String USER = DB_NAME;                 // DB ID matches DB name per spec
    private static final String PASSWORD = DB_NAME;             // DB password matches DB name per spec

    public static Connection getConnection() throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.err.println("Couldn't find MySQL JDBC driver.");
            e.printStackTrace();
        }
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    public static void closeConnection(Connection conn) {
        try {
            if (conn != null && !conn.isClosed()) {
                conn.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}