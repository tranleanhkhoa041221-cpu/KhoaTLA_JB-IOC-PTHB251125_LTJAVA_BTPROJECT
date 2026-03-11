package BaiTapProject.Utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionDB {
        private static final String driver = "org.postgresql.Driver";
        private static final String url = "jdbc:postgresql://localhost:5433/shop_db";
        private static final String username = "postgres";
        private static final  String password = "04122001";
        public static Connection getConnection() {
            try {
                Class.forName(driver);
                return DriverManager.getConnection(url, username, password);
            } catch (ClassNotFoundException | SQLException e) {
                throw new RuntimeException(e);
            }
            //       return null;
        }
        public static void closeConnection(Connection connection) {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }

        }
    }



