package com.nagp.assignment;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.*;

@SpringBootApplication
@RestController
public class RecordsFromDB {

    public static void main(String[] args) {
        SpringApplication.run(RecordsFromDB.class, args);
    }

    @GetMapping("/")
    public String home() {
        return "Hello, this is my API service!";
    }

    @GetMapping("/records")
    public String getRecords() {
        return "Retrieving records from the database!\n\n" + fromDB();
    }

    private String fromDB() {
        String url = "jdbc:mysql://localhost:3306/dummy_records";
        String username = "root";
        String password = "interOP@123";
        StringBuilder builder = new StringBuilder(String.format("<br/><br/>Fetching records from pod: [%s]<br/><br/>", System.getenv("HOSTNAME")));
        try (Connection connection = DriverManager.getConnection(url, username, password)) {
            String sql = "SELECT * FROM employee";
            try (Statement statement = connection.createStatement();
                 ResultSet resultSet = statement.executeQuery(sql)) {
                while (resultSet.next()) {
                    int id = resultSet.getInt("id");
                    String name = resultSet.getString("name");
                    String record = String.format(" -> ID: [%d], Name: [%s]...<br/>", id, name);
                    System.out.println(record);
                    builder.append(record);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            builder.append(e.getMessage());
        }
        return builder.toString();
    }
}

