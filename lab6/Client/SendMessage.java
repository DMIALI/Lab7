import java.io.IOException;
import java.net.*;

public class SendMessage {
    //private String message;
    private String host;
    private int port;
    public SendMessage(String host, int port){
        this.host = host;
        this.port = port;
    }
    public void sendMessage(String message){
        try {
            byte[] data = message.getBytes();
            InetAddress address = InetAddress.getByName(host);
            DatagramPacket packet = new DatagramPacket(data, data.length, address, port);
            DatagramSocket datagramSocket = new DatagramSocket();
            datagramSocket.send(packet);
            datagramSocket.close();
        } catch (UnknownHostException e){
            System.out.println("gg");
        } catch (IOException e) {
            System.out.println("ll");
        }
    }
}
