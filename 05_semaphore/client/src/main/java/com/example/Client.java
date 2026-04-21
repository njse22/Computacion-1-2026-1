package com.example;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Client {

    public String getGreeting() {
        return "Hello from Client!";
    }

    public static void main(String[] args) {
        String host = "192.168.131.198"; // Default host
        int port = 5000;

        System.out.println("Connecting to " + host + ":" + port + "...");
        System.out.println("Type 'exit' or 'quit' to close the connection.\n");

        try (

            // Socket del cliente -> conecta al servidor
            Socket socket = new Socket(host, port);

            // Escribe lo que el socket le recive desde el servidor
            PrintWriter writer = 
                new PrintWriter(socket.getOutputStream(), true);

            // Leer lo que el cliente le manda al servidor 
            BufferedReader readerSocket = 
                new BufferedReader(new InputStreamReader(socket.getInputStream()));


            // lector de la consola
            BufferedReader consoleReader = 
                new BufferedReader(new InputStreamReader(System.in));
        ) {

            String command = "";
            System.out.println("Write command (read ID / update ID count): ");


            while ((command = consoleReader.readLine()) != null) {
                
                if ( command.equalsIgnoreCase("exit") || 
                     command.equalsIgnoreCase("quit")) {

                    System.out.println("Exit !!");
                    break;
                }

                if (command.trim().isEmpty()) {
                    System.out.println("Empty !!!");
                    continue;
                }

                System.out.println("command");
                // El coamndo se escribe en el socket 
                writer.println(command);

                // Lee la información que le entra socket
                String response = readerSocket.readLine();
                
                if (response == null) {
                    System.out.println("Server disconnected.");
                    break;
                }

                System.out.println("Server response: " + response + "\n");
            }

            System.out.println("Session finished. Closing connection.");

        } catch (Exception e) {
            System.err.println("Client Error: " + e.getMessage());
        }
    }
}
