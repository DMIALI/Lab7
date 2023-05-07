package ManagerOfCommands.Commands;

import ManagerOfCommands.CommandData.CommandData;

import java.util.ArrayList;

public class Reorder extends Command{
    @Override
    public CommandData processing(String title, ArrayList<String> args) {
        CommandData commandData = new CommandData();
        commandData.setName("reorder");
        commandData.setArgs(null);
        return commandData;
    }
}
