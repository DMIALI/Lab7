package Main;

import CommandData.ClientData;
import CommandData.InputCommandData;
import CommandData.PrintType;
import CommandData.ServerData;
import Utils.Client;
import Utils.Printer;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;

import java.io.*;
import java.net.*;
import java.util.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.lang.model.type.PrimitiveType;

public class Server {
    @Getter
    private ClientManager clientManager;

    private DatagramSocket datagramSocket;
    private byte[] buffer = new byte[4096];
    private static final Logger logger = LogManager.getLogger();
    public Server (DatagramSocket datagramSocket){
        this.datagramSocket = datagramSocket;
    }

    public void receiveThenSend(CollectionManager collectionManager, Printer printer, ControlCenter controlCenter){
        while (true){
            try {
                DatagramPacket datagramPacket = new DatagramPacket(buffer, buffer.length);
                datagramSocket.receive(datagramPacket);
                InetAddress inetAddress = datagramPacket.getAddress();
                int port = datagramPacket.getPort();
                ClientData clientData = handle(datagramPacket);
                Client client = clientManager.getClient(inetAddress, port, clientData.getCounter());
                logger.info("Клиент: адрес " + client.getInetAddress() + " порт " + client.getPort()+ " объект " + client.toString());
                logger.debug("Получена датаграмма номер: " + clientData.getCounter());
                logger.debug("Ожидалось: " + client.getDatagramCounter());
                logger.info("Команда: " + clientData.getName());
                logger.debug("Список клиентов: " + clientManager.getClients().toString());
                if (checkAccess(clientData, client)){
                    continue;
                }
                checkClientData(clientData, client);
                InputCommandData inputCommandData = new InputCommandData(collectionManager,client, printer, clientData, controlCenter.getCommandMap());
                controlCenter.executeCommand(inputCommandData);
            } catch (IOException | ClassNotFoundException e) {
                logger.fatal(e);
            }
        }
    }
    public ClientData handle(DatagramPacket datagramPacket) throws IOException, ClassNotFoundException {
        InputStream inputStream = new ByteArrayInputStream(datagramPacket.getData());
        TypeReference<ClientData> mapType = new TypeReference<ClientData>() {};
        ClientData clientData = (new ObjectMapper()).readValue(inputStream, mapType);
        return clientData;
    }
    public void checkClientData(ClientData clientData, Client client) throws IOException {
        if (clientData.getCounter() == client.getDatagramCounter()){
            client.increaseCounter();
        }
        else{
            if (Objects.equals(clientData.getCounter(), client.getLatestServerData().counter())){
                send(client.getLatestServerData(), client);
                return;
            }
            logger.warn("Получена датаграмма с неожиданным номером");
            throw new IOException();
        }
    }

    public boolean checkAccess(ClientData clientData, Client client) throws IOException {
        if (Objects.equals(clientData.getName(), "checkAccess")){
            client.increaseCounter();
            send(new ServerData(1L, "checkAccess", PrintType.PRINT), client);
            return true;
        }
        return false;
    }
    public void send(ServerData serverData, Client client) {

        try{
            byte[] sendingDataBuffer = (new ObjectMapper()).writeValueAsString(serverData).getBytes();
            DatagramPacket datagramPacket = new DatagramPacket(sendingDataBuffer, sendingDataBuffer.length, client.getInetAddress(), client.getPort());
            try {
                datagramSocket.send(datagramPacket);
            }
            catch (IOException e){
                logger.error(e);
            }
        }
        catch (IOException e){
            logger.error(e);
        }
    }
    private static DatagramSocket checkPort(String arg) {
        try {
            int port = Integer.parseInt(arg);
            try {
                return new DatagramSocket(port);
            }
            catch (IOException e){
                logger.fatal("Не удалось занять порт, возможно он уже используется");
                e.printStackTrace();
                System.exit(1);
            }
        } catch (NumberFormatException e) {
            logger.fatal("Введен неверный порт");
            System.err.println("Введен неверный порт");
            System.exit(1);
        }
        return null;
    }
    public static void checkArgs(String[] args){
        if (args.length<2) {
            logger.fatal("Введено недостаточно аргументов");
            System.err.println("Необходимо ввести путь к файлу и порт, введено аргументов: " + args.length);
            System.exit(1);
        }
    }
    public static void main(String[] args) throws IOException {
        checkArgs(args);
        DatagramSocket datagramSocket = checkPort(args[1]);
        Server server = new Server(datagramSocket);
        server.clientManager = new ClientManager();
        Scanner scanner = new Scanner(System.in);
        CollectionManager collectionManager = new CollectionManager(args[0], scanner);
        Printer printer = new Printer(server);
        ControlCenter controlCenter = new ControlCenter();
        server.receiveThenSend(collectionManager, printer, controlCenter);
    }
}