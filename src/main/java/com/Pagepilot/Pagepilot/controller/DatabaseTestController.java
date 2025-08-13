package com.Pagepilot.Pagepilot.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/test")
public class DatabaseTestController {

    @Autowired
    private DataSource dataSource;

    @GetMapping("/connection")
    public Map<String, Object> testConnection() {
        Map<String, Object> result = new HashMap<>();

        try (Connection connection = dataSource.getConnection()) {
            result.put("status", "SUCCESS");
            result.put("message", "Database connection successful!");
            result.put("database", connection.getCatalog());
            result.put("url", connection.getMetaData().getURL());
            result.put("driver", connection.getMetaData().getDriverName());
        } catch (Exception e) {
            result.put("status", "ERROR");
            result.put("message", "Database connection failed: " + e.getMessage());
        }

        return result;
    }

    @GetMapping("/tables")
    public Map<String, Object> checkTables() {
        Map<String, Object> result = new HashMap<>();

        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement()) {

            // Check if tables exist and count records
            String[] tables = {"Users", "Authors", "Books", "Loans", "Reviews", "Favorites", "Reservations"};
            Map<String, Integer> tableCounts = new HashMap<>();

            for (String table : tables) {
                try (ResultSet rs = statement.executeQuery("SELECT COUNT(*) FROM " + table)) {
                    if (rs.next()) {
                        tableCounts.put(table, rs.getInt(1));
                    }
                } catch (Exception e) {
                    tableCounts.put(table, -1); // Table doesn't exist or error
                }
            }

            result.put("status", "SUCCESS");
            result.put("message", "Table check completed");
            result.put("tableCounts", tableCounts);

        } catch (Exception e) {
            result.put("status", "ERROR");
            result.put("message", "Table check failed: " + e.getMessage());
        }

        return result;
    }

    @GetMapping("/sample-data")
    public Map<String, Object> getSampleData() {
        Map<String, Object> result = new HashMap<>();

        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement()) {

            // Get sample book data
            String query = "SELECT b.title, a.name as author, b.category, b.year " +
                    "FROM Books b JOIN Authors a ON b.author_id = a.id LIMIT 5";

            try (ResultSet rs = statement.executeQuery(query)) {
                Map<Integer, Map<String, Object>> books = new HashMap<>();
                int count = 0;

                while (rs.next()) {
                    Map<String, Object> book = new HashMap<>();
                    book.put("title", rs.getString("title"));
                    book.put("author", rs.getString("author"));
                    book.put("category", rs.getString("category"));
                    book.put("year", rs.getInt("year"));
                    books.put(++count, book);
                }

                result.put("status", "SUCCESS");
                result.put("message", "Sample data retrieved successfully");
                result.put("sampleBooks", books);
            }

        } catch (Exception e) {
            result.put("status", "ERROR");
            result.put("message", "Sample data retrieval failed: " + e.getMessage());
        }

        return result;
    }
}

