package com.example;

import com.example.model.Warehouse;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.concurrent.Semaphore;

public class ClientHandler implements Runnable {

    // La conexión con el cliente
    private Socket socket;
    private Warehouse warehouse; 
    private Semaphore semaphore;

    public ClientHandler(Socket socket, Warehouse warehouse, Semaphore semaphore){
        this.socket = socket; 
        this.warehouse = warehouse;
        this.semaphore = semaphore;
    }

    @Override
    public void run(){
        try (
            // Lector de los comandos que entran por el socket 
            BufferedReader reader = 
                new BufferedReader(new InputStreamReader(socket.getInputStream()));

            // Escritor de los comandos que se envia al cliente desde el servidor
            PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);
        ) {
            System.out.println("Client connected: " + socket.getInetAddress());
            
            String line;

            while ((line = reader.readLine()) != null) {
                String[] command = line.split(" "); 
                String response = "Invalid command";

                // GET 
                // read 1 -> command[2]

                // POST 
                // update 1 10 -> command[3]

                if (command[0].equals("read")) {
                    if (command.length >= 2) {
                        try {
                            int id = Integer.parseInt(command[1]);
                            System.out.println("Reading product ID: " + id);
                            
                            // Using semaphore to control access
                            // Bloqueo el recurso 
                            semaphore.acquire();
                            try {
                                // de la logivca de negocio hago la consulta 
                                // necesaria 
                                response = warehouse.getProductById(id);
                            } finally {
                                // liberar el recurso
                                semaphore.release();
                            }
                        } catch (NumberFormatException e) {
                            response = "Invalid ID format";
                        }
                    } else {
                        response = "ID required for read";
                    }
                }
                else if (command[0].equals("update")) {
                    if (command.length >= 3) {
                        try {
                            int id = Integer.parseInt(command[1]); 
                            int count = Integer.parseInt(command[2]);
                            System.out.println(
                                    "Updating product ID: " + id + 
                                    " to count: " + count);
                            
                            // Using semaphore to control access
                            // Bloqueo el recurso 
                            semaphore.acquire();
                            try {
                                // se realiza la consulta 
                                String result = warehouse.updateProduct(id, count);
                                response = "Product update result for ID " 
                                    + id + ": " + result;
                            } finally {
                                // libero el recurso 
                                semaphore.release();
                            }
                        } catch (NumberFormatException e) {
                            response = "Invalid number format for update";
                        }
                    } else {
                        response = "ID and Count required for update";
                    }
                }
                
                writer.println(response); // Send response to client
            }
        } catch (Exception e) {
            System.err.println("Handler error: " + e.getMessage());
        } finally {
            try {
                socket.close();
            } catch (Exception e) {
                // Ignore
            }
        }
    }
}
