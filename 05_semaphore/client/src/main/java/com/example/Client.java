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
        String host = "192.168.1.8"; // Default host
        int port = 5000;

        if (args.length > 0) {
            host = args[0];
        }

        System.out.println("Connecting to " + host + ":" + port + "...");
        System.out.println("Type 'exit' or 'quit' to close the connection.\n");

        try (
            Socket socket = new Socket(host, port);
            PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            BufferedReader consoleReader = new BufferedReader(new InputStreamReader(System.in));
        ) {

            String command = "";
            System.out.println("Write command (read ID / update ID count): ");
            while ((command = consoleReader.readLine()) != null) {
                
                if (command == null || command.equalsIgnoreCase("exit") || command.equalsIgnoreCase("quit")) {
                    System.out.println("Exit !!");
                    break;
                }

                if (command.trim().isEmpty()) {
                    System.out.println("Empty !!!");
                    continue;
                }

                System.out.println("command");
                writer.println(command);
                String response = reader.readLine();
                
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
