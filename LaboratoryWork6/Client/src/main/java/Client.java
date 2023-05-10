import ManagerOfCommands.CommandData.CommandData;
import ManagerOfCommands.CommandsManager;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.*;
import java.util.Scanner;


public class Client {

    private static DatagramSocket datagramSocket;
    private static int SERVER_PORT = 1408;
    private byte[] buffer;


    public Client(DatagramSocket datagramSocket) {
        this.datagramSocket = datagramSocket;
    }


    public void connection(){
        try{
            datagramSocket.connect(InetAddress.getByName("localhost"), SERVER_PORT);
            datagramSocket.setSoTimeout(100_000);
        } catch (SocketException e) {
            throw new RuntimeException(e);
        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        }
    }



    public static void main(String[] args) throws SocketException {
        //s367803@helios.se.ifmo.ru
        DatagramSocket datagramSocket = new DatagramSocket();
        Client client = new Client(datagramSocket);
        client.connection();
        System.out.println("Send datagramSocket to Server");
        CommandsManager commandsManager = new CommandsManager();
        commandsManager.start(args[0]);
        //client.sendThenReceive();
    }
}