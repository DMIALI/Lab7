import ManagerOfCommands.CommandData.CommandData;
import ManagerOfCommands.CommandsManager;
import Utils.Printer;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;


public class Client {
    private static Printer printer = new Printer();

    private DatagramSocket datagramSocket = new DatagramSocket();
    private static int SERVER_PORT = 1408;
    private byte[] buffer;


    public Client(DatagramSocket datagramSocket) throws SocketException {
        this.datagramSocket = new DatagramSocket();
    }


    public void connection(){
        try{
            datagramSocket.connect(InetAddress.getByName("localhost"), SERVER_PORT);
            datagramSocket.setSoTimeout(10_000);
            testSend();
            printer.outPrintln("Подключение успешно выполнено!");
        } catch (SocketException e) {
            throw new RuntimeException(e);
        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        }
    }
    private void testSend() {
        CommandData commandData = new CommandData();
        commandData.setName("checkAccess");
        boolean receivedResponse = false;
        int retries = 0;
        while(!receivedResponse) {
            try {
                DatagramPacket testPacket = serialize(commandData);
                datagramSocket.send(testPacket);
                byte[] buffer = new byte[1024];
                DatagramPacket datagramPacket = new DatagramPacket(buffer, buffer.length);
                datagramSocket.receive(datagramPacket);
                receivedResponse = true;
            } catch (IOException e) {
                retries ++;
                printer.errPrintln("Попробую отправить данные в " + retries + " раз");
            }
        }
    }
    public void start () {
        Scanner scanner = new Scanner(System.in);
        CommandsManager commandsManager = new CommandsManager();
        printer.outPrint("Введите команду:");
        String command = scanner.nextLine();

        while (true) {
            try {
                ArrayList<String> listOfCommand = new ArrayList<String>();
                Collections.addAll(listOfCommand, command.split(" "));
                String name = listOfCommand.remove(0);
                CommandData commandData = commandsManager.check(name, listOfCommand);
                printer.errPrintln(commandData.getNumber().toString());
            } catch (NullPointerException e) {
                printer.errPrintln("Команда не найдена");
            }
            printer.outPrint("Введите команду: ");
            command = scanner.nextLine();
        }
    }
    public DatagramPacket serialize (CommandData commandData) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
        objectOutputStream.writeObject(commandData);
        byte[] buffer = byteArrayOutputStream.toByteArray();
        return new DatagramPacket(buffer, buffer.length);
    }
    public static void main(String[] args) throws SocketException {
        //s367803@helios.se.ifmo.ru
        DatagramSocket datagramSocket = new DatagramSocket();
        Client client = new Client(datagramSocket);
        printer.outPrintln("Пытаюсь подключиться к серверу, пожалуйста подождите...");
        client.connection();
        printer.outPrintln("Send datagramSocket to Server");
        client.start();
    }
}