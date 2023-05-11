package Main;

import CommandData.ClientData;
import CommandData.InputCommandData;
import CommandData.PrintType;
import CommandData.ServerData;
import DataTypes.MusicBand;
import Utils.Client;
import Utils.Printer;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.*;
import java.net.*;
import java.util.*;

public class Server {

    private DatagramSocket datagramSocket;
    private byte[] buffer = new byte[256];
    private static int PORT = 1408;

    public Server (DatagramSocket datagramSocket){
        this.datagramSocket = datagramSocket;
    }

    public void receiveThenSend(ClientManager clientManager, CollectionManager collectionManager, Printer printer){
        while (true){
            try {
                DatagramPacket datagramPacket = new DatagramPacket(buffer, buffer.length);
                datagramSocket.receive(datagramPacket);
                InetAddress inetAddress = datagramPacket.getAddress();
                int port = datagramPacket.getPort();
                System.out.println(port);
                Client client = clientManager.getClient(inetAddress, port);
                ClientData clientData = handle(datagramPacket);
                System.out.println(clientData.getCounter());
                System.out.println(clientData.getName());
                if (Objects.equals(clientData.getName(), "checkAccess")){
                    System.out.println("sfsfwwf");
                    send(new ServerData(1L, "checkAccess", PrintType.PRINT), client);
                }
                checkClientData(clientData, client);
                InputCommandData inputCommandData = new InputCommandData(collectionManager,client, printer, clientData);
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }
    public ClientData handle(DatagramPacket datagramPacket) throws IOException, ClassNotFoundException {
        InputStream inputStream = new ByteArrayInputStream(datagramPacket.getData());
        TypeReference<ClientData> mapType = new TypeReference<ClientData>() {};
        ClientData clientData = (new ObjectMapper()).readValue(inputStream, mapType);
        /*InputStream inputStream = new ByteArrayInputStream(datagramPacket.getData());
        ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);
        ClientData clientData =  (ClientData) objectInputStream.readObject();*/
        return clientData;
    }
    public void checkClientData(ClientData clientData, Client client) throws IOException {
        if (clientData.getCounter() == client.getDatagramCounter()){
            client.increaseCounter();
        }
        else{
            if (Objects.equals(clientData.getCounter(), client.getLatestServerData().counter())){
                send(client.getLatestServerData(), client);
            }
            throw new IOException();
        }
    }
    public void send(ServerData serverData, Client client) throws IOException {
        /*ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
        objectOutputStream.writeObject(serverData);
        objectOutputStream.close();
        byte[] sendingDataBuffer  = Base64.getEncoder().encodeToString(outputStream.toByteArray()).getBytes();*/
        byte[] sendingDataBuffer = (new ObjectMapper()).writeValueAsString(serverData).getBytes();
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
        CollectionManager collectionManager = new CollectionManager("C:\\Users\\фвьшт\\IdeaProjects\\Java-Programming\\LaboratoryWork6\\Server\\Server\\src\\main\\java\\Main\\data.json", scanner);
        Printer printer = new Printer();
        server.receiveThenSend(clientManager, collectionManager, printer);
    }
}