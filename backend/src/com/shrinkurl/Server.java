package com.shrinkurl;

import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;

import java.net.InetSocketAddress;
import java.io.IOException;
import java.io.OutputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Server {
    private static final Logger logger = LoggerFactory.getLogger(Server.class);

    public static void main(String[] args) throws IOException {
        new Server().start();
    }

    public void start() throws IOException {
    Database.initialize(); 

    HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);
    server.createContext("/ping", new PingHandler());
    server.createContext("/register", new RegisterHandler());
    server.createContext("/login", new LoginHandler());


    server.setExecutor(null);
    server.start();

    logger.info("Server started at http://localhost:8080");
}


    static class PingHandler implements HttpHandler {
        private static final Logger logger = LoggerFactory.getLogger(PingHandler.class);

        @Override
        public void handle(HttpExchange exchange) throws IOException {
            String response = "pong123";
            exchange.sendResponseHeaders(200, response.length());
            try (OutputStream os = exchange.getResponseBody()) {
                os.write(response.getBytes());
            }
            logger.debug("Ping request handled.");
        }
    }
}
