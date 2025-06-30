package com.shrinkurl;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.*;

public class UrlRedirectHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String path = exchange.getRequestURI().getPath().substring(1); 
        String longUrl = getLongUrlFromDb(path);

        if (longUrl != null) {
            exchange.getResponseHeaders().set("Location", longUrl);
            exchange.sendResponseHeaders(302, -1);
        } else {
            String response = "404 - Short URL not found";
            exchange.sendResponseHeaders(404, response.length());
            try (OutputStream os = exchange.getResponseBody()) {
                os.write(response.getBytes());
            }
        }
    }

    private String getLongUrlFromDb(String alias) {
        String sql = "SELECT long_url FROM urls WHERE short_code = ?";
        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, alias);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return rs.getString("long_url");
            }
        } catch (SQLException e) {
            e.printStackTrace(); 
        }
        return null;
    }
}
