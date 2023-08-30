package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;


public class DatabaseUtil {
    String jdbcUrl = "jdbc:mysql://localhost:3306/mydatabase";
    String username;
    String password;
    Connection connection;

    DatabaseUtil(String jdbcUrl, String username, String password) {
        this.jdbcUrl = jdbcUrl;
        this.username = username;
        this.password = password;
    }

    public Connection connect() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            this.connection = DriverManager.getConnection(jdbcUrl, username, password);
            return this.connection;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public void close() {
        try {
            this.connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ResultSet executeQuery(String sqlQuery) {
        try {
            Statement statement = this.connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sqlQuery);
            return resultSet;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
