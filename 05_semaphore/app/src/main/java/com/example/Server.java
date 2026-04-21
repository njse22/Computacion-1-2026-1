package com.example;

import java.net.ServerSocket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

import com.example.model.Warehouse;

public class Server {

    // ThreadPool, manejar multiples conexiones
    private ExecutorService pool; 
    // Semaphore, bloquear recursos 
    private Semaphore semaphore; 
    // Server socket para habilitar un puerto 
    private ServerSocket socket;
    // Controlador de logica de negocio
    private Warehouse warehouse;

    public String getGreeting() {
        return "Hello World!";
    }

    public Server() {
        // Inicializo el ThreadPool para habilitar 5 conexiones simultaneas
        this.pool = Executors.newFixedThreadPool(5); 

        // Solo 3 clientes pueden hacer operaciones de forma 
        // simultanea
        this.semaphore = new Semaphore(3); // Example limit for simultaneous processing
        this.warehouse = new Warehouse(); // Shared instance
    }

    public static void main(String[] args) {
        Server server = new Server(); 
        // inicializar el puerto de escuccha del servidor
        server.setPort(5000);

        System.out.println("Server started on port 5000...");

        try {
            while (true) {
                // Accepts connection and passes shared warehouse and semaphore
                // gestionar las solicitudes del cliente en hilos distintos
                server.getPool().execute( // runnable 
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
