package com.example;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.Socket;

public class ClientHandler implements Runnable {

    // la acción de gestionar clientes

    private Socket clientSocket; 

    public ClientHandler(Socket clientSocket){
        this.clientSocket = clientSocket; 
    }

    @Override
    public void run(){

        try {
            BufferedReader socketReader = new BufferedReader(
                    new InputStreamReader(clientSocket.getInputStream()));

            String mjs = "";
            System.out.println("ClientHandler::on method run ...");
            while ( (mjs = socketReader.readLine()) != null) {

                if (mjs.equals("EXIT")) {
                    socketReader.close();
                    clientSocket.close();
                }

                System.out.println(clientSocket.getInetAddress());
                System.out.println(clientSocket.getPort());
                System.out.println(mjs);
            }

        } catch (Exception e) {
            // TODO: handle exception
        }



    }
    
    
}
