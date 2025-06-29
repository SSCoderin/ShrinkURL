package com.shrinkurl;

import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;
import com.google.gson.Gson;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.sql.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RegisterHandler implements HttpHandler {
    private static final Logger logger = LoggerFactory.getLogger(RegisterHandler.class);
    private static final Gson gson = new Gson();

    @Override
    public void handle(HttpExchange exchange) throws IOException {
            exchange.getResponseHeaders().add("Access-Control-Allow-Origin", "*");
    exchange.getResponseHeaders().add("Access-Control-Allow-Methods", "GET, POST, OPTIONS");
    exchange.getResponseHeaders().add("Access-Control-Allow-Headers", "Content-Type, Authorization");

        if (!exchange.getRequestMethod().equalsIgnoreCase("POST")) {
            exchange.sendResponseHeaders(405, -1); // Method Not Allowed
            return;
        }

        InputStreamReader isr = new InputStreamReader(exchange.getRequestBody(), StandardCharsets.UTF_8);
        BufferedReader br = new BufferedReader(isr);
        StringBuilder body = new StringBuilder();
        String line;
        while ((line = br.readLine()) != null) {
            body.append(line);
        }

        RegisterRequest request = gson.fromJson(body.toString(), RegisterRequest.class);

        String username = request.username;
        String password = request.password;

        String response;
        try (Connection conn = Database.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement("INSERT INTO users (username, password) VALUES (?, ?)");
            stmt.setString(1, username);
            stmt.setString(2, password); // Hash in production
            stmt.executeUpdate();

            response = "{\"message\":\"Registration successful\"}";
            exchange.sendResponseHeaders(200, response.length());
        } catch (SQLException e) {
            logger.error("Registration failed for user '{}': {}", username, e.getMessage());
            response = "{\"message\":\"Username already exists\"}";
            exchange.sendResponseHeaders(409, response.length()); // Conflict
        }

        try (OutputStream os = exchange.getResponseBody()) {
            os.write(response.getBytes());
        }
    }

    static class RegisterRequest {
        String username;
        String password;
    }
}
