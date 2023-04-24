import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.Objects;
import java.util.Scanner;

public class Server {

    private DatagramSocket datagramSocket;
    private byte[] buffer = new byte[256];

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
        DatagramSocket datagramSocket = new DatagramSocket(1409);
        Server server = new Server(datagramSocket);
        server.receiveThenSend();

    }
}
