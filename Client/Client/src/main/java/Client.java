import ClientModules.Handler;
import ClientModules.Sender;
import ManagerOfCommands.CommandData.ClientData;
import ManagerOfCommands.CommandData.PrintType;
import ManagerOfCommands.CommandData.ServerData;
import ManagerOfCommands.CommandsManager;
import Utils.Printer;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.PriorityQueue;
import java.util.Scanner;


public class Client {
    private static final int CHUNK_SIZE = 250;
    private static Printer printer = new Printer();
    private static Scanner scanner = new Scanner(System.in);
    private static Handler handler = new Handler();
    private DatagramSocket datagramSocket = new DatagramSocket();
    private static int SERVER_PORT;
    private PriorityQueue<ClientData> pull = new PriorityQueue<ClientData>();
    private byte[] buffer;

    private char[] password ;
    private String login;
    public Client(DatagramSocket datagramSocket) throws SocketException {
        this.datagramSocket = new DatagramSocket();
    }


    public void connection() throws UnknownHostException {
        try{
            datagramSocket.connect(InetAddress.getByName("localhost"), SERVER_PORT);
            datagramSocket.setSoTimeout(10_000);
            authorization();
            printer.outPrintln("Подключение успешно выполнено!");

        }catch (UnknownHostException e) {
            printer.errPrintln("Сервера пока не существует :(");
            throw e;
        } catch (SocketException e) {
            throw new RuntimeException(e);
        }
    }

    public void authorization(){
        while (true){
            printer.outPrintln("Вы хотите зарегистрироваться или войти в существующий аккаунт?");
            printer.outPrintln("LogIn - войти \t Create - зарегистрироваться");
            String input = scanner.nextLine();
            ClientData clientData = null;
            if (input.toLowerCase().equals("LogIn".toLowerCase())){
                printer.outPrintln("Введите имя пользователя");
                String newUsername = scanner.nextLine();
                //String newUsername = System.console().readLine();
                printer.outPrintln("Введите пароль");
                //char[] newPassword = System.console().readPassword(); - хороший ввод пароля, без отображения
                char[] newPassword = scanner.nextLine().toCharArray();
                clientData = ClientData.builder()
                        .name("clientEntry")
                        .login(newUsername)
                        .passwd(newPassword).build();
            } else if (input.toLowerCase().equals("Create".toLowerCase())){
                printer.outPrintln("Введите имя пользователя");
                //String newUsername = System.console().readLine();
                String newUsername = scanner.nextLine();
                printer.outPrintln("Введите пароль");
                //char[] newPassword = System.console().readPassword(); - хороший ввод пароля, без отображения
                char[] newPassword = scanner.nextLine().toCharArray();
                clientData = ClientData.builder()
                        .name("createNewClient")
                        .login(newUsername)
                        .passwd(newPassword).build();

                //printer.errPrintln(new String(newPassword));
            }
            if (clientData != null){
                try {
                    ArrayList ans = sendThenReceive(clientData);
                    outputAnswers(ans);
                    ServerData serverData = (ServerData) ans.get(0);
                    if (((ServerData) ans.get(0)).printType() != PrintType.ERRPRINTLN) {
                        break;
                    }
                } catch (IOException e) {
                    printer.errPrintln(String.valueOf(e));
                }
            }
        }
    }

    private void testSend() throws IOException {
        try{
            ClientData clientData = new ClientData();
            clientData.setName("checkAccess");
            ArrayList ans = sendThenReceive(clientData);
        } catch (IOException e){
            throw e;
        }
    }

    private void sendData(ClientData clientData) throws IOException {
        Sender.send(clientData, datagramSocket, CHUNK_SIZE);
    }

    private ServerData receiveData() throws IOException {
        DatagramPacket datagramPacket = new DatagramPacket(new byte[CHUNK_SIZE + 4], CHUNK_SIZE + 4);
        datagramSocket.receive(datagramPacket);
        byte[] data = datagramPacket.getData();
        int counter = ((data[0] & 0xFF) << 24) | ((data[1] & 0xFF) << 16) | ((data[2] & 0xFF) << 8 ) | ((data[3] & 0xFF));
        if (counter >= 0){
            return handler.add(datagramPacket, CHUNK_SIZE);
        }
        else {
            return handler.handle(datagramPacket, CHUNK_SIZE);
        }
    }

