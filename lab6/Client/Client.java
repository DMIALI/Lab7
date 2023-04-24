import java.io.IOException;
import java.net.*;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;

public class Client {


    private DatagramSocket datagramSocket;
    private InetAddress inetAddress;
    private byte[] buffer;

    public Client(DatagramSocket datagramSocket, InetAddress inetAddress) {
        this.datagramSocket = datagramSocket;
        this.inetAddress = inetAddress;
    }

    public void sendThenReceive(){

        Scanner scanner =  new Scanner(System.in);
        while (true){

            try{
                String message = scanner.nextLine();

                buffer = message.getBytes();
                DatagramPacket datagramPacket = new DatagramPacket(buffer, buffer.length, inetAddress, 1409);
                datagramSocket.send(datagramPacket);
                datagramSocket.receive(datagramPacket);
                String serverMessage = new String(datagramPacket.getData(), 0, datagramPacket.getLength());
                System.out.println("Server message: "+ serverMessage);
            } catch (UnknownHostException e){
                System.out.println("gg");
            } catch (IOException e) {
                e.printStackTrace();
                break;
            }
        }

    }

    public static void main(String[] args) throws SocketException, UnknownHostException {
        //s367803@helios.se.ifmo.ru
        DatagramSocket datagramSocket = new DatagramSocket();
        InetAddress inetAddress = InetAddress.getByName("helios.cs.ifmo.ru");
        Client client = new Client(datagramSocket, inetAddress);
        System.out.println("Send datagramSocket to Server");
        client.sendThenReceive();
    }
}
