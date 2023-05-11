import ManagerOfCommands.CommandData.ClientData;
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
    private static Printer printer = new Printer();
    private static Scanner scanner = new Scanner(System.in);
    private DatagramSocket datagramSocket = new DatagramSocket();
    private static int SERVER_PORT = 1408;
    private PriorityQueue<ClientData> pull = new PriorityQueue<ClientData>();
    private byte[] buffer;


    public Client(DatagramSocket datagramSocket) throws SocketException {
        this.datagramSocket = new DatagramSocket();
    }


    public void connection() throws UnknownHostException {
        try{
            datagramSocket.connect(InetAddress.getByName("localhost"), SERVER_PORT);
            datagramSocket.setSoTimeout(10_000);
            try {
                testSend();
                printer.outPrintln("Подключение успешно выполнено!");
            } catch (IOException e) {
                printer.errPrintln("Подключение не было установленно");
                throw new UnknownHostException();
            }

        }catch (UnknownHostException e) {
            printer.errPrintln("Сервера пока не существует :(");
            throw e;
        } catch (SocketException e) {
            throw new RuntimeException(e);
        }
    }
    // хз зачем
    private void testSend() throws IOException {
        try{
            ClientData clientData = new ClientData();
            clientData.setName("checkAccess");
            ServerData ans = sendThenReceive(clientData);
        } catch (IOException e){
            throw e;
        }
    }

    private void sendData(ClientData clientData) throws IOException {
        DatagramPacket datagramPacket = serialize(clientData);
        datagramSocket.send(datagramPacket);
    }

    private ServerData receiveData() throws IOException {
        byte[] buffer = new byte[1024];
        DatagramPacket datagramPacket = new DatagramPacket(buffer, buffer.length);
        datagramSocket.receive(datagramPacket);
        return deserialize(datagramPacket);
    }

    public ServerData deserialize (DatagramPacket datagramPacket) throws IOException {
        /*ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(datagramPacket.getData());
        ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);
        return (ClientData) objectInputStream.readObject();*/
        InputStream inputStream = new ByteArrayInputStream(datagramPacket.getData());
        TypeReference<ServerData> mapType = new TypeReference<ServerData>() {};
        ServerData serverData = (new ObjectMapper()).readValue(inputStream, mapType);
        return  serverData;
    }
    public ServerData sendThenReceive(ClientData clientData) throws IOException {
        try{
            sendData(clientData);
            return receiveData();

        } catch (IOException e) {
            throw e;
        }
    }
    public void pullSender(){
        for (ClientData clientData : pull) {
            try {
                ServerData ans = sendThenReceive(clientData);
                printer.out(ans.message(), ans.printType());
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
                pull.add(clientData);
                ServerData answer = sendThenReceive(clientData);
                pull.remove(clientData);
                printer.out(answer.message(), answer.printType());
                if (pull.size() > 0){
                    printer.outPrintln("Хотите отправить все не дошедшие ранее команды? [y/n]");
                    String ans = scanner.next();
                    if (ans.equals("yes") || ans.equals("y")){
                        pullSender();
                    }else{
                        pull.clear();
                    }
                }

                //printer.outPrintln(answer.counter().toString());
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