package Commands;

import CommandData.InputCommandData;
import Main.CollectionManager;
import Utils.Printer;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class ExecuteScript extends Command {
    @Override
    public void execute(InputCommandData input) {
            String arg = input.clientData().getArg();
            Printer printer = input.printer();
            HashMap<String, Command> commandMap = input.commandMap();
            CollectionManager collectionManager = input.collectionManager();
            ArrayList<String> scripts = new ArrayList<>();
            ArrayList<ArrayList<String>> commands = new ArrayList<>();
            addCommands(arg, printer, commandMap, commands, scripts, input);
            for (ArrayList<String> i : commands) {
                String name = i.remove(0);
                commandMap.get(name).execute(new InputCommandData(collectionManager, input.client(), printer, input.clientData(), input.commandMap()));
            }
    }
        private void addCommands(String arg, Printer printer, HashMap<String, Command> commandMap, ArrayList<ArrayList<String>> commands, ArrayList<String> scripts, InputCommandData input){
            try {
                File file = new File(arg);
                BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
                String command;
                while ((command = bufferedReader.readLine()) != null) {
                    ArrayList<String> listOfCommand = new ArrayList<String>();
                    Collections.addAll(listOfCommand, command.split(" "));
                    String name = listOfCommand.get(0);
                    try {
                        if (name.equals("execute_script")) {
                            if (scripts.contains(listOfCommand.get(1))) {
                                printer.errPrintln("Скрипт " + listOfCommand.get(1) + " вызывается рекурсивно. Повторное исполнение не будет произведено.", input.client(), input.clientData());
                            } else {
                                scripts.add(listOfCommand.get(1));
                                listOfCommand.remove(0);
                                addCommands(listOfCommand.get(0), printer, commandMap, commands, scripts, input);
                            }
                        }
                        else{
                            commandMap.get(name);
                            commands.add(listOfCommand);
                        }
                    } catch (NullPointerException e) {
                        printer.errPrintln("Команда не найдена: " + name, input.client(), input.clientData());
                    }
                }
            } catch (IOException e) {
                printer.errPrintln("Ошибка чтения файла: " + String.valueOf(e), input.client(), input.clientData());
            }
    }
}
