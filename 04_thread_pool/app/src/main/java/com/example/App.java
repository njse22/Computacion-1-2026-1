package com.example;

import java.net.ServerSocket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;


// Servidor

public class App {
    public String getGreeting() {
        return "Hello World!";
    }

    public static void main(String[] args) {
        ExecutorService pool = Executors.newFixedThreadPool(3); 

        // Semaphore 
        Semaphore semaphore = new Semaphore(3);
        // Try with resources 
        try (ServerSocket socket = new ServerSocket(5000)) {

            semaphore.acquire();
            pool.execute(new ClientHandler(socket.accept()));
            
        } catch (Exception e) {
            // TODO: handle exception
            pool.shutdown();
        }
        finally{
            semaphore.release();
        }
    }
}
