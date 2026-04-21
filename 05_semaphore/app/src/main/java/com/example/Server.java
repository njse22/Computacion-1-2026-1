package com.example;

import java.net.ServerSocket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

import com.example.model.Warehouse;

public class Server {

    private ExecutorService pool; 
    private Semaphore semaphore; 
    private ServerSocket socket;
    private Warehouse warehouse;

    public String getGreeting() {
        return "Hello World!";
    }

    public Server() {
        this.pool = Executors.newFixedThreadPool(5); 
        this.semaphore = new Semaphore(3); // Example limit for simultaneous processing
        this.warehouse = new Warehouse(); // Shared instance
    }

    public static void main(String[] args) {
        Server server = new Server(); 
        server.setPort(5000);

        System.out.println("Server started on port 5000...");

        try {
            while (true) {
                // Accepts connection and passes shared warehouse and semaphore
                server.getPool().execute(
                        new ClientHandler(server.getSocket().accept(), 
                            server.getWarehouse(), server.getSemaphore()));
            }
        } catch (Exception e) {
            System.err.println("Server error: " + e.getMessage());
            server.getPool().shutdown();
        }
    }

    public Warehouse getWarehouse() {
        return warehouse;
    }

    public Semaphore getSemaphore() {
        return semaphore;
    }

    public ServerSocket getSocket() {
        return socket;
    }

    public ExecutorService getPool() {
        return pool;
    }

    public void setPort(int port) {
        try {
            this.socket = new ServerSocket(port);
        } catch (Exception e) {
            System.err.println("Error setting port: " + e.getMessage());
        }
    }
}
