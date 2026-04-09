package com.example;

public class App {
    public String getGreeting() {
        return "Hello World!";
    }

    public static void main(String[] args) {

        TCPConnection connection = TCPConnection.getInstance(); 

        /////////////////
        //  Recepción  //
        /////////////////
        // connection.setServerPort(5000);
        // connection.start();


        /////////////
        //  Envio  //
        /////////////
        connection.sendMessage("192.168.131.179", 5000);
        
    }
}
