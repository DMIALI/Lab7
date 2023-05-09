package ManagerOfCommands.Commands;

import ManagerOfCommands.CommandData.CommandData;

import java.util.ArrayList;

public class Help extends Command{

//    @Override
//    public void processing(InputCommandData input) {
//
//    }


    @Override
    public CommandData processing(String title, ArrayList<String> args) {
        CommandData commandData = new CommandData();
        commandData.setName("help");
        commandData.setArgs(null);
        return commandData;
    }
}
