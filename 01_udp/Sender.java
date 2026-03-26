import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class Sender{

    public static void main(String[] args) {
        
        try (DatagramSocket socket = new DatagramSocket()) {

            // mensaje 
            String msg = "Hola desde el Sender"; 

            DatagramPacket packet = new DatagramPacket(msg.getBytes(), msg.length());
            
            System.out.println("Conectando con el reciver");
            //                                   dirección ip : Puerto
            socket.connect(InetAddress.getByName("192.168.131.28"), 5000);

            System.out.println("Enviando paquete");

            socket.send(packet);
            
        } catch (Exception e) {
            System.out.println("ERROR");
        }
    }
}
