package DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
public class ConnectionDB {
    private static final String URL = "jdbc:mysql://localhost:3300/banco_teste";
    private static final String USER = "root";
    private static final String PASSWORD = "150610";

    public static Connection getConnection() throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (ClassNotFoundException e) {
            throw new SQLException("Erro ao carregar o driver JDBC", e);
        }
    }
}

