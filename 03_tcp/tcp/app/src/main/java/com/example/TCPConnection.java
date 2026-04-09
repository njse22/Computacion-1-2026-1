package com.example;

import java.net.ServerSocket;
import java.net.Socket;

import java.net.InetAddress;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class TCPConnection extends Thread {

    //////////////////
    //  SINGLETON   //
    //////////////////
    private static TCPConnection instance; 

    private TCPConnection(){}

    public static TCPConnection getInstance(){
        if(instance == null){
            instance = new TCPConnection();
        }
        return instance; 
    }


    private Socket socket;


    @Override
    public void run(){
        // Recepción 
        try {
            // Leer la información desde una secuencia de bytes
            InputStreamReader isr = new InputStreamReader(socket.getInputStream()); 
            BufferedReader reader = new BufferedReader(isr); 

            String msj = reader.readLine().trim(); 
            System.out.println(msj);

        } catch (Exception e) {
            // TODO: handle exception
        }
    }

    public void setServerPort(int port){
        try {
            this.socket = new ServerSocket(port).accept();
        } catch (IOException e) {
            // TODO: handle exception
        }
    }

    public void sendMessage(String dstIp, int dstPort){

        new Thread( () -> {
            try {
                Socket remoteSocket = new Socket(InetAddress.getByName(dstIp), dstPort);
                OutputStreamWriter osw = new OutputStreamWriter(remoteSocket.getOutputStream()); 
                BufferedWriter writer = new BufferedWriter(osw); 

                BufferedReader consoReader = new BufferedReader(new InputStreamReader(System.in));

                System.out.println("Type message: ");
                String msj = ""; 

                while ( (msj = consoReader.readLine()) != null) {
                    System.out.println("send::while >> " + msj);
                    writer.write(msj);
                }
                writer.flush();
                writer.close();
                remoteSocket.close();
                
            } catch (Exception e) {
                // TODO: handle exception
            }
        } ).start();


    }

    
}
