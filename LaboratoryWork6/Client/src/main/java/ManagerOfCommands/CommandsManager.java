package ManagerOfCommands;

import ManagerOfCommands.CommandData.ClientData;
import ManagerOfCommands.Commands.*;

import java.util.ArrayList;
import java.util.HashMap;

public class CommandsManager {
    private final HashMap<String, Command> commandMap = new HashMap<>();

    public CommandsManager() {
        commandMap.put("add", new Add());
        commandMap.put("clear", new Clear());
        commandMap.put("count_by_number_of_participants", new CountByNumberOfParticipants());
        commandMap.put("execute_script", new ExecuteScript());
        commandMap.put("exit", new Exit());
        commandMap.put("filter_by_albums_count", new FilterByAlbumsCount());
        commandMap.put("help", new Help());
        commandMap.put("info", new Info());
        commandMap.put("remove_any_by_front_man", new RemoveAnyByFrontMan());
        commandMap.put("remove_at", new RemoveAt());
        commandMap.put("remove_by_id", new RemoveById());
        commandMap.put("reorder", new Reorder());
        commandMap.put("show", new Show());
        commandMap.put("shuffle", new Shuffle());
        commandMap.put("update", new Update());
    }

    public ClientData check (String name, ArrayList<String> listOfCommand) throws NullPointerException, RuntimeException{
        ClientData clientData =  commandMap.get(inputHandler(name)).processing(name, listOfCommand);
        if (clientData != null){
            return  clientData;
        }
        throw new RuntimeException();
        //printer.errPrintln(commandMap.get(inputHandler(name)).processing(name, listOfCommand).getNumber().toString());
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
