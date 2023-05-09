package ManagerOfCommands.Commands;

import ManagerOfCommands.CommandData.CommandData;

import java.util.ArrayList;

public abstract class Command {

    public Command() {
    }

//    public abstract void processing(InputCommandData input);
    public abstract CommandData processing(String title, ArrayList<String> args);
}
