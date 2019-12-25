package com.db.shipit.utils;
import java.sql.*;

public class DataSourceGenerator {

    static final String user = "admin";
    static final String password = "shipitadmin";
    static final String url = "jdbc:mysql://shipit.czpq5tzr95ng.us-east-1.rds.amazonaws.com";
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

        statement.executeUpdate("DROP TABLE IF EXISTS Message;");
        statement.executeUpdate("DROP TABLE IF EXISTS Report;");
        statement.executeUpdate("DROP TABLE IF EXISTS Subscription;");
        statement.executeUpdate("DROP TABLE IF EXISTS Package;");
        statement.executeUpdate("DROP TABLE IF EXISTS Route;");
        statement.executeUpdate("DROP TABLE IF EXISTS Customer;");
        statement.executeUpdate("DROP TABLE IF EXISTS CustomerService;");
        statement.executeUpdate("DROP TABLE IF EXISTS User;");
        statement.executeUpdate("DROP TABLE IF EXISTS Branch;");


        statement.executeUpdate("CREATE TABLE Branch(" +
                "name VARCHAR(10)," +
                "city_name VARCHAR(10) NOT NULL," +
                "PRIMARY KEY(name)," +
                "UNIQUE KEY(city_name));");

        statement.executeUpdate("INSERT INTO Branch VALUES" +
                "('Bilkent', 'Ankara')," +
                "('Kadikoy', 'Istanbul')," +
                "('Cesme', 'Izmir')," +
                "('Finike', 'Antalya')," +
                "('Datca', 'Bodrum')," +
                "('Of', 'Trabzon')");


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

        statement.executeUpdate("CREATE TABLE Route(" +
                "from_city VARCHAR(10)," +
                "hub VARCHAR(10)," +
                "to_city VARCHAR(10)," +
                "PRIMARY KEY(from_city,to_city)," +
                "FOREIGN KEY(from_city) REFERENCES Branch(city_name)," +
                "FOREIGN KEY(hub) REFERENCES Branch(city_name)," +
                "FOREIGN KEY(to_city) REFERENCES Branch(city_name));");

        statement.executeUpdate("CREATE TABLE Package(" +
                "package_id CHAR(6)," +
                "receiver_id CHAR(6) NOT NULL," +
                "sender_id CHAR(6) NOT NULL," +
                "delivery_date DATETIME," +
                "send_date DATETIME NOT NULL," +
                "payment_side VARCHAR(8) NOT NULL," +
                "payment_status VARCHAR(8) NOT NULL," +
                "delivery_type VARCHAR(10) NOT NULL," +
                "status VARCHAR(10) NOT NULL," +
                "package_type VARCHAR(11) NOT NULL," +
                "courier VARCHAR(20) NOT NULL," +
                "from_city VARCHAR(10) NOT NULL," +
                "curr_city VARCHAR(10) NOT NULL," +
                "to_city VARCHAR(10) NOT NULL," +
                "cost NUMERIC(10) NOT NULL," +
                "CHECK(payment_side IN ('sender','receiver'))," +
                "CHECK(payment_status IN ('paid','not paid'))," +
                "CHECK(delivery_type IN ('normal','fast','super fast'))," +
                "CHECK(status IN ('preparing', 'onTransfer', 'onBranch', 'delivered', 'declined'))," +
                " CHECK(package_type IN ('heavy','medium','lightweight'))," +
                "PRIMARY KEY(package_id)," +
                "FOREIGN KEY(from_city) REFERENCES Branch(city_name)," +
                "FOREIGN KEY(curr_city) REFERENCES Branch(city_name)," +
                "FOREIGN KEY(to_city) REFERENCES Branch(city_name)," +
                "FOREIGN KEY(receiver_id) REFERENCES Customer(ID)," +
                "FOREIGN KEY(sender_id) REFERENCES Customer(ID));");

        statement.executeUpdate("CREATE TABLE Subscription(" +
                "ID CHAR(6)," +
                "subscription_number INTEGER NOT NULL," +
                "subscription_tier INTEGER NOT NULL," +
                "used_package_rights INTEGER NOT NULL DEFAULT 0," +
                "start_date DATE NOT NULL," +
                "end_date DATE NOT NULL," +
                "PRIMARY KEY(ID, subscription_number)," +
                "FOREIGN KEY(ID) REFERENCES Customer(ID)," +
                "CHECK(subscription_tier >= 0)," +
                "CHECK((used_package_rights <= subscription_tier) AND (used_package_rights >= 0)));");

        statement.executeUpdate("CREATE TABLE Report(" +
                "report_id CHAR(6)," +
                "handler_id CHAR(6)," +
                "issuer_id CHAR(6) NOT NULL," +
                "package_id CHAR(6) NOT NULL," +
                "description VARCHAR(1000) NOT NULL," +
                "report_type VARCHAR(7) NOT NULL," +
                "issue_date DATETIME NOT NULL," +
                "result VARCHAR(8)," +
                "PRIMARY KEY(report_id)," +
                "FOREIGN KEY(handler_id) REFERENCES CustomerService(ID)," +
                "FOREIGN KEY(issuer_id) REFERENCES Customer(ID)," +
                "FOREIGN KEY(package_id) REFERENCES Package(package_id)," +
                "CHECK(report_type IN ('damaged','missing','wrong'))," +
                "CHECK(result IN (null,'positive','negative')));");
        
        statement.executeUpdate("CREATE TABLE Message(" +
                "report_id CHAR(6)," +
                "message_number INTEGER," +
                "text VARCHAR(1000) NOT NULL," +
                "date DATETIME NOT NULL," +
                "sender CHAR(6) NOT NULL," +
                "PRIMARY KEY(report_id,message_number)," +
                "FOREIGN KEY(report_id) REFERENCES Report(report_id)," +
                "FOREIGN KEY(sender) REFERENCES User(ID));");








    }
}
