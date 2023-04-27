package Server;

import java.io.IOException;
import java.net.*;

public class Server {

    private DatagramSocket datagramSocket;
    private byte[] buffer = new byte[256];
    private static int PORT = 1408;

    public Server (DatagramSocket datagramSocket){
        this.datagramSocket = datagramSocket;
    }

    public void receiveThenSend(){
        while (true){
            try {
                DatagramPacket datagramPacket = new DatagramPacket(buffer, buffer.length);
                datagramSocket.receive(datagramPacket);
                InetAddress inetAddress = datagramPacket.getAddress();
                int port = datagramPacket.getPort();
                String message = new String(datagramPacket.getData(), 0, datagramPacket.getLength());
                if (message.equals("exit")){
                    datagramSocket.close();
                    break;
                }
                System.out.println(message);
                datagramPacket = new DatagramPacket(buffer, buffer.length, inetAddress, port);
                datagramSocket.send(datagramPacket);

            } catch (IOException e) {
                e.printStackTrace();
                break;
            }
        }
    }

    public static void main(String[] arg) throws SocketException {
        DatagramSocket datagramSocket = new DatagramSocket(PORT);
        Server server = new Server(datagramSocket);
        server.receiveThenSend();
    }
}