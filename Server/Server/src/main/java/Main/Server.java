package Main;

import CommandData.*;
import ServerModules.*;
import ServerModules.Client;
import Utils.Printer;
import lombok.Getter;

import java.io.*;
import java.net.*;
import java.util.*;
import java.util.concurrent.*;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Server {
    private static final int CHUNK_SIZE = 250;
    @Getter
    private ClientManager clientManager;
    private DatagramSocket datagramSocket;
    private static final Logger logger = LogManager.getLogger();
    public Server (DatagramSocket datagramSocket){
        this.datagramSocket = datagramSocket;
    }

    public void receiveThenSend(CollectionManager collectionManager, Printer printer, ControlCenter controlCenter, ExecutorService executorService, ForkJoinPool forkJoinPool) throws NullPointerException {
        while (true){
            DatagramPacket datagramPacket = new DatagramPacket(new byte[CHUNK_SIZE + 4], CHUNK_SIZE + 4);
            try {
                datagramSocket.receive(datagramPacket);
            } catch (IOException e) {
                logger.fatal(e);
            }
            executorService.submit(new Runnable() {
                @Override
                public void run() {
                    ReceivedData receivedData = forkJoinPool.invoke(new RecursiveTask<ReceivedData>() {
                        @Override
                        protected ReceivedData compute() {
                            try {
                                Client client = clientManager.getClient(datagramPacket.getAddress(), datagramPacket.getPort());
                                return new ReceivedData(receiveClientData(client, datagramPacket, collectionManager, printer, controlCenter),client);
                            }
                            catch (IOException | ClassNotFoundException | NullPointerException e){
                                logger.fatal(e);
                            }
                            return null;
                        }
                    });
                    ClientData clientData = receivedData.clientData();
                    Client client = receivedData.client();
                    if (clientData == null){
                        return;
                    }
                    clientManager.checkIfClientNew(client, clientData);
                    logger.info("Клиент: адрес " + client.getInetAddress() + " порт " + client.getPort()+ " объект " + client.toString());
                    logger.debug("Получена датаграмма номер: " + clientData.getCounter());
                    logger.debug("Ожидалось: " + client.getDatagramCounter());
                    logger.info("Команда: " + clientData.getName());
                    logger.debug(clientData.toString());
                    logger.debug("Список клиентов: " + clientManager.getClients().toString());
                    try{
                        if (checkAccess(clientData, client)){
                            return;
                        }
                        checkClientData(clientData, client);
                        controlCenter.executeCommand(new InputCommandData(collectionManager, client, printer, clientData, controlCenter.getCommandMap()));
                    }
                    catch (IOException e){
                        logger.fatal(e);
                    }
                }
            });

        }
    }
    private ClientData receiveClientData(Client client, DatagramPacket datagramPacket, CollectionManager collectionManager, Printer printer, ControlCenter controlCenter) throws IOException, ClassNotFoundException {
        byte[] data = datagramPacket.getData();
        int counter = ((data[0] & 0xFF) << 24) | ((data[1] & 0xFF) << 16) | ((data[2] & 0xFF) << 8 ) | ((data[3] & 0xFF));
        ClientData clientData;
        if (counter >= 0){
            logger.trace("Получен чанк номер " + (1+counter/CHUNK_SIZE));
            clientData = client.getHandler().add(datagramPacket, CHUNK_SIZE);
        }
        else{
            logger.trace("Получен чанк номер " + 1 + " ожидается еще "+ (-1+((int) -1*counter/CHUNK_SIZE)) +" чанков");
            clientData = client.getHandler().handle(datagramPacket, CHUNK_SIZE);
        }
        return clientData;
    }
    private void checkClientData(ClientData clientData, Client client) throws IOException {
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

    private boolean checkAccess(ClientData clientData, Client client) throws IOException {
        if (Objects.equals(clientData.getName(), "checkAccess")){
            client.increaseCounter();
            send(new ServerData(1L, "checkAccess", PrintType.PRINT), client);
            return true;
        }
        return false;
    }
    public void send(ServerData serverData, Client client) {
        Sender.send(serverData, client,datagramSocket, CHUNK_SIZE ,logger);
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
    private static void checkArgs(String[] args){
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
        ExecutorService executorService = Executors.newCachedThreadPool();
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        server.receiveThenSend(collectionManager, printer, controlCenter, executorService, forkJoinPool);
    }
}