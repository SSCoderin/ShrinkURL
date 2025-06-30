package com.shrinkurl;

import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;

import java.io.*;
import java.sql.*;
import java.util.Random;

import org.json.JSONObject;

public class ShortenHandler implements HttpHandler {

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        if (!exchange.getRequestMethod().equalsIgnoreCase("POST")) {
            exchange.sendResponseHeaders(405, -1); 
            return;
        }

        // Read JSON body
        InputStreamReader isr = new InputStreamReader(exchange.getRequestBody());
        BufferedReader br = new BufferedReader(isr);
        StringBuilder requestBody = new StringBuilder();
        String line;
        while ((line = br.readLine()) != null) {
            requestBody.append(line);
        }

        JSONObject body = new JSONObject(requestBody.toString());
        String longUrl = body.getString("longUrl");
        String customAlias = body.optString("customAlias", "");

        String shortCode = customAlias.isEmpty() ? generateRandomCode() : customAlias;

        if (aliasExists(shortCode)) {
            sendJson(exchange, 400, "Alias already in use.");
            return;
        }

        saveUrl(shortCode, longUrl);

        JSONObject res = new JSONObject();
        res.put("shortUrl", "http://localhost:8080/" + shortCode);
        sendJson(exchange, 200, res.toString());
    }

    private boolean aliasExists(String code) {
        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement("SELECT id FROM urls WHERE short_code = ?")) {
            stmt.setString(1, code);
            ResultSet rs = stmt.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
            return true; 
        }
    }

    private void saveUrl(String code, String longUrl) {
        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement("INSERT INTO urls (short_code, long_url) VALUES (?, ?)")) {
            stmt.setString(1, code);
            stmt.setString(2, longUrl);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private String generateRandomCode() {
        String chars = "abcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder sb = new StringBuilder();
        Random rand = new Random();
        for (int i = 0; i < 6; i++) {
            sb.append(chars.charAt(rand.nextInt(chars.length())));
        }
        return sb.toString();
    }

    private void sendJson(HttpExchange exchange, int status, String json) throws IOException {
        exchange.getResponseHeaders().add("Content-Type", "application/json");
        exchange.sendResponseHeaders(status, json.length());
        try (OutputStream os = exchange.getResponseBody()) {
            os.write(json.getBytes());
        }
    }
}
