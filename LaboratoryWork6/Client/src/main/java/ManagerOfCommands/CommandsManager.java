package ManagerOfCommands;

import ManagerOfCommands.CommandData.CommandData;
import ManagerOfCommands.CommandData.InputCommandData;
import ManagerOfCommands.Commands.*;
import Utils.Printer;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Scanner;

public class CommandsManager {
    private final HashMap<String, Command> commandMap = new HashMap<>();

    public CommandsManager() {
        commandMap.put("help", new Help());
        commandMap.put("clear", new Clear());
        commandMap.put("info", new Info());
        commandMap.put("reorder", new Reorder());
        commandMap.put("show", new Show());
        commandMap.put("shuffle", new Shuffle());
    }

    public void start (String path){
        Scanner scanner = new Scanner(System.in);
        Printer printer = new Printer();
        printer.outPrint("Введите команду:");
        String command = scanner.nextLine();
        while (true){
            try{
                ArrayList<String> listOfCommand = new ArrayList<String>();
                Collections.addAll(listOfCommand, command.split(" "));
                String name = listOfCommand.remove(0);
                //commandMap.get(inputHandler(name)).processing(new InputCommandData(scanner, printer, listOfCommand, commandMap));
                commandMap.get(inputHandler(name)).processing(name, listOfCommand);
                printer.errPrintln(commandMap.get(inputHandler(name)).processing(name, listOfCommand).getNumber());
            } catch(NullPointerException e) {
                printer.errPrintln("Команда не найдена");

            }
            printer.outPrint("Введите команду: ");
//            System.out.println();
            command = scanner.nextLine();
        }
    }
    // надо дописать сериализацию
    public void serialize (CommandData commandData){
        ObjectInputStream objectInputStream;
        byte [] info = new byte[256];

        try{
            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(info);
            objectInputStream = new ObjectInputStream(byteArrayInputStream);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        //CommandData cmd = (CommandData) objectInputStream.readObject();
    }
    private String inputHandler(String input) {
        StringBuilder name = new StringBuilder();
        for (String word : input.split("_")) {
            name.append(word.toLowerCase());
            name.append('_');
        }
        name.deleteCharAt(name.lastIndexOf("_"));
        return name.toString();
    }
}
