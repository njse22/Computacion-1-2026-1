import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class Reciver {

    public static void main(String[] args) {

        // Try-with resources -> inicializo un objeto que consume un recurso
        // del sistema operativo / En este caso el recurso es un socket
        //                                              port
        System.out.println("Escuchando mensajes...");
        try (DatagramSocket socket = new DatagramSocket(5000)) {

            byte[] buffer = new byte[1024]; 

            DatagramPacket packet = new DatagramPacket(buffer, buffer.length); 

            System.out.println("Mensaje Escuchando: ");
            socket.receive(packet);

            String msg = new String(packet.getData());
            System.out.println(msg);

            
        } catch (Exception e) {
            // TODO: handle exception
        }
    }
}
