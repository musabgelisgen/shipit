package com.db.shipit.utils;

import com.db.shipit.ShipitApplication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.jdbc.core.JdbcTemplate;

import java.sql.*;

public class DataSourceGenerator {

    static final String user = "root";
    static final String password = "";
    static final String url = "jdbc:mysql://localhost";
    static final String table_name = "/shipit";

    public static void main(String[] args) {
        try {
            generateDataResources();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void generateDataResources() throws SQLException {
        Connection connection = null;
        Statement statement = null;
        ResultSet rs;

        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection(url + table_name, user, password);
            statement = connection.createStatement();
        } catch (
                SQLException e) {
            System.out.println("Connection to the database can't be made. Check credentials. Check the server is up and ports are open");
            System.exit(1);
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
            System.exit(1);
        }

        statement.executeUpdate("DROP TABLE IF EXISTS Customer;");
        statement.executeUpdate("DROP TABLE IF EXISTS CustomerService;");
        statement.executeUpdate("DROP TABLE IF EXISTS User;");
        statement.executeUpdate("DROP TABLE IF EXISTS Branch;");


        statement.executeUpdate("CREATE TABLE Branch(" +
                "name VARCHAR(10)," +
                "city_name VARCHAR(10) NOT NULL," +
                "PRIMARY KEY(name)," +
                "UNIQUE KEY(city_name));");


        statement.executeUpdate("CREATE TABLE User(" +
                "ID CHAR(6)," +
                "email VARCHAR(30) NOT NULL," +
                "encrypted_password VARCHAR(12) NOT NULL," +
                "first_name VARCHAR(20) NOT NULL," +
                "last_name VARCHAR(20) NOT NULL," +
                "PRIMARY KEY (ID));");


        statement.executeUpdate("CREATE TABLE Customer(" +
                "ID CHAR(6)," +
                "city_name VARCHAR(10) NOT NULL," +
                "phone_number NUMERIC(11) NOT NULL," +
                "address VARCHAR(50) NOT NULL," +
                "credits NUMERIC(10) NOT NULL DEFAULT 0," +
                "PRIMARY KEY(ID)," +
                "FOREIGN KEY(ID) REFERENCES User(ID)," +
                "FOREIGN KEY(city_name) REFERENCES Branch(city_name)," +
                "CHECK(credits >= 0));");


        statement.executeUpdate("CREATE TABLE CustomerService(" +
                "ID CHAR(6)," +
                "branch_name VARCHAR(10)," +
                "salary NUMERIC(8,2)," +
                "PRIMARY KEY(ID)," +
                "FOREIGN KEY(ID) REFERENCES User(ID)," +
                "FOREIGN KEY(branch_name) REFERENCES Branch(name)," +
                "CHECK(salary >= 0));");


    }
}
