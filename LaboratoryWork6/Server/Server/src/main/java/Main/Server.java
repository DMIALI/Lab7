package Main;

import CommandData.ClientData;
import CommandData.InputCommandData;
import CommandData.ServerData;
import Utils.Client;
import Utils.Printer;

import java.io.*;
import java.net.*;
import java.util.Objects;
import java.util.Base64;
import java.util.Scanner;

public class Server {

    private DatagramSocket datagramSocket;
    private byte[] buffer = new byte[256];
    private static int PORT = 1408;

    public Server (DatagramSocket datagramSocket){
        this.datagramSocket = datagramSocket;
    }

    public void receiveThenSend(ClientManager clientManager, CollectionManager collectionManager){
        while (true){
            try {
                DatagramPacket datagramPacket = new DatagramPacket(buffer, buffer.length);
                datagramSocket.receive(datagramPacket);
                if (message.equals("exit")){
                    datagramSocket.close();
                }
                System.out.println(message);


            } catch (IOException e) {
                e.printStackTrace();
                break;
            }
        }
    }
    public InputCommandData handle(ClientManager clientManager, CollectionManager collectionManager, Printer printer, DatagramPacket datagramPacket) throws IOException, ClassNotFoundException {
        InetAddress inetAddress = datagramPacket.getAddress();
        int port = datagramPacket.getPort();
        //String message = new String(datagramPacket.getData(), 0, datagramPacket.getLength());
        InputStream inputStream = new ByteArrayInputStream(datagramPacket.getData());
        ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);
        ClientData clientData = (ClientData) objectInputStream.readObject();
        Client client = clientManager.getClient(inetAddress, port);
        return new InputCommandData(collectionManager, client, printer, clientData.getArgs(), );
    }
    public void checkClientData(ClientData clientData, Client client) throws IOException {
        if (clientData.getCounter() == client.getDatagramCounter()){
            client.increaseCounter();
        }
        else{
            if (Objects.equals(clientData.getCounter(), client.getLatestServerData().counter())){
                send(client.getLatestServerData(), client);
            }
        }
    }
    public void send(ServerData serverData, Client client) throws IOException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
        objectOutputStream.writeObject(serverData);
        objectOutputStream.close();
        byte[] sendingDataBuffer  = Base64.getEncoder().encodeToString(outputStream.toByteArray()).getBytes();
        DatagramPacket datagramPacket = new DatagramPacket(sendingDataBuffer, sendingDataBuffer.length, client.getInetAddress(), client.getPort());
        try {
            datagramSocket.send(datagramPacket);
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }
    public static void main(String[] arg) throws IOException {
        DatagramSocket datagramSocket = new DatagramSocket(PORT);
        Server server = new Server(datagramSocket);
        ClientManager clientManager = new ClientManager();
        Scanner scanner = new Scanner(System.in);
        CollectionManager collectionManager = new CollectionManager("data.json", scanner);
        server.receiveThenSend(clientManager, collectionManager);
    }
}