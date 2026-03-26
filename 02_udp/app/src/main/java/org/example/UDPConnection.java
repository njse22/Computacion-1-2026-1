package org.example;

import java.net.DatagramSocket;
import java.net.DatagramPacket;
import java.net.InetAddress;

public class UDPConnection extends Thread {

    // Patron singleton
    private static UDPConnection instance; 

    private UDPConnection(){ }

    public static UDPConnection getInstance(){

        if (instance == null){
            instance = new UDPConnection(); 
        }
        return instance; 

    }

    private DatagramSocket socket; 

    public void setPort(int port){
        try {
            socket = new DatagramSocket(port); 
        } catch (Exception e) {
            // TODO: handle exception
        }
    }

    @Override
    public void run(){
        // Recepción del mensaje

        try {
            socket = new DatagramSocket(5000);
            byte[] buffer = new byte[1024]; 

            DatagramPacket packet = new DatagramPacket(buffer, buffer.length); 

            socket.receive(packet);

            String msg = new String(packet.getData());
            System.out.println(msg);
            
        } catch (Exception e) {
            // TODO: handle exception
        }
    }

    public void sendDatagramt(String msj, String ip, int port){

        new Thread( () -> {
            // Envio del mensaje
            try {
                DatagramSocket socket = new DatagramSocket();

                DatagramPacket packet = new DatagramPacket(msj.getBytes(), msj.length());
                
                System.out.println("Conectando con el reciver");
                //                                   dirección ip : Puerto
                socket.connect(InetAddress.getByName(ip), port);

                System.out.println("Enviando paquete");

                socket.send(packet);
                
            } catch (Exception e) {
                System.out.println("ERROR");
            }
        } ).start();



    }

}
