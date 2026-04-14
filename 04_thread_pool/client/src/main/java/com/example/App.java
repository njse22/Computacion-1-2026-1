package com.example;

import java.io.BufferedWriter;
import java.io.OutputStreamWriter;
import java.net.Socket;

public class App {
    public String getGreeting() {
        return "Hello World!";
    }

    public static void main(String[] args) {
        try {
            Socket socket = new Socket("127.0.0.1", 5000); 

            BufferedWriter writer = new BufferedWriter(
                    new OutputStreamWriter(socket.getOutputStream()));

            
            String msj = "Hola desde el cliente: " + socket.getLocalPort();
            writer.write(msj);

            writer.flush();
            writer.close();
            socket.close();


        } catch (Exception e) {
            // TODO: handle exception
        }
    }
}
