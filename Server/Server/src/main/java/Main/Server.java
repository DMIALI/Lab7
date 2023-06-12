package Main;

import CommandData.*;
import ServerModules.*;
import ServerModules.Client;
import Utils.ClientConnection;
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
                                ClientConnection clientConnection = new ClientConnection(datagramPacket.getAddress(),datagramPacket.getPort());
                                return new ReceivedData(receiveClientData(clientConnection, datagramPacket, clientManager),clientConnection);
                            }
                            catch (IOException | ClassNotFoundException | NullPointerException e){
                                logger.fatal(e);
                            }
                            return null;
                        }
                    });
                    ClientData clientData = receivedData.clientData();
                    ClientConnection clientConnection = receivedData.clientConnection();
                    if (clientData == null || !checkLoginAndPasswd(clientData, clientManager, clientConnection)){
                        return;
                    }
                    Client client = clientManager.getClient(clientData.getLogin(),clientData.getPasswd());
                    client.setClientConnection(clientConnection);
                    clientManager.checkIfClientNew(client, clientData);
                    logger.info("Клиент: логин " +client.getLogin()+ " адрес " + clientConnection.inetAddress() + " порт " + clientConnection.port());
                    logger.debug("Получена датаграмма номер: " + clientData.getCounter());
                    logger.debug("Ожидалось: " + client.getDatagramCounter());
                    logger.info("Команда: " + clientData.getName());
                    logger.debug(clientData.toString());
                    logger.debug("Список клиентов: " + clientManager.getClients().toString());
                    try{
                        /*if (checkAccess(clientData, client)){
                            return;
                        }*/
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
    private ClientData receiveClientData(ClientConnection clientConnection, DatagramPacket datagramPacket, ClientManager clientManager) throws IOException, ClassNotFoundException {
        byte[] data = datagramPacket.getData();
        if (!clientManager.getHandlers().containsKey(clientConnection)){
            clientManager.getHandlers().put(clientConnection, new Handler());
        }
        int counter = ((data[0] & 0xFF) << 24) | ((data[1] & 0xFF) << 16) | ((data[2] & 0xFF) << 8 ) | ((data[3] & 0xFF));
        ClientData clientData;
        if (counter >= 0){
            logger.trace("Получен чанк номер " + (1+counter/CHUNK_SIZE));
            clientData = clientManager.getHandlers().get(clientConnection).add(datagramPacket, CHUNK_SIZE);
        }
        else{
            logger.trace("Получен чанк номер " + 1 + " ожидается еще "+ (-1+((int) -1*counter/CHUNK_SIZE)) +" чанков");
            clientData = clientManager.getHandlers().get(clientConnection).handle(datagramPacket, CHUNK_SIZE);
        }
        return clientData;
    }
    private void checkClientData(ClientData clientData, Client client) throws IOException {
        if (clientData.getCounter() == client.getDatagramCounter()){
            client.increaseCounter();
        }
        else{
            if (Objects.equals(clientData.getCounter(), client.getLatestServerData().counter())){
                send(client.getLatestServerData(), client.getClientConnection());
                return;
            }
            logger.warn("Получена датаграмма с неожиданным номером");
            throw new IOException();
        }
    }
    /*private boolean checkAccess(ClientData clientData, Client client) throws IOException {
        if (Objects.equals(clientData.getName(), "checkAccess")){
            client.increaseCounter();
            send(new ServerData(1L, "checkAccess", PrintType.PRINT), new ClientConnection(clientData.));
            return true;
        }
        return false;
    }*/
    private boolean checkLoginAndPasswd(ClientData clientData, ClientManager clientManager, ClientConnection clientConnection) {
        if (Objects.equals(clientData.getName(), "createNewClient")){
            switch (clientManager.checkLoginAndPasswd(clientData.getLogin(),clientData.getPasswd())){
                case(-1):
                    send(new ServerData(clientData.getCounter(),"Пользователь с таким логином существует", PrintType.ERRPRINTLN), clientConnection);
                    logger.info("Неудачная попытка регистрации: пользователь с логином "+clientData.getLogin()+" уже существует");
                    return false;
                case(1):
                    send(new ServerData(clientData.getCounter(),"Вы успешно вошли под логином " + clientData.getLogin(), PrintType.PRINTLN), clientConnection);
                    logger.info("Вход вместо регистрации: пользователь с логином "+clientData.getLogin()+" уже существует");
                    return false;
                default:
                    clientManager.addClient(new Client(clientData.getLogin(), clientData.getPasswd()));
                    send(new ServerData(clientData.getCounter(),"Вы успешно зарегистрировались под логином " + clientData.getLogin(), PrintType.PRINTLN), clientConnection);
                    return false;
            }
        }
        else if (Objects.equals(clientData.getName(), "clientEntry")) {
            switch (clientManager.checkLoginAndPasswd(clientData.getLogin(),clientData.getPasswd())){
                case(-1):
                    send(new ServerData(clientData.getCounter(),"Неверный пароль", PrintType.ERRPRINTLN), clientConnection);
                    logger.info("Неудачная попытка авторизации: указан неверный пароль, логин "+clientData.getLogin());
                    return false;
                case(1):
                    send(new ServerData(clientData.getCounter(),"Вы успешно вошли под логином " + clientData.getLogin(), PrintType.PRINTLN), clientConnection);
                    logger.info("Успешная авторизация: пользователь с логином "+clientData.getLogin());
                    return false;
                default:
                    clientManager.addClient(new Client(clientData.getLogin(), clientData.getPasswd()));
                    send(new ServerData(clientData.getCounter(),"Пользователя с логином " + clientData.getLogin() + " не существует", PrintType.ERRPRINTLN), clientConnection);
                    logger.info("Неудачная попытка авторизации: указан несуществующий логин "+clientData.getLogin());
                    return false;
            }
        }
        return true;
    }
    public void send(ServerData serverData, ClientConnection clientConnection) {
        Sender.send(serverData, clientConnection, datagramSocket, CHUNK_SIZE ,logger);
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