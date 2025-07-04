package com.shrinkurl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Database {
    private static final Logger logger = LoggerFactory.getLogger(Database.class);
    private static final String DB_URL = "jdbc:h2:./shrinkurl_db"; 

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL, "shiv", "123");
    }

    public static void initialize() {
        try (Connection conn = getConnection(); Statement stmt = conn.createStatement()) {
            String createTableSQL = "CREATE TABLE IF NOT EXISTS users (" +
                    "id INT AUTO_INCREMENT PRIMARY KEY, " +
                    "username VARCHAR(255) UNIQUE NOT NULL, " +
                    "password VARCHAR(255) NOT NULL" +
                    ")";
            stmt.execute(createTableSQL);
            String createUrlsTable = "CREATE TABLE IF NOT EXISTS urls (" +
    "id INT AUTO_INCREMENT PRIMARY KEY, " +
    "long_url VARCHAR(2048) NOT NULL, " +
    "short_code VARCHAR(255) UNIQUE NOT NULL" +
    ")";
stmt.execute(createUrlsTable);

            logger.info("Users table created or already exists.");
        } catch (SQLException e) {
            logger.error(" Failed to initialize database", e);
        }   
    }
}
