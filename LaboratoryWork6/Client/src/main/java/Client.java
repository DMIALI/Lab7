import ManagerOfCommands.CommandData.ClientData;
import ManagerOfCommands.CommandsManager;
import Utils.Printer;
import com.fasterxml.jackson.databind.ObjectMapper;

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
        ClientData clientData = new ClientData();
        clientData.setName("checkAccess");
        ClientData ans = sendThenReceive(clientData);
    }

    private void sendData(ClientData clientData) throws IOException {
        DatagramPacket datagramPacket = serialize(clientData);
        datagramSocket.send(datagramPacket);
    }

    private ClientData receiveData() throws IOException {
        byte[] buffer = new byte[1024];
        DatagramPacket datagramPacket = new DatagramPacket(buffer, buffer.length);
        datagramSocket.receive(datagramPacket);
        return deserialize(datagramPacket);
    }

    public ClientData deserialize (DatagramPacket datagramPacket) throws IOException {
        try {
            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(datagramPacket.getData());
            ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);
            return (ClientData) objectInputStream.readObject();
        } catch (ClassNotFoundException e){
            printer.errPrintln("Не получилось десериализовать данные :(");
            return null;
        }
    }

    public ClientData sendThenReceive(ClientData clientData){
        ClientData answer = null;
        while (true){
            int retries = 10;
            boolean receivedResponse = false;
            while(!receivedResponse && retries > 0) {
                try {
                    sendData(clientData);
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
                ClientData clientData = commandsManager.check(name, listOfCommand);
                //here commandData ready for sending

                ClientData answer = sendThenReceive(clientData);
                if (answer == null){
                    //add pull
                }
                printer.outPrintln(answer.getCounter().toString());
            } catch (NullPointerException e) {
                printer.errPrintln("Команда не найдена");
            }
            printer.outPrint("Введите команду: ");
            command = scanner.nextLine();
        }
    }
    public DatagramPacket serialize (ClientData clientData) throws IOException {
        /*ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
        objectOutputStream.writeObject(commandData);
        byte[] buffer = byteArrayOutputStream.toByteArray();*/
        byte[] buffer = (new ObjectMapper()).writeValueAsString(clientData).getBytes();
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