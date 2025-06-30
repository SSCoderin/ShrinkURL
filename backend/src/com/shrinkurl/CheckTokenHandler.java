package com.shrinkurl;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashSet;
import java.util.Set;

public class CheckTokenHandler implements HttpHandler {

    // Dummy token store (acts like a session/token storage)
    private static final Set<String> validTokens = new HashSet<>();

    // Method to be called when a user logs in successfully (from LoginHandler)
    public static void addToken(String token) {
        validTokens.add(token);
    }

    // Method to remove token (e.g., on logout)
    public static void removeToken(String token) {
        validTokens.remove(token);
    }

    // Method to check if a token exists
    private boolean isValidToken(String token) {
        return validTokens.contains(token);
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        // Enable CORS
        exchange.getResponseHeaders().add("Access-Control-Allow-Origin", "*");
        exchange.getResponseHeaders().add("Access-Control-Allow-Headers", "Content-Type, Authorization");
        exchange.getResponseHeaders().add("Access-Control-Allow-Methods", "POST, OPTIONS");

        if ("OPTIONS".equalsIgnoreCase(exchange.getRequestMethod())) {
            exchange.sendResponseHeaders(204, -1);
            return;
        }

        if (!"POST".equalsIgnoreCase(exchange.getRequestMethod())) {
            sendResponse(exchange, 405, "{\"message\": \"Method not allowed\"}");
            return;
        }

        String authHeader = exchange.getRequestHeaders().getFirst("Authorization");

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            sendResponse(exchange, 401, "{\"message\": \"Missing or invalid Authorization header\"}");
            return;
        }

        String token = authHeader.substring("Bearer ".length()).trim();

        if (isValidToken(token)) {
            sendResponse(exchange, 200, "{\"message\": \"Token is valid\"}");
        } else {
            sendResponse(exchange, 401, "{\"message\": \"Invalid or expired token\"}");
        }
    }

    private void sendResponse(HttpExchange exchange, int statusCode, String body) throws IOException {
        exchange.getResponseHeaders().set("Content-Type", "application/json");
        exchange.sendResponseHeaders(statusCode, body.length());
        try (OutputStream os = exchange.getResponseBody()) {
            os.write(body.getBytes());
        }
    }
}
