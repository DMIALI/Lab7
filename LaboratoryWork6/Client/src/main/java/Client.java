import ManagerOfCommands.CommandData.CommandData;
import ManagerOfCommands.CommandsManager;
import Utils.Printer;

import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;


public class Client {
    private static Printer printer = new Printer();
    private Scanner scanner = new Scanner(System.in);
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
    // хз зачем
    private void testSend() {
        CommandData commandData = new CommandData();
        commandData.setName("checkAccess");
        CommandData ans = sendThenReceive(commandData);
    }

    private void sendData(CommandData commandData) throws IOException {
        DatagramPacket datagramPacket = serialize(commandData);
        datagramSocket.send(datagramPacket);
    }

    private CommandData receiveData() throws IOException {
        byte[] buffer = new byte[1024];
        DatagramPacket datagramPacket = new DatagramPacket(buffer, buffer.length);
        datagramSocket.receive(datagramPacket);
        return deserialize(datagramPacket);
    }

    public CommandData deserialize (DatagramPacket datagramPacket) throws IOException {
        try {
            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(datagramPacket.getData());
            ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);
            return (CommandData) objectInputStream.readObject();
        } catch (ClassNotFoundException e){
            printer.errPrintln("Не получилось десериализовать данные :(");
            return null;
        }
    }

    public CommandData sendThenReceive(CommandData commandData){
        CommandData answer = null;
        while (true){
            int retries = 10;
            boolean receivedResponse = false;
            while(!receivedResponse && retries > 0) {
                try {
                    sendData(commandData);
                    answer = receiveData();
                    receivedResponse = true;
                } catch (SocketTimeoutException e) {
                    retries--;
                    printer.errPrintln("Попробую отправить данные в " + retries + " раз");
                } catch (IOException e) {
                    retries--;
                    printer.errPrintln("Не получилось десериализовать данные :(");
                }
            }
            if(receivedResponse || retries == 0){
                printer.outPrintln("Дальнейшие действия: \n continue - продолжить отправлять эту команду \n try - продолжить вводить команды" );
                if (scanner.next().equals("try")){
                    return null;
                }

            }
            return answer;
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
                CommandData commandData = commandsManager.check(name, listOfCommand);
                //here commandData ready for sending

                CommandData answer = sendThenReceive(commandData);
                if (answer == null){
                    //add pull
                }
                printer.outPrintln(answer.getNumber().toString());
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
        client.start();
    }
}