    public ServerData deserialize (DatagramPacket datagramPacket) throws IOException {

        InputStream inputStream = new ByteArrayInputStream(datagramPacket.getData());
        TypeReference<ServerData> mapType = new TypeReference<ServerData>() {};
        ServerData serverData = (new ObjectMapper()).readValue(inputStream, mapType);
        return  serverData;
    }
    public ArrayList<ServerData> sendThenReceive(ClientData clientData) throws IOException {
        ArrayList<ServerData> answers = new ArrayList<>();
        sendData(clientData);
        while(true) {
            try {
                ServerData serverData = receiveData();
                if (!(serverData == null)){
                    answers.add(serverData);;
                }
                datagramSocket.setSoTimeout(10);
            } catch (IOException e) {
                break;
            }
        }
        if (datagramSocket.getSoTimeout() == 10){
            datagramSocket.setSoTimeout(10_000);
            return answers;
        }
        datagramSocket.setSoTimeout(10_000);
        throw new IOException();
    }

    private void outputAnswers(ArrayList<ServerData> ans){
        for (ServerData serverData : ans){
            printer.out(serverData.message(), serverData.printType());
            printer.outPrintln("");
        }

    }

    public void pullSender(long index){

        long delta = index - pull.peek().getCounter() + 1;
        for (ClientData clientData : pull) {
            try {
                clientData.setCounter(clientData.getCounter() + delta);
                printer.outPrintln("Команда " + clientData.getName() + ":");
                outputAnswers(sendThenReceive(clientData));
            } catch (IOException e) {
                printer.errPrintln("Снова не получилось отправить данные :(");
            }
        }
    }
    public void start () {

        CommandsManager commandsManager = new CommandsManager();
        printer.outPrint("Введите команду:");
        String command = scanner.nextLine();

        while (true) {
            try {
                ArrayList<String> listOfCommand = new ArrayList<String>();
                Collections.addAll(listOfCommand, command.split(" "));
                String name = listOfCommand.remove(0);
                ClientData clientData = commandsManager.check(name, listOfCommand);
                //here commandData ready for sending
                if (name.equals("exit")){
                    sendData(clientData);
                    printer.outPrintln("By!");
                    System.exit(0);
                }
                pull.add(clientData);

               outputAnswers(sendThenReceive(clientData));
                //printer.out(answer.message(), answer.printType());

                pull.remove(clientData);
                if (pull.size() > 0){
                    printer.outPrintln("Хотите отправить все не дошедшие ранее команды? [y/n]");
                    String ans = scanner.nextLine();
                    if (ans.equals("yes") || ans.equals("y")){
                        ClientData.setNumber(clientData.getCounter() + pull.size());
                        pullSender(clientData.getCounter());
                    }
                    pull.clear();
                }

            }  catch (NullPointerException e){
                printer.errPrintln("Команда не найдена");
            } catch (RuntimeException e){
                printer.errPrintln("Попробуйте еще раз");
            } catch ( IOException e) {

                printer.errPrintln("Не получилось отправить/получить данные");
            }
            printer.outPrint("Введите команду: ");
            command = scanner.nextLine();
        }
    }
    private static void checkPort(String arg) {
        try {
            SERVER_PORT = Integer.parseInt(arg);
        } catch (NumberFormatException e) {
            System.err.println("Введен неверный порт");
            System.exit(1);
        }
    }
    public static void checkArgs(String[] args){
        if (args.length<1) {
            System.err.println("Необходимо ввести порт сервера, введено аргументов: " + args.length);
            System.exit(1);
        }
    }
    public static void main(String[] args) throws SocketException {
        //s367803@helios.se.ifmo.ru
        checkArgs(args);
        checkPort(args[0]);
        DatagramSocket datagramSocket = new DatagramSocket();
        Client client = new Client(datagramSocket);
        printer.outPrintln("Пытаюсь подключиться к серверу, пожалуйста подождите...");
        //Authorizer authorizer = new Authorizer(printer);
        //authorizer.authorization();
        while(true){
            try {
                client.connection();
                client.start();
                break;
            } catch (UnknownHostException e) {
                printer.outPrintln("Хотите попробывать подключиться занова? [y/n]");
                String ans = scanner.nextLine();
                if (ans.equals("no") || ans.equals("n")){
                    break;
                }
            }
        }
    }
}