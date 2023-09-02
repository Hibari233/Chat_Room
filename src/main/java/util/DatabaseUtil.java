package util;

import java.sql.*;

import com.alibaba.fastjson2.*;

import javax.swing.plaf.nimbus.State;

public class DatabaseUtil {
    String jdbcUrl;
    String username;
    String password;
    Connection connection;

    public DatabaseUtil(String jdbcUrl, String username, String password) {
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

    public void testConnection() throws SQLException {
        Statement st = this.connection.createStatement();
        ResultSet rs = st.executeQuery("SELECT * FROM users");
        // print rs result
        while (rs.next()) {
            System.out.println(rs.getString("nickName"));
        }


    }

}