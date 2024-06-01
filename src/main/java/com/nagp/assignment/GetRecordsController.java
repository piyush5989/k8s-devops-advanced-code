package com.nagp.assignment;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.sql.*;

@SpringBootApplication
@RestController
public class GetRecordsController {
    private static final String DB_URL = "jdbc:mysql://" + System.getenv("DB_HOST") + ":3306/" + System.getenv("DB_NAME");
    private static final String DB_USER = System.getenv("DB_USER");
    private static final String DB_PASSWORD = System.getenv("DB_PASSWORD");
    private static final String DB_TABLE = System.getenv("DB_TABLE");

    public static void main(String[] args) {
        SpringApplication.run(GetRecordsController.class, args);
    }

    @GetMapping("/")
    public String home() {
        return String.format("Hey, this is an API service to fetch records from DB: [%s]<br/><br/>Please use <i>/records</i> endpoint to retrieve the records from the specified table: [%s]<br/><br/>Please use <i>/fib/{num}</i> endpoint to simulate the load on a pod to trigger HPA action...", DB_URL, DB_TABLE);
    }

    @GetMapping("/records")
    public String getRecords() {
        StringBuilder builder = new StringBuilder(String.format("<br/>Fetching records from pod: [%s]<br/><br/>", System.getenv("HOSTNAME")));
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            String sql = String.format("select * from %s", DB_TABLE);
            builder.append("<b>Query:</b> " + sql + "<br/><br/>");
            try (Statement statement = connection.createStatement();
                 ResultSet resultSet = statement.executeQuery(sql)) {
                int count = 0;
                while (resultSet.next()) {
                    int id = resultSet.getInt("id");
                    String name = resultSet.getString("name");
                    String record = String.format("Row %d => Id: [%d], Name: [%s]<br/>", ++count, id, name);
                    System.out.println(record);
                    builder.append(record);
                    builder.append(printDash(record.length()) + "<br/>");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            builder.append(e.getMessage());
        }
        return builder.toString();
    }

    private String printDash(int length) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            sb.append("-");
        }
        return sb.toString();
    }


    //This method is to simulate load on a pod to trigger HPA action
    @GetMapping("/fib/{num}")
    public String getFibonacci(@PathVariable("num") int num) {
        return String.format("Fibonacci of specified number: [%d] = [%d]<br/>", num, fib(num));
    }

    private long fib(int num) {
        if (num < 2) {
            return 1;
        }
        return fib(num - 2) + fib(num - 1);
    }
}

