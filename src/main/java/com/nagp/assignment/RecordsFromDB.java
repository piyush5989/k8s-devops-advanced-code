package com.nagp.assignment;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.sql.*;

@SpringBootApplication
@RestController
public class RecordsFromDB {
    private static final String DB_URL = "jdbc:mysql://" + System.getenv("DB_HOST") + ":3306/" + System.getenv("DB_NAME");
    private static final String DB_USER = System.getenv("DB_USER");
    private static final String DB_PASSWORD = System.getenv("DB_PASSWORD");

    public static void main(String[] args) {
        SpringApplication.run(RecordsFromDB.class, args);
    }

    @GetMapping("/")
    public String home() {
        return String.format("Hello, this is my API service to fetch records from DB: [%s]", DB_URL);
    }

    @GetMapping("/records")
    public String getRecords() {
        StringBuilder builder = new StringBuilder(String.format("<br/>Fetching records from pod: [%s]<br/><br/>", System.getenv("HOSTNAME")));
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            String sql = "SELECT * FROM employee";
            try (Statement statement = connection.createStatement();
                 ResultSet resultSet = statement.executeQuery(sql)) {
                int columnCount = resultSet.getMetaData().getColumnCount();
                int[] columnSizes = new int[columnCount];
                String[] columnNames = new String[columnCount];

                for (int i = 1; i <= columnCount; i++) {
                    columnNames[i - 1] = resultSet.getMetaData().getColumnName(i);
                    columnSizes[i - 1] = Math.max(resultSet.getMetaData().getColumnDisplaySize(i), columnNames[i - 1].length());
                }

                // Print the column headers
                Utils.printSeparator(columnSizes);
                Utils.printRow(columnNames, columnSizes);
                Utils.printSeparator(columnSizes);

                // Print the rows
                while (resultSet.next()) {
                    String[] row = new String[columnCount];
                    for (int i = 1; i <= columnCount; i++) {
                        row[i - 1] = resultSet.getString(i);
                    }
                    Utils.printRow(row, columnSizes);
                }

                Utils.printSeparator(columnSizes);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            builder.append(e.getMessage());
        }
        return builder.toString();
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

