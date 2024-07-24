package com.example.mrdoob.controller;

import java.sql.*;

public class DatabaseController {

    private static boolean isDatabaseExist = false;


    public static Connection connect() {
        // Get database name
        String dbFilePath = "test.db";
        String url = "jdbc:sqlite:" + dbFilePath;
        Connection conn = null;
        try {
            // Verify if database already exist
            if (!isDatabaseExist) {
                DatabaseController databaseController = new DatabaseController();
                databaseController.createDatabase(url);
                isDatabaseExist = true;
            }

            Class.forName("org.sqlite.JDBC");
            conn = DriverManager.getConnection(url);

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return conn;
    }

    public static void addTest(String name, String status, Boolean check, String date) {
        // Add test results in TestTable
        String sql = "INSERT INTO TestTable (test_name, test_status, test_check,test_date) VALUES (?, ?, ?,?)";

        try (Connection conn = connect()) {
            assert conn != null;

            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, name);
            stmt.setString(2, status);
            stmt.setString(3, String.valueOf(check));
            stmt.setString(4, date);
            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public void createDatabase(String url) {

        // Create table for database if not exist
        String tableExistQuery = "SELECT name FROM sqlite_master WHERE type='table' AND name='TestTable'";

        String createTableSql = "CREATE TABLE TestTable ("
                + " test_id INTEGER NOT NULL PRIMARY KEY,"
                + " test_name TEXT NOT NULL,"
                + " test_status TEXT CHECK (test_status IN ('Only', 'All') ) NOT NULL,"
                + " test_check TEXT NOT NULL CHECK (test_check IN ('true', 'false') ),"
                + " test_date TEXT NOT NULL)";

        try (Connection conn = DriverManager.getConnection(url);
             Statement stmt = conn.createStatement()) {

            // Check if the table exists
            try (PreparedStatement pstmt = conn.prepareStatement(tableExistQuery);
                 ResultSet rs = pstmt.executeQuery()) {

                if (!rs.next()) {
                    // Create table if not exist
                    stmt.execute(createTableSql);
                    System.out.println("Table TestTable created.");
                } else {
                    System.out.println("Table TestTable already exists.");
                }
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
