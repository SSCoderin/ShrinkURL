package com.shrinkurl;

import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;
import com.google.gson.Gson;
import com.shrinkurl.models.User;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.sql.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoginHandler implements HttpHandler {
    private static final Logger logger = LoggerFactory.getLogger(LoginHandler.class);
    private static final Gson gson = new Gson();

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        exchange.getResponseHeaders().add("Access-Control-Allow-Origin", "*");
        exchange.getResponseHeaders().add("Access-Control-Allow-Methods", "POST, OPTIONS");
        exchange.getResponseHeaders().add("Access-Control-Allow-Headers", "Content-Type");

        if (exchange.getRequestMethod().equalsIgnoreCase("OPTIONS")) {
            exchange.sendResponseHeaders(204, -1);
            return;
        }

        if (!exchange.getRequestMethod().equalsIgnoreCase("POST")) {
            exchange.sendResponseHeaders(405, -1);
            return;
        }

        InputStreamReader isr = new InputStreamReader(exchange.getRequestBody(), StandardCharsets.UTF_8);
        BufferedReader br = new BufferedReader(isr);
        StringBuilder body = new StringBuilder();
        String line;
        while ((line = br.readLine()) != null) {
            body.append(line);
        }

        User loginRequest = gson.fromJson(body.toString(), User.class);
        String response;

        try (Connection conn = Database.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement(
                "SELECT * FROM users WHERE username = ? AND password = ?"
            );
            stmt.setString(1, loginRequest.getUsername());
            stmt.setString(2, loginRequest.getPassword());

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                response = "{\"message\":\"Login successful\",\"token\":\"dummy-token-" + loginRequest.getUsername() + "\"}";
                exchange.sendResponseHeaders(200, response.length());
                logger.info("✅ Login successful for user '{}'", loginRequest.getUsername());
            } else {
                response = "{\"message\":\"Invalid username or password\"}";
                exchange.sendResponseHeaders(401, response.length());
                logger.warn("Failed login attempt for user '{}'", loginRequest.getUsername());
            }
        } catch (SQLException e) {
            logger.error("Database error during login", e);
            response = "{\"message\":\"Server error\"}";
            exchange.sendResponseHeaders(500, response.length());
        }

        try (OutputStream os = exchange.getResponseBody()) {
            os.write(response.getBytes());
        }
    }
}
