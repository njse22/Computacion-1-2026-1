package com.example;

import java.net.ServerSocket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


// Servidor

public class App {
    public String getGreeting() {
        return "Hello World!";
    }

    public static void main(String[] args) {
        ExecutorService pool = Executors.newFixedThreadPool(3); 
        // Try with resources 
        try (ServerSocket socket = new ServerSocket(5000)) {

            pool.execute(new ClientHandler(socket.accept()));
            
        } catch (Exception e) {
            // TODO: handle exception
            pool.shutdown();
        }
    }
}